package com.trapuce.marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.trapuce.marketplace.exceptions.EmailSendException;
import com.trapuce.marketplace.models.Token;
import com.trapuce.marketplace.models.User;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(Token emailConfirmationToken) {
        String subject = "Confirm your Email - MALI SUGU Registration";
        String actionText = "Confirm Email Address";
        String messageBody = "We're excited to have you get started with MALI SUGU! Please click the button below to confirm your account:";
        String link = generateConfirmationLink(emailConfirmationToken.getTokenValue());

        sendEmail(emailConfirmationToken.getUser(), subject, actionText, messageBody, link);
    }

    public void sendPasswordResetEmail(Token passwordResetToken) {
        String subject = "Reset Your Password - MALI SUGU";
        String actionText = "Reset Password";
        String messageBody = "We received a request to reset your password. Click the button below to proceed:";
        String link = generatePasswordResetLink(passwordResetToken.getTokenValue());

        sendEmail(passwordResetToken.getUser(), subject, actionText, messageBody, link);
    }

    private void sendEmail(User user, String subject, String actionText, String messageBody, String link) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject(subject);

            String htmlContent = String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                        .container { padding: 20px; }
                        .button {
                            display: inline-block;
                            padding: 10px 20px;
                            background-color: #007bff;
                            color: white;
                            text-decoration: none;
                            border-radius: 5px;
                            margin: 20px 0;
                        }
                        .footer { margin-top: 20px; color: #666; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h2>Dear %s,</h2>
                        <p>%s</p>
                        <a href="%s" class="button">%s</a>
                        <p>If the button doesn't work, you can copy and paste this link into your browser:</p>
                        <p>%s</p>
                        <div class="footer">
                            <p>Best regards,<br>MALI SUGU Team</p>
                        </div>
                    </div>
                </body>
                </html>
                """, 
                user.getFirstName(),
                messageBody,
                link,
                actionText,
                link
            );

            helper.setText(htmlContent, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new EmailSendException("Failed to send email", e);
        }
    }

    private String generateConfirmationLink(String token) {
        return "http://localhost:8080/api/v1/auth/confirm-email?token=" + token;
    }

    private String generatePasswordResetLink(String token) {
        return "http://localhost:8080/api/v1/auth/password-change?token=" + token;
    }
}
