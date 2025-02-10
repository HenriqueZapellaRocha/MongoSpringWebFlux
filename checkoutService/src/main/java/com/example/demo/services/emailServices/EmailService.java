package com.example.demo.services.emailServices;

import com.example.avro.CheckoutMessage;
import com.example.demo.domain.models.ProductEntity;
import io.vertx.ext.mail.MailMessage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import io.vertx.ext.mail.MailClient;

@Service
@Data
public class EmailService {

    private final MailClient mailClient;

    public Mono<Void> sendEmail( CheckoutMessage checkoutMessage, ProductEntity product, String checkoutId ) {



        return templateLoader().map( template -> {

            BigDecimal value = product.getPrice().multiply( new BigDecimal( checkoutMessage.getQuantity()) )
                    .setScale( 2, RoundingMode.HALF_UP );

                template = template.replace( "#{clientName}", checkoutMessage.getLogin() );
                template = template.replace( "#{productName}", product.getName() ) ;
                template = template.replace( "#{checkoutId}", checkoutId );
                template = template.replace( "#{value}", value.toString() );
                template = template.replace( "#{currency}", checkoutMessage.getCurrency() );

                return template;

        } )
                .flatMap(emailTemplate -> {
                    MailMessage mailMessage = new MailMessage();
                    mailMessage.setFrom("noreply@mail.com");
                    mailMessage.setTo(checkoutMessage.getUserEmail());
                    mailMessage.setSubject("Checkout Confirmation");
                    mailMessage.setHtml(emailTemplate);

                    return Mono.just( mailClient.sendMail(mailMessage) );
                })
                .then();
    }

    public Mono<String> templateLoader() {
        ClassPathResource resource = new ClassPathResource( "emailTemplate.html" );
        return DataBufferUtils.read( resource, new DefaultDataBufferFactory(), 4096)
                .map( dataBuffer -> {
                    byte[] bytes = new byte[ dataBuffer.readableByteCount() ];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release( dataBuffer );
                    return new String( bytes, StandardCharsets.UTF_8 );
                } ).reduce( (content1, content2) -> content1 + content2 );
    }
}

