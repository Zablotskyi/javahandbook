package com.example.javahandbook.service;

import com.example.javahandbook.entity.ArticleComment;
import com.example.javahandbook.model.ArticleData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {

    private final JavaMailSender mailSender;

    @Value("${app.notifications.admin-email}")
    private String adminEmail;

    @Value("${app.notifications.from-email}")
    private String fromEmail;

    @Value("${app.site.base-url}")
    private String siteBaseUrl;

    public EmailNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendNewCommentNotification(ArticleData article, ArticleComment comment) {
        String articleUrl = siteBaseUrl + "/articles/" + article.slug() + "#comments";
        String adminUrl = siteBaseUrl + "/admin";

        String subject = "Новий коментар на Java Handbook";

        String text = """
                На сайті Java Handbook додано новий коментар.

                Стаття:
                %s

                Автор:
                %s

                Коментар:
                %s

                Посилання на статтю:
                %s

                Посилання на адмінку:
                %s
                """.formatted(
                article.title(),
                comment.getAuthorName(),
                comment.getContent(),
                articleUrl,
                adminUrl
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(adminEmail);
        message.setSubject(subject);
        message.setText(text);

        try {
            System.out.println("=================================");
            System.out.println("Trying to send comment notification email...");
            System.out.println("From: " + fromEmail);
            System.out.println("To: " + adminEmail);
            System.out.println("Article URL: " + articleUrl);
            System.out.println("Admin URL: " + adminUrl);
            System.out.println("SMTP sending started...");
            System.out.println("=================================");

            mailSender.send(message);

            System.out.println("=================================");
            System.out.println("Email notification sent successfully.");
            System.out.println("=================================");
        } catch (MailException exception) {
            System.err.println("=================================");
            System.err.println("Failed to send comment notification email.");
            System.err.println("Error class: " + exception.getClass().getName());
            System.err.println("Error message: " + exception.getMessage());
            exception.printStackTrace();
            System.err.println("=================================");
        }
    }
}