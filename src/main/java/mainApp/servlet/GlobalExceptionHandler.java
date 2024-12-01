package mainApp.servlet;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Gérer les erreurs 404 (Not Found)
    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404(NoHandlerFoundException ex, Model model) {
        model.addAttribute("errorCode", "404");
        model.addAttribute("errorMessage", "La page que vous cherchez est introuvable.");
        return "error"; // Vue JSP pour afficher l'erreur
    }

    // Gérer les erreurs 500 (Internal Server Error)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handle500(Exception ex, Model model) {
        model.addAttribute("errorCode", "500");
        model.addAttribute("errorMessage", "Une erreur interne est survenue. Veuillez réessayer plus tard.");
        return "error"; // Vue JSP pour afficher l'erreur
    }

    // Gérer toutes les autres erreurs
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleGenericError(Throwable ex, Model model) {
        model.addAttribute("errorCode", "400");
        model.addAttribute("errorMessage", "Une erreur inattendue est survenue.");
        return "error"; // Vue JSP pour afficher l'erreur
    }
}
