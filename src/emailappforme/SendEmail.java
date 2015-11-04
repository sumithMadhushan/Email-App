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


import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//import emailappforme.Email.txtMyEmailAddress;
import static emailappforme.Email.txtMyEmailAddress;
import static emailappforme.Email.txtMyPassword;

public class SendEmail {
    
    String ReceiverEmail="";
    String subject="";
    String emailBody="";
    
    int isEmailSend(String ReceiverEmail,String subject,String emailBody){
        
        this.ReceiverEmail=ReceiverEmail;
        this.subject=subject;
        this.emailBody=emailBody;
        
        
        final String senderEmail =txtMyEmailAddress.getText();
        final String senderPassword =txtMyPassword.getText();
   
   
       Properties prop=new Properties();
       
       prop.put("mail.smtp.ssl.trust","smtp.gmail.com");
       prop.put("mail.smtp.auth",true);
       prop.put("mail.smtp.starttls.enable",true);
       prop.put("mail.smtp.host","smtp.gmail.com");
       prop.put("mail.smtp.port","587");
       
       Session session =Session.getInstance(prop, new javax.mail.Authenticator() 
       {
          
          protected javax.mail.PasswordAuthentication getPasswordAuthentication()
           {
               return new javax.mail.PasswordAuthentication(senderEmail, senderPassword);
           }
       });
       
       try{
           Message message =new MimeMessage(session);
           message.setFrom(new InternetAddress(senderEmail));
           message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ReceiverEmail));
           message.setSubject(subject);
           message.setText(emailBody);
           
           Transport.send(message);
           
           System.out.println("message sent");
           return 1;
       }catch(Exception e){
           System.out.println(e); 
           return 0;
       }
    
    
    }
}
