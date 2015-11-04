/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailappforme;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static emailappforme.Email.filePath;
import static emailappforme.Email.txtMyEmailAddress;
import static emailappforme.Email.txtMyPassword;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {

    String ReceiverEmail = "";
    String subject = "";
    String emailBody = "";

    int isEmailSend(String ReceiverEmail, String subject, String emailBody) {

        this.ReceiverEmail = ReceiverEmail;
        this.subject = subject;
        this.emailBody = emailBody;

        final String senderEmail = txtMyEmailAddress.getText();
        final String senderPassword = txtMyPassword.getText();
        final String filepath = Email.txtFilePath.getText();

        Properties prop = new Properties();

        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", true);
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {

            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {

            if (filepath.equals("")) {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ReceiverEmail));
                message.setSubject(subject);
                message.setText(emailBody);

                Transport.send(message);

                System.out.println("message sent");
                return 1;
            } else {
                Message msg = new MimeMessage(session);
                //msg.setText(emailBody);//set the contains in the email body
                msg.setSubject(subject);//set the subject in the email
                msg.setFrom(new InternetAddress(senderEmail));//sender email
                msg.addRecipient(Message.RecipientType.TO,new InternetAddress(ReceiverEmail));

                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(emailBody);//set the contains in the email body

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);

                messageBodyPart = new MimeBodyPart();
                DataSource sourse = new FileDataSource(filePath);//get the file path from the jFileChooser
                messageBodyPart.setDataHandler(new DataHandler(sourse));
                messageBodyPart.setFileName("Attachement");
                multipart.addBodyPart(messageBodyPart);
                msg.setContent(multipart);

                Transport.send(msg);
            //finalCout=1;
                //JOptionPane.showMessageDialog(this, "Email send successfully");
                System.out.println("send successfully");
                return 1;
            }

        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }

    }
}
