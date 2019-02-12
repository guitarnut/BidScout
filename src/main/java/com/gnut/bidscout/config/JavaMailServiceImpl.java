package com.gnut.bidscout.config;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class JavaMailServiceImpl {
//    @Bean
//    public JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("email-smtp.us-east-1.amazonaws.com");
//        mailSender.setPort(587);
//        mailSender.setUsername("AKIAJ4P2FUUFRQLSUMCQ");
//        mailSender.setPassword("BGx3EWihVCUKIwtm3CcnG0ooeeYiE6hudIvggu2aqQSk");
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        return mailSender;
//    }
}
