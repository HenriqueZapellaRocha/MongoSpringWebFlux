package com.example.demo.services.emailServices;

import com.example.avro.CheckoutMessage;
import com.example.demo.domain.models.ProductEntity;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;

@Service
@Data
public class EmailService {

    private final JavaMailSender mailSender;


    public Mono<Void> sendEmail(CheckoutMessage checkoutMessage, ProductEntity product, String checkoutId) {

        return Mono.fromRunnable( () -> {

            try {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setFrom("noreply@mail.com");
                helper.setSubject("checkout confirmation");
                helper.setTo( checkoutMessage.getUserEmail() );

                String template = templateLoader();

                BigDecimal value = product.getPrice().multiply(new BigDecimal(checkoutMessage.getQuantity()))
                        .setScale( 2, RoundingMode.HALF_UP );

                template = template.replace( "#{clientName}", checkoutMessage.getLogin() );
                template = template.replace( "#{productName}", product.getName() );
                template = template.replace( "#{checkoutId}", checkoutId );
                template = template.replace( "#{value}", value.toString() );
                helper.setText(template, true);

                mailSender.send(mimeMessage);

            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } )
                .then()
                .subscribeOn( Schedulers.boundedElastic() );
    }

    public String templateLoader() throws IOException {
        ClassPathResource resource = new ClassPathResource( "emailTemplate.html" );
        return new String( resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8 );
    }
}

