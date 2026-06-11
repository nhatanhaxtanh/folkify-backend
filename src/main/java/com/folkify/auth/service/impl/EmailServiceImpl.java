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
                <html lang="vi">
                <head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"></head>
                <body style="margin:0;padding:0;background-color:#0f0f1a;font-family:'Segoe UI',Arial,sans-serif;">
                  <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#0f0f1a;padding:40px 16px;">
                    <tr><td align="center">
                      <table width="100%%" cellpadding="0" cellspacing="0" style="max-width:480px;background:#1a1a2e;border-radius:20px;overflow:hidden;border:1px solid #2a2a4a;">

                        <!-- Header -->
                        <tr>
                          <td style="background:linear-gradient(135deg,#1a1a2e 0%%,#16213e 100%%);padding:32px 40px 24px;border-bottom:2px solid #D97706;">
                            <div style="display:flex;align-items:center;">
                              <span style="font-size:28px;font-weight:900;color:#D97706;letter-spacing:-1px;">&#9835; Folkify</span>
                            </div>
                            <p style="color:#9999bb;font-size:13px;margin:6px 0 0;">Học nhạc cụ dân tộc Việt Nam</p>
                          </td>
                        </tr>

                        <!-- Body -->
                        <tr>
                          <td style="padding:36px 40px 28px;">
                            <div style="width:64px;height:64px;background:rgba(217,119,6,0.15);border-radius:16px;display:flex;align-items:center;justify-content:center;margin-bottom:24px;">
                              <span style="font-size:30px;">&#128274;</span>
                            </div>
                            <h2 style="color:#ffffff;font-size:22px;font-weight:700;margin:0 0 12px;">Đặt lại mật khẩu</h2>
                            <p style="color:#aaaacc;font-size:15px;line-height:1.6;margin:0 0 8px;">
                              Bạn vừa yêu cầu đặt lại mật khẩu cho tài khoản Folkify.
                            </p>
                            <p style="color:#aaaacc;font-size:15px;line-height:1.6;margin:0 0 28px;">
                              Nhấn nút bên dưới để tiếp tục. Link có hiệu lực trong <span style="color:#D97706;font-weight:600;">1 giờ</span>.
                            </p>
                            <a href="%s"
                               style="display:inline-block;background:linear-gradient(135deg,#D97706,#f59e0b);color:#ffffff;text-decoration:none;font-size:16px;font-weight:700;padding:15px 36px;border-radius:12px;letter-spacing:0.3px;">
                              &#128273;&nbsp; Đặt lại mật khẩu
                            </a>
                          </td>
                        </tr>

                        <!-- Divider -->
                        <tr><td style="padding:0 40px;"><div style="height:1px;background:#2a2a4a;"></div></td></tr>

                        <!-- Footer -->
                        <tr>
                          <td style="padding:24px 40px 32px;">
                            <p style="color:#555577;font-size:13px;line-height:1.6;margin:0;">
                              Nếu bạn không yêu cầu đặt lại mật khẩu, hãy bỏ qua email này — mật khẩu sẽ không thay đổi.
                            </p>
                            <p style="color:#444466;font-size:12px;margin:12px 0 0;">
                              &copy; 2025 Folkify. All rights reserved.
                            </p>
                          </td>
                        </tr>

                      </table>
                    </td></tr>
                  </table>
                </body>
                </html>
                """.formatted(resetLink);
    }
}
