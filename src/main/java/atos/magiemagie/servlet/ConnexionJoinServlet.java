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
@WebServlet(name = "ConnexionJoinServlet", urlPatterns = {"/connexion-join"})
public class ConnexionJoinServlet extends AutowireServlet {

    PartieService partieServ = new PartieService();
    JoueurService joueurServ = new JoueurService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String avatar = req.getParameter("avatar");
        Cookie cookies[] = req.getCookies();
        String partieNom = "";
        
        
        if (joueurServ.joueurExist(login) == true) {
            resp.sendRedirect("./home?login=exist");
        } else {
        
            for(Cookie cookie : cookies){
                if (cookie.getName().equals("partieNom")) {
                    partieNom = cookie.getValue();
                    cookie.setMaxAge(0);
                }
            }
            partieServ.creerNouvellePartie(partieNom);
            Partie partie = partieServ.listerPartiesNonDemarrees().get(partieServ.listerPartiesNonDemarrees().size()-1);//Récup la dernière partie créée
            joueurServ.rejoindrePartie(login, avatar, partie.getId());
            Joueur user = joueurServ.joueurByName(login);
            req.getSession().setAttribute("user", user);
            Partie partieActuelle = user.getPartieActuelle();
            req.getSession().setAttribute("partieActuelle", partieActuelle);
            resp.sendRedirect("./lobby?idPartie="+partie.getId());


        }
    }
}
