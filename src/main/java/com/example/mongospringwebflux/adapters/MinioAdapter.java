package com.example.mongospringwebflux.adapters;

import com.example.mongospringwebflux.exception.GlobalException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.http.Method;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.io.File;
import java.util.concurrent.TimeUnit;

@Service

@Data
public class MinioAdapter {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;


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
                                if (temp.exists()) {
                                    temp.delete();
                                }
                            }));
                });
    }

    public Mono<String> getSignedImageUrl(String productId) {

        return Mono.fromCallable(() -> {

            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(productId)
                            .expiry(2, TimeUnit.HOURS)
                            .build()
            );
        }).subscribeOn( Schedulers.boundedElastic() );
    }

    public Mono<Void> deleteFile(String productId) {
        return Mono.fromCallable(() -> {

            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(productId)
                            .build()
            );
            return Mono.empty();
                })
                .subscribeOn( Schedulers.boundedElastic() )
                .onErrorMap(e -> new GlobalException( "error deleting image" ))
                .then();
    }
}
