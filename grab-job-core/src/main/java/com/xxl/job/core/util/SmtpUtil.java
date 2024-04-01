package com.xxl.job.core.util;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class SmtpUtil {

    /**
     * make script file
     *
     * @throws IOException
     */
    public static boolean sampleMail(String host, String port, String from, String fromPassword, String to, String html) throws IOException {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.port", port);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, fromPassword);
            }
        });
        MimeMessage message = new MimeMessage(session);
        try {
            InternetAddress fromAddress = new InternetAddress(from, "coinhoho.com");
            message.setFrom(fromAddress);
            // (Optional) Set the reply-to address
            //            Address[] a = new Address[1];
            //            a[0] = new InternetAddress("***");
            //            message.setReplyTo(a);
            // Set the recipient's email address, such as yyy@yyy.com
            InternetAddress toAddress = new InternetAddress(to);
            message.setRecipient(MimeMessage.RecipientType.TO, toAddress);
            // If the email is to be sent to multiple recipients at the same time, replace the above two lines with the following (due to the restrictions of some emailing systems, we recommend you try to send the email to one recipient at a time; plus, the email can be sent to up to 50 recipients at a time):
            //InternetAddress[] adds = new InternetAddress[2];
            //adds[0] = new InternetAddress("xxx@xxx.com");
            //adds[1] = new InternetAddress("xxx@xxx.com");
            //message.setRecipients(Message.RecipientType.TO, adds);
            // Set the email subject
            message.setSubject("Test email");
            message.setHeader("Content-Transfer-Encoding", "base64");
            // Set the email body type: `text/plain` (plain text) or `text/html` (HTML document)
            message.setContent(html, "text/html;charset=UTF-8");
            // Send the email
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        sampleMail("sg-smtp.qcloudmail.com", "465", "official@coinhoho.com", "OfficialCoinhoho123", "ixiezhouming@gmail.com", "test-html");
    }

    /*public static void main(String[] args) {
        String to = "ixiezhouming@gmail.com";
        String from = "official@coinhoho.com";
        String host = "sg-smtp.qcloudmail.com";
        String port = "465";
        String password = "OfficialCoinhoho123";
        //
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.enable", "true");

        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.port", port);

        //properties.put("mail.password", password);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("This is the email subject 2");
            message.setText("This is the email body 2");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }*/

}
