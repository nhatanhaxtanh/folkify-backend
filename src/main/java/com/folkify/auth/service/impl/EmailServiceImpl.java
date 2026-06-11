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
                <html lang="vi" xmlns="http://www.w3.org/1999/xhtml">
                <head>
                  <meta charset="UTF-8"/>
                  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                  <title>Đặt lại mật khẩu - Folkify</title>
                </head>
                <body style="margin:0;padding:0;background-color:#0f0f1a;">
                <table width="100%%" cellpadding="0" cellspacing="0" border="0" bgcolor="#0f0f1a">
                  <tr><td align="center" style="padding:40px 16px;">
                    <table width="520" cellpadding="0" cellspacing="0" border="0" style="max-width:520px;width:100%%;">

                      <!-- Logo header -->
                      <tr>
                        <td align="center" bgcolor="#1a1a2e" style="background-color:#1a1a2e;padding:28px 40px 20px;border-radius:16px 16px 0 0;border-bottom:3px solid #D97706;">
                          <table cellpadding="0" cellspacing="0" border="0" width="100%%">
                            <tr>
                              <td>
                                <span style="font-size:26px;font-weight:900;color:#D97706;font-family:Arial,sans-serif;letter-spacing:-0.5px;">&#9835; Folkify</span>
                              </td>
                              <td align="right">
                                <span style="font-size:12px;color:#555577;font-family:Arial,sans-serif;">Học nhạc cụ dân tộc</span>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>

                      <!-- Body -->
                      <tr>
                        <td bgcolor="#16213e" style="background-color:#16213e;padding:36px 40px 32px;">
                          <table cellpadding="0" cellspacing="0" border="0" width="100%%">
                            <tr>
                              <td style="padding-bottom:20px;">
                                <table cellpadding="0" cellspacing="0" border="0">
                                  <tr>
                                    <td width="52" height="52" bgcolor="#2a1f0a" style="background-color:#2a1f0a;border-radius:12px;text-align:center;vertical-align:middle;">
                                      <span style="font-size:24px;line-height:52px;">&#128274;</span>
                                    </td>
                                  </tr>
                                </table>
                              </td>
                            </tr>
                            <tr>
                              <td style="font-family:Arial,sans-serif;font-size:22px;font-weight:700;color:#ffffff;padding-bottom:12px;">
                                Đặt lại mật khẩu
                              </td>
                            </tr>
                            <tr>
                              <td style="font-family:Arial,sans-serif;font-size:15px;color:#aaaacc;line-height:1.7;padding-bottom:8px;">
                                Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản Folkify của bạn.
                              </td>
                            </tr>
                            <tr>
                              <td style="font-family:Arial,sans-serif;font-size:15px;color:#aaaacc;line-height:1.7;padding-bottom:32px;">
                                Nhấn nút bên dưới để tiếp tục. Link có hiệu lực trong
                                <span style="color:#D97706;font-weight:700;">1 giờ</span>.
                              </td>
                            </tr>
                            <tr>
                              <td align="center" style="padding-bottom:8px;">
                                <a href="%s"
                                   style="display:inline-block;background-color:#D97706;color:#ffffff;text-decoration:none;font-family:Arial,sans-serif;font-size:16px;font-weight:700;padding:16px 40px;border-radius:12px;letter-spacing:0.3px;">
                                  Đặt lại mật khẩu
                                </a>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>

                      <!-- Footer -->
                      <tr>
                        <td bgcolor="#111122" style="background-color:#111122;padding:20px 40px 28px;border-radius:0 0 16px 16px;border-top:1px solid #2a2a4a;">
                          <table cellpadding="0" cellspacing="0" border="0" width="100%%">
                            <tr>
                              <td style="font-family:Arial,sans-serif;font-size:13px;color:#555577;line-height:1.6;padding-bottom:8px;">
                                Nếu bạn không yêu cầu đặt lại mật khẩu, hãy bỏ qua email này.
                              </td>
                            </tr>
                            <tr>
                              <td style="font-family:Arial,sans-serif;font-size:12px;color:#444466;">
                                &copy; 2025 Folkify &mdash; Học nhạc cụ dân tộc Việt Nam
                              </td>
                            </tr>
                          </table>
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
