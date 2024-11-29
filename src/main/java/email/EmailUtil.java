package email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;  // Autowired JavaMailSender bean from Spring Boot

    // Send Email method
    public void envoyerEmail(String destinataire, String sujet, String contenu) {
        try {
            // Create the MimeMessage
            MimeMessageHelper messageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage());
            
            // Set email fields
            messageHelper.setTo(destinataire); // Recipient's email
            messageHelper.setSubject(sujet);   // Subject of the email
            messageHelper.setText(contenu);    // Body of the email
            messageHelper.setFrom("serveurlocal4@gmail.com"); // Sender's email

            // Send email
            javaMailSender.send(messageHelper.getMimeMessage());

            System.out.println("Email envoyé avec succès!");

        } catch (MailException | MessagingException e) {
            // Catch both MailException and MessagingException
            e.printStackTrace();
            System.out.println("Échec de l'envoi de l'email.");
        }
    }

    public static void main(String[] args) {
        // Creating Spring ApplicationContext to use Spring Beans
        org.springframework.context.annotation.AnnotationConfigApplicationContext context = new org.springframework.context.annotation.AnnotationConfigApplicationContext();
        context.scan("email"); // Make sure to scan the package where your EmailUtil is located
        context.refresh();

        EmailUtil emailUtil = context.getBean(EmailUtil.class);

        String destinataire = "clement.le.coadou@gmail.com";
        String sujet = "Bienvenue sur notre application!";
        String contenu = "Merci de vous être inscrit sur notre application.";

        emailUtil.envoyerEmail(destinataire, sujet, contenu);

        context.close();  // Closing the context
    }
}
