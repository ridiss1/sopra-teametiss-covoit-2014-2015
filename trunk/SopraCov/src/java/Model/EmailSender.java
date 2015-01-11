/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.UnsupportedEncodingException;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Adrian
 */
public class EmailSender {
    

public EmailSender(String email, String name){    
try {
    InitialContext ctx = new InitialContext();
        Session session =
            (Session) ctx.lookup("mail/SopraCovContact");
        // Or by injection.
        //@Resource(name = "mail/<name>")
        //private Session session;

        // Create email and headers.
        Message msg = new MimeMessage(session);
        msg.setSubject("Bienvenue "+ name +" sur SopraCov");
        msg.setRecipient(RecipientType.TO,
                         new InternetAddress(
                         email,
                         name));
        msg.setFrom(new InternetAddress(
                    "teametiss31@gmail.com",
                    "SopraCov Teametiss"));

        // Body text.
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Cher(e) "+ name+ "\nVous venez de vous enregistrer sur le site SopraCov.\n\n Merci d'avoir choisi l'appli SopraCov.\n\n Teametiss");
         // Multipart message.
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);       

        // Add multipart message to email.
        msg.setContent(multipart);


        // Send email.
        Transport.send(msg);}

    catch (NamingException | MessagingException | UnsupportedEncodingException e){
    System.err.println("ERROR BOOL");
    e.printStackTrace();
            }
}
}
    


