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
    private final String deepLinkBaseUrl;

    public EmailServiceImpl(
            JavaMailSender mailSender,
            @Value("${app.mail.from}") String from,
            @Value("${app.deep-link.base-url}") String deepLinkBaseUrl
    ) {
        this.mailSender = mailSender;
        this.from = from;
        this.deepLinkBaseUrl = deepLinkBaseUrl;
    }

    @Override
    public void sendPasswordResetEmail(String to, String resetToken) {
        String resetLink = deepLinkBaseUrl + "?token=" + resetToken;
        String html = buildResetEmailHtml(resetLink);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("Đặt lại mật khẩu Folkify");
            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Không thể gửi email", e);
        }
    }

    private String buildResetEmailHtml(String resetLink) {
        return """
                <!DOCTYPE html>
                <html>
                <body style="font-family: Arial, sans-serif; background: #f5f5f5; padding: 24px;">
                  <div style="max-width: 480px; margin: 0 auto; background: #1a1a2e; border-radius: 16px; padding: 32px; color: #fff;">
                    <h2 style="color: #D97706; margin-top: 0;">Folkify</h2>
                    <h3>Đặt lại mật khẩu</h3>
                    <p style="color: #ccc;">Bạn vừa yêu cầu đặt lại mật khẩu. Nhấn nút bên dưới để tiếp tục.</p>
                    <p style="color: #ccc;">Link có hiệu lực trong <strong style="color: #fff;">1 giờ</strong>.</p>
                    <a href="%s" style="display: inline-block; margin: 16px 0; padding: 14px 28px; background: #D97706; color: #fff; border-radius: 8px; text-decoration: none; font-weight: bold;">Đặt lại mật khẩu</a>
                    <p style="color: #888; font-size: 12px; margin-top: 24px;">Nếu bạn không yêu cầu, hãy bỏ qua email này. Mật khẩu sẽ không thay đổi.</p>
                  </div>
                </body>
                </html>
                """.formatted(resetLink);
    }
}
