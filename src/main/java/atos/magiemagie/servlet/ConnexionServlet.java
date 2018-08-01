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
@WebServlet(name = "ConnexionServlet", urlPatterns = {"/connexion"})
public class ConnexionServlet extends AutowireServlet {
    PartieService PartieServ = new PartieService();
    JoueurService JoueurServ = new JoueurService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        Joueur user = new Joueur();
        user.setPseudo(req.getParameter("login"));
        user.setAvatar(req.getParameter("avatar"));
        
        if (JoueurServ.joueurExist(user.getPseudo()) == true) {
            resp.sendRedirect("./home?login=exist");
        } else {
            req.getSession().setAttribute("user", user);
            Partie partieActuelle = user.getPartieActuelle();
            req.getSession().setAttribute("partieActuelle", partieActuelle);
        }
             resp.sendRedirect("./liste-partie");

        }
    }


            
    

