package com.folkify.auth.service.impl;

import com.folkify.auth.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final String from;
    private final String baseUrl;

    public EmailServiceImpl(
            JavaMailSender mailSender,
            @Value("${app.mail.from}") String from,
            @Value("${app.base-url}") String baseUrl
    ) {
        this.mailSender = mailSender;
        this.from = from;
        this.baseUrl = baseUrl;
    }

    @Override
    public void sendPasswordResetEmail(String to, String resetToken) {
        String resetLink = baseUrl + "/api/auth/reset-password/open?token=" + resetToken;
        String html = buildResetEmailHtml(resetLink);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("Dat lai mat khau Folkify");
            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Khong the gui email", e);
        }
    }

    private String buildResetEmailHtml(String resetLink) {
        return "<!DOCTYPE html>" +
               "<html lang=\"vi\">" +
               "<head><meta charset=\"UTF-8\"/><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"/></head>" +
               "<body style=\"margin:0;padding:0;background-color:#ffffff;font-family:Arial,sans-serif;\">" +
               "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">" +
               "<tr><td align=\"center\" style=\"padding:40px 16px;\">" +
               "<table width=\"480\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:480px;width:100%;\">" +

               // Logo
               "<tr>" +
               "<td style=\"padding-bottom:32px;\">" +
               "<span style=\"font-family:Georgia,serif;font-size:28px;font-weight:900;color:#1a5c2e;\">&#9835; Folkify</span>" +
               "</td>" +
               "</tr>" +

               // Content
               "<tr>" +
               "<td style=\"font-size:15px;color:#374151;line-height:1.7;padding-bottom:8px;\">" +
               "Ch&#250;ng t&#244;i nh&#7853;n &#273;&#432;&#7907;c y&#234;u c&#7847;u &#273;&#7863;t l&#7841;i m&#7853;t kh&#7849;u cho t&#224;i kho&#7843;n Folkify c&#7911;a b&#7841;n. &#272;&#7875; b&#7855;t &#273;&#7847;u, h&#227;y nh&#7845;n n&#250;t b&#234;n d&#432;&#7899;i." +
               "</td>" +
               "</tr>" +

               // Button
               "<tr>" +
               "<td align=\"center\" style=\"padding:32px 0;\">" +
               "<a href=\"" + resetLink + "\" style=\"display:inline-block;background-color:#1a5c2e;color:#ffffff;text-decoration:none;font-family:Arial,sans-serif;font-size:15px;font-weight:700;padding:14px 40px;border-radius:24px;\">" +
               "&#272;&#7863;t l&#7841;i m&#7853;t kh&#7849;u" +
               "</a>" +
               "</td>" +
               "</tr>" +

               // Note
               "<tr>" +
               "<td style=\"font-size:14px;color:#6b7280;line-height:1.7;padding-bottom:32px;\">" +
               "N&#7871;u b&#7841;n g&#7863;p b&#7845;t k&#7923; v&#7845;n &#273;&#7873;, h&#227;y ph&#7843;n h&#7891;i l&#7841;i email n&#224;y. Link c&#243; hi&#7879;u l&#7921;c trong 1 gi&#7901;." +
               "</td>" +
               "</tr>" +

               // Sign off
               "<tr>" +
               "<td style=\"font-size:14px;color:#374151;padding-bottom:40px;\">" +
               "Tr&#226;n tr&#7885;ng,<br/>Folkify Team" +
               "</td>" +
               "</tr>" +

               // Footer
               "<tr>" +
               "<td align=\"center\" style=\"border-top:1px solid #e5e7eb;padding-top:24px;\">" +
               "<span style=\"font-size:13px;color:#9ca3af;\">G&#7917;i t&#7915; </span>" +
               "<span style=\"font-size:13px;color:#1a5c2e;font-weight:700;\">Folkify</span>" +
               "</td>" +
               "</tr>" +

               "</table>" +
               "</td></tr>" +
               "</table>" +
               "</body>" +
               "</html>";
    }
}
