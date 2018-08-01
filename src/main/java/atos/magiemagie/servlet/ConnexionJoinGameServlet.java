/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.servlet;

import atos.magiemagie.entity.Joueur;
import atos.magiemagie.entity.Partie;
import atos.magiemagie.service.JoueurService;
import atos.magiemagie.service.PartieService;
import atos.magiemagie.spring.AutowireServlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrateur
 */
@WebServlet(name = "ConnexionJoinGameServlet", urlPatterns = {"/connexion-join-game"})
public class ConnexionJoinGameServlet extends AutowireServlet {

    PartieService partieServ = new PartieService();
    JoueurService joueurServ = new JoueurService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
// Rejoindre une partie sans s'être connecté avant
        String login = req.getParameter("login");
        String avatar = req.getParameter("avatar");
        Cookie cookies[] = req.getCookies();
        Long idPartie = -1L;
        
        
        
        if (joueurServ.joueurExist(login) == true) {
            resp.sendRedirect("./home?login=exist");
        } else {
        
            for(Cookie cookie : cookies){
                if (cookie.getName().equals("idPartie")) {
                    idPartie = Long.parseLong(cookie.getValue());
                    cookie.setMaxAge(0);
                }
            }
            joueurServ.rejoindrePartie(login, avatar, idPartie);
            Joueur user = joueurServ.joueurByName(login);
            req.getSession().setAttribute("user", user);
            Partie partieActuelle = user.getPartieActuelle();
            req.getSession().setAttribute("partieActuelle", idPartie);
            resp.sendRedirect("./lobby?idPartie="+idPartie);


        }
    }
}
