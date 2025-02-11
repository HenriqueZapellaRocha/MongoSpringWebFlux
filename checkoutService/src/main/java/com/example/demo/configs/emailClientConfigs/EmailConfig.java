package com.example.demo.configs.emailClientConfigs;


import io.vertx.core.Vertx;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailConfig;
import io.vertx.ext.mail.StartTLSOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {


    @Value( "${mail.host}" )
    private String hostname;
    @Value( "${mail.port}" )
    private int port;
    @Value( "${mail.username}" )
    private String username;
    @Value( "${mail.password}" )
    private String password;

    @Bean
    public MailClient mailClient() {
        MailConfig config = new MailConfig()
                .setHostname( hostname )
                .setPort( port )
                .setUsername( username )
                .setPassword( password )
                .setStarttls( StartTLSOptions.REQUIRED );
        return MailClient.createShared( Vertx.vertx(), config );
    }
}
