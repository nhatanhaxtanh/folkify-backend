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
               "<html lang=\"vi\" xmlns=\"http://www.w3.org/1999/xhtml\">" +
               "<head>" +
               "<meta charset=\"UTF-8\"/>" +
               "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>" +
               "<title>Dat lai mat khau - Folkify</title>" +
               "</head>" +
               "<body style=\"margin:0;padding:0;background-color:#f0f7f0;\">" +
               "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#f0f7f0\">" +
               "<tr><td align=\"center\" style=\"padding:40px 16px;\">" +
               "<table width=\"520\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:520px;width:100%;\">" +

               // Header
               "<tr>" +
               "<td align=\"center\" bgcolor=\"#1a5c2e\" style=\"background-color:#1a5c2e;padding:28px 40px;border-radius:16px 16px 0 0;\">" +
               "<span style=\"font-family:Georgia,serif;font-size:30px;font-weight:900;color:#ffffff;letter-spacing:1px;\">&#9835; Folkify</span>" +
               "<br/>" +
               "<span style=\"font-family:Arial,sans-serif;font-size:12px;color:#a7d4b0;letter-spacing:0.5px;\">H&#7885;c nh&#7841;c c&#7909; d&#226;n t&#7897;c Vi&#7879;t Nam</span>" +
               "</td>" +
               "</tr>" +

               // Body
               "<tr>" +
               "<td bgcolor=\"#ffffff\" style=\"background-color:#ffffff;padding:40px 40px 32px;border-left:1px solid #c6dfc9;border-right:1px solid #c6dfc9;\">" +
               "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">" +
               "<tr>" +
               "<td style=\"padding-bottom:24px;\">" +
               "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">" +
               "<tr>" +
               "<td width=\"56\" height=\"56\" bgcolor=\"#e8f5eb\" style=\"background-color:#e8f5eb;border-radius:14px;text-align:center;vertical-align:middle;\">" +
               "<span style=\"font-size:26px;line-height:56px;\">&#128274;</span>" +
               "</td>" +
               "</tr>" +
               "</table>" +
               "</td>" +
               "</tr>" +
               "<tr>" +
               "<td style=\"font-family:Arial,sans-serif;font-size:22px;font-weight:700;color:#14532d;padding-bottom:14px;\">" +
               "&#272;&#7863;t l&#7841;i m&#7853;t kh&#7849;u" +
               "</td>" +
               "</tr>" +
               "<tr>" +
               "<td style=\"font-family:Arial,sans-serif;font-size:15px;color:#4b5563;line-height:1.7;padding-bottom:8px;\">" +
               "Ch&#250;ng t&#244;i nh&#7853;n &#273;&#432;&#7907;c y&#234;u c&#7847;u &#273;&#7863;t l&#7841;i m&#7853;t kh&#7849;u cho t&#224;i kho&#7843;n Folkify c&#7911;a b&#7841;n." +
               "</td>" +
               "</tr>" +
               "<tr>" +
               "<td style=\"font-family:Arial,sans-serif;font-size:15px;color:#4b5563;line-height:1.7;padding-bottom:36px;\">" +
               "Nh&#7845;n n&#250;t b&#234;n d&#432;&#7899;i &#273;&#7875; ti&#7871;p t&#7909;c. Link c&#243; hi&#7879;u l&#7921;c trong " +
               "<span style=\"color:#1a5c2e;font-weight:700;\">1 gi&#7901;</span>." +
               "</td>" +
               "</tr>" +
               "<tr>" +
               "<td align=\"center\" style=\"padding-bottom:16px;\">" +
               "<a href=\"" + resetLink + "\" style=\"display:inline-block;background-color:#1a5c2e;color:#ffffff;text-decoration:none;font-family:Arial,sans-serif;font-size:16px;font-weight:700;padding:16px 44px;border-radius:12px;letter-spacing:0.3px;\">" +
               "&#272;&#7863;t l&#7841;i m&#7853;t kh&#7849;u" +
               "</a>" +
               "</td>" +
               "</tr>" +
               "</table>" +
               "</td>" +
               "</tr>" +

               // Footer
               "<tr>" +
               "<td bgcolor=\"#f8fdf9\" style=\"background-color:#f8fdf9;padding:20px 40px 28px;border-radius:0 0 16px 16px;border:1px solid #c6dfc9;border-top:none;\">" +
               "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">" +
               "<tr>" +
               "<td style=\"font-family:Arial,sans-serif;font-size:13px;color:#6b7280;line-height:1.6;padding-bottom:8px;\">" +
               "N&#7871;u b&#7841;n kh&#244;ng y&#234;u c&#7847;u &#273;&#7863;t l&#7841;i m&#7853;t kh&#7849;u, h&#227;y b&#7887; qua email n&#224;y." +
               "</td>" +
               "</tr>" +
               "<tr>" +
               "<td style=\"font-family:Arial,sans-serif;font-size:12px;color:#9ca3af;\">" +
               "&copy; 2025 Folkify &mdash; H&#7885;c nh&#7841;c c&#7909; d&#226;n t&#7897;c Vi&#7879;t Nam" +
               "</td>" +
               "</tr>" +
               "</table>" +
               "</td>" +
               "</tr>" +

               "</table>" +
               "</td></tr>" +
               "</table>" +
               "</body>" +
               "</html>";
    }
}
