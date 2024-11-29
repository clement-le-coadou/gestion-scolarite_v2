package email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.MailException;
import org.springframework.mail.MailMessage;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;  // Spring's JavaMailSender for sending emails

    // Send Email method
    public void envoyerEmail(String destinataire, String sujet, String contenu) {
        try {
            // Create the MimeMessage
            MimeMessageHelper messageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage());
            try {
				messageHelper.setTo(destinataire);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			} // Recipient's email
            try {
				messageHelper.setSubject(sujet);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			} // Subject of the email
            try {
				messageHelper.setText(contenu);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			} // Body of the email
            try {
				messageHelper.setFrom("serveurlocal4@gmail.com");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			} // Sender's email

            // Send email
            javaMailSender.send(messageHelper.getMimeMessage());

            System.out.println("Email envoyé avec succès!");

        } catch (MailException e) {
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
