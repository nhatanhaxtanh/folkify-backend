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

    private static final String LOGO_BASE64 = "data:image/png;base64,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";

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
        String logoUrl = LOGO_BASE64;
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
               "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr>" +
               "<td valign=\"middle\" style=\"padding-right:14px;\">" +
               "<img src=\"" + logoUrl + "\" width=\"52\" height=\"52\" alt=\"Folkify\" style=\"display:block;border-radius:12px;\"/>" +
               "</td>" +
               "<td valign=\"middle\" align=\"left\">" +
               "<span style=\"font-family:Georgia,serif;font-size:26px;font-weight:900;color:#ffffff;letter-spacing:0.5px;\">Folkify</span>" +
               "<br/>" +
               "<span style=\"font-family:Arial,sans-serif;font-size:12px;color:#a7d4b0;\">H&#7885;c nh&#7841;c c&#7909; d&#226;n t&#7897;c Vi&#7879;t Nam</span>" +
               "</td>" +
               "</tr></table>" +
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
