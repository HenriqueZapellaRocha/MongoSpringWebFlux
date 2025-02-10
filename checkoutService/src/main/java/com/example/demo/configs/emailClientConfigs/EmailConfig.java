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


    @Value("${mail.host}")
    private String hostname;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.ssl}")
    private boolean starttls;

    @Bean
    public MailClient mailClient() {
        // Configuração do MailClient a partir das propriedades
        MailConfig config = new MailConfig()
                .setHostname(hostname)
                .setPort(port)
                .setUsername(username)
                .setPassword(password)
                .setStarttls(StartTLSOptions.REQUIRED);
//                .setSsl(true);
        return MailClient.createShared(Vertx.vertx(), config);
    }
}
