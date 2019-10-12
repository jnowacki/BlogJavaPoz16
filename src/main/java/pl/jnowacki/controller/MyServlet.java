package pl.jnowacki.controller;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

@WebServlet("/email")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.mailtrap.io");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("5ae912f9c53254", "cef6986ccdae40");
            }
        });

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("javapoz16@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse("contact@jnowacki.pl"));
            message.setSubject("Mail Subject");

            String msg = "<a href=\"https://www.youtube.com/watch?v=oHg5SJYRHA0\">click me</a>";

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        resp.getWriter().println("Hi from servlet");
    }
}
