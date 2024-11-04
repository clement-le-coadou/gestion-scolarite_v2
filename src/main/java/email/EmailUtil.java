package email;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
    public static Properties getMailProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP server
        props.put("mail.smtp.port", "587"); // Port SMTP
        props.put("mail.smtp.auth", "true"); // Authentification requise
        props.put("mail.smtp.starttls.enable", "true"); // Activer TLS

        return props;
    }
    
    public static void envoyerEmail(String destinataire, String sujet, String contenu) {
        String username = "serveurlocal4@gmail.com";
        String password = "tgnb lzln fmns bjcy"; // à remplacer par un mot de passe d’application pour des raisons de sécurité

        // Obtenir les propriétés configurées
        Properties props = getMailProperties();
        
        // Créer une session avec l'authentification SMTP
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Créer un objet MimeMessage
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setText(contenu);

            // Envoyer le message
            Transport.send(message);
            System.out.println("Email envoyé avec succès!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Échec de l'envoi de l'email.");
        }
    }
    

    public static void main(String[] args) {
        String destinataire = "clement.le.coadou@gmail.com";
        String sujet = "Bienvenue sur notre application!";
        String contenu = "Merci de vous être inscrit sur notre application.";

        envoyerEmail(destinataire, sujet, contenu);
    }
    

}

