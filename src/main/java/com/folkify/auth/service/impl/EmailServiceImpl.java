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
        String logoUrl = baseUrl + "/images/logo.png";
        String html = buildResetEmailHtml(resetLink, logoUrl);
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

    private String buildResetEmailHtml(String resetLink, String logoUrl) {
        return """
                <!DOCTYPE html>
                <html lang="vi" xmlns="http://www.w3.org/1999/xhtml">
                <head>
                  <meta charset="UTF-8"/>
                  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                  <title>Đặt lại mật khẩu - Folkify</title>
                </head>
                <body style="margin:0;padding:0;background-color:#f0f7f0;">
                <table width="100%%" cellpadding="0" cellspacing="0" border="0" bgcolor="#f0f7f0">
                  <tr><td align="center" style="padding:40px 16px;">
                    <table width="520" cellpadding="0" cellspacing="0" border="0" style="max-width:520px;width:100%%;">

                      <!-- Header -->
                      <tr>
                        <td align="center" bgcolor="#1a5c2e" style="background-color:#1a5c2e;padding:28px 40px;border-radius:16px 16px 0 0;">
                          <table cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td valign="middle" style="padding-right:12px;">
                                <img src="%s" width="48" height="48" alt="Folkify" style="display:block;border-radius:10px;"/>
                              </td>
                              <td valign="middle">
                                <span style="font-family:Arial,sans-serif;font-size:26px;font-weight:900;color:#ffffff;letter-spacing:-0.5px;">Folkify</span>
                                <br/>
                                <span style="font-family:Arial,sans-serif;font-size:12px;color:#a7d4b0;">Học nhạc cụ dân tộc Việt Nam</span>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>

                      <!-- Green accent bar -->
                      <tr>
                        <td height="4" bgcolor="#4ade80" style="background-color:#4ade80;font-size:0;line-height:0;">&nbsp;</td>
                      </tr>

                      <!-- Body -->
                      <tr>
                        <td bgcolor="#ffffff" style="background-color:#ffffff;padding:40px 40px 32px;border-left:1px solid #d1e7d4;border-right:1px solid #d1e7d4;">
                          <table cellpadding="0" cellspacing="0" border="0" width="100%%">
                            <tr>
                              <td style="padding-bottom:24px;">
                                <table cellpadding="0" cellspacing="0" border="0">
                                  <tr>
                                    <td width="56" height="56" bgcolor="#f0f7f0" style="background-color:#f0f7f0;border-radius:14px;text-align:center;vertical-align:middle;">
                                      <span style="font-size:26px;line-height:56px;">&#128274;</span>
                                    </td>
                                  </tr>
                                </table>
                              </td>
                            </tr>
                            <tr>
                              <td style="font-family:Arial,sans-serif;font-size:22px;font-weight:700;color:#14532d;padding-bottom:14px;">
                                Đặt lại mật khẩu
                              </td>
                            </tr>
                            <tr>
                              <td style="font-family:Arial,sans-serif;font-size:15px;color:#4b5563;line-height:1.7;padding-bottom:8px;">
                                Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản Folkify của bạn.
                              </td>
                            </tr>
                            <tr>
                              <td style="font-family:Arial,sans-serif;font-size:15px;color:#4b5563;line-height:1.7;padding-bottom:36px;">
                                Nhấn nút bên dưới để tiếp tục. Link có hiệu lực trong
                                <span style="color:#1a5c2e;font-weight:700;">1 giờ</span>.
                              </td>
                            </tr>
                            <tr>
                              <td align="center" style="padding-bottom:16px;">
                                <a href="%s"
                                   style="display:inline-block;background-color:#1a5c2e;color:#ffffff;text-decoration:none;font-family:Arial,sans-serif;font-size:16px;font-weight:700;padding:16px 44px;border-radius:12px;letter-spacing:0.3px;">
                                  Đặt lại mật khẩu
                                </a>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>

                      <!-- Footer -->
                      <tr>
                        <td bgcolor="#f8fdf9" style="background-color:#f8fdf9;padding:20px 40px 28px;border-radius:0 0 16px 16px;border:1px solid #d1e7d4;border-top:none;">
                          <table cellpadding="0" cellspacing="0" border="0" width="100%%">
                            <tr>
                              <td style="font-family:Arial,sans-serif;font-size:13px;color:#6b7280;line-height:1.6;padding-bottom:8px;">
                                Nếu bạn không yêu cầu đặt lại mật khẩu, hãy bỏ qua email này — mật khẩu sẽ không thay đổi.
                              </td>
                            </tr>
                            <tr>
                              <td style="font-family:Arial,sans-serif;font-size:12px;color:#9ca3af;">
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
                """.formatted(logoUrl, resetLink);
    }
}
