package com.devyatochka.huaweiapp.Helper;

import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by alexbelogurow on 26.03.17.
 */

public class Mail extends javax.mail.Authenticator {
    private static final String PORT = "25";
            //"425";  // default smtp port
    private static final String S_PORT = "465"; // default socketfactory port;
    private static final String HOST = "smtp.gmail.com";   // default smtp server

    private String sender;
    private String password;
    private String[] getterArray;

    private String topicOfEmail;
    private String textOfEmail;

    private boolean auth;
    private boolean debuggable;

    private Multipart multipart;

    public Mail(String sender, String password, String[] getter, String topicOfEmail, String textOfEmail) {
        this.sender = sender;
        this.password = password;
        this.getterArray = getter;
        this.topicOfEmail = topicOfEmail;
        this.textOfEmail = textOfEmail;

        debuggable = false; // debug mode on or off - default off
        auth = true; // smtp authentication - default on

        multipart = new MimeMultipart();

        // There is something wrong with MailCap, javamail can not find a
        // handler for the multipart/mixed part, so this bit needs to be added.
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();

        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");

        CommandMap.setDefaultCommandMap(mc);
    }

    public boolean send() throws Exception {
        Properties props = _setProperties();

        if (!sender.equals("") && !password.equals("") && getterArray.length > 0
                && !topicOfEmail.equals("")
                && !textOfEmail.equals("")) {
            Session session = Session.getInstance(props, this);

            MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(sender));

            InternetAddress[] addressTo = new InternetAddress[getterArray.length];
            for (int i = 0; i < getterArray.length; i++) {
                addressTo[i] = new InternetAddress(getterArray[i]);
            }
            msg.setRecipients(MimeMessage.RecipientType.TO, addressTo);

            msg.setSubject(topicOfEmail);
            msg.setSentDate(new Date());

            // setup message body
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(textOfEmail);
            multipart.addBodyPart(messageBodyPart);

            msg.setHeader("X-Priority", "1");
            // Put parts in message
            msg.setContent(multipart);

            // send email
            Transport.send(msg);

            return true;
        } else {
            return false;
        }
    }

    public void addAttachment(String filename) throws Exception {
        BodyPart messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);

        multipart.addBodyPart(messageBodyPart);
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(sender, password);
    }

    private Properties _setProperties() {
        Properties props = new Properties();

        props.put("mail.smtp.host", HOST);

        if (debuggable) {
            props.put("mail.debug", "true");
        }

        if (auth) {
            props.put("mail.smtp.auth", "true");
        }

        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.socketFactory.port", S_PORT);
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        return props;
    }

}
