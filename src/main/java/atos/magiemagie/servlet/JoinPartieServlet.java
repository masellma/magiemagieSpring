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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrateur
 */
@WebServlet(name = "JoinPartieServlet", urlPatterns = {"/join"})
public class JoinPartieServlet extends AutowireServlet {
    PartieService partieServ = new PartieService();
    JoueurService joueurServ = new JoueurService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
// Rejoindre une partie (connecté)
        Long id = Long.parseLong(req.getParameter("idPartie"));
        Joueur user = (Joueur) req.getSession().getAttribute("user");
        if (req.getSession().getAttribute("user") != null) {
            joueurServ.rejoindrePartie(user.getPseudo(), user.getAvatar(), id);
            Joueur joueur = joueurServ.joueurByName(user.getPseudo());
            req.getSession().setAttribute("user", joueur);
            Partie partieActuelle = user.getPartieActuelle();
            req.getSession().setAttribute("partieActuelle", partieActuelle);
            resp.sendRedirect("./lobby?idPartie="+id);
        }
    }

    
}
