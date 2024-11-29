package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/autorisation")
public class AutorisationController {

    public AutorisationController() {
        // Constructeur par d�faut
    }

    @GetMapping
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    @PostMapping
    public void handlePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // R�utilisation de la logique GET pour les requ�tes POST
        handleGet(request, response);
    }
}
