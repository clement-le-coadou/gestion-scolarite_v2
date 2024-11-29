package mdpHacher;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {                //cette classe n'est pas utilisé par soucis de simpliciter lors de la présentation et de la conception
											// mais elle est importante, car elle permet de sécuriser les mots de passes, il suffit d'utiliser la fonction
											//hashPassword

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        // Créer une instance de MessageDigest pour SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        
        // Hacher le mot de passe
        byte[] hashBytes = digest.digest(password.getBytes());

        // Convertir les octets en une chaîne hexadécimale
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        
        return hexString.toString();
    }

    public static void main(String[] args) {
        try {
            String password = "monMotDePasse";
            String hashedPassword = hashPassword(password);
            System.out.println("Mot de passe haché : " + hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}

