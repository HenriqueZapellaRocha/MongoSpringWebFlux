package com.example.mongospringwebflux.service.services;

import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;

@Service
public class MinioService {

    private final MinioClient minioClient;
    private final String bucketName;

    public MinioService(@Value( "${minio.url}" ) String endpoint,
                        @Value( "${minio.access.key}" ) String accessKey,
                        @Value( "${minio.secret.key}" ) String secretKey,
                        @Value( "${minio.bucket.name}" ) String bucketName) {
        this.bucketName = bucketName;
        this.minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    public Mono<Void> uploadFile(Mono<FilePart> file, String fileName) {
        return file
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(filePart -> {
                    File temp = new File(filePart.filename());
                    return filePart.transferTo(temp)
                            .then(Mono.fromCallable(() -> {
                                UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                                        .bucket(bucketName)
                                        .object(fileName)
                                        .filename(temp.getAbsolutePath())
                                        .build();
                                return minioClient.uploadObject(uploadObjectArgs);
                            }))
                            .then(Mono.fromRunnable(() -> {

                                if ( temp.exists() ) {
                                    temp.delete();
                                }
                            }));
                });
    }
}
