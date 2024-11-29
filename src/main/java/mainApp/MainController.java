package mainApp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  // L'annotation @Controller indique qu'il s'agit d'un contrôleur Spring MVC
public class MainController {

    // Mapping de la racine ("/") de l'application
    @GetMapping("/")
    public String accueil() {
        // Retourne "accueil", ce qui signifie que Spring Boot cherchera un fichier "accueil.jsp"
        return "accueil";  
    }
}