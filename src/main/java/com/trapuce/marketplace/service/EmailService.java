package com.trapuce.marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.trapuce.marketplace.exceptions.EmailSendException;
import com.trapuce.marketplace.models.EmailConfirmationToken;
import com.trapuce.marketplace.models.User;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    public void sendConfirmationEmail(EmailConfirmationToken emailConfirmationToken) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            User user = emailConfirmationToken.getUser();
            String confirmationLink = generateConfirmationLink(emailConfirmationToken.getToken());
            
            helper.setTo(user.getEmail());
            helper.setSubject("Confirm your Email - MALI SUGU Registration");
            
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
                        <p>We're excited to have you get started with MALI SUGU!</p>
                        <p>Please click the button below to confirm your account:</p>
                        <a href="%s" class="button">Confirm Email Address</a>
                        <p>If the button doesn't work, you can copy and paste this link into your browser:</p>
                        <p>%s</p>
                        <div class="footer">
                            <p>Best regards,<br>MALI SUGU Registration Team</p>
                        </div>
                    </div>
                </body>
                </html>
                """, 
                user.getFirstName(),
                confirmationLink,
                confirmationLink
            );
            
            helper.setText(htmlContent, true);
            mailSender.send(message);
            
        } catch (MessagingException e) {
            throw new EmailSendException("Failed to send confirmation email", e);
        }
    }
    
 


    private String generateConfirmationLink(String token) {
        return "http://localhost:8080/api/v1/auth/confirm-email?token=" + token;
    }
}
