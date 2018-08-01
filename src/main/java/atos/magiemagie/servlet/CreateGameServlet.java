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

/**
 *
 * @author Administrateur
 */
@WebServlet(name = "CreateGameServlet", urlPatterns = {"/creer-partie"})
public class CreateGameServlet extends AutowireServlet {
    PartieService partieServ = new PartieService();
    JoueurService JoueurServ = new JoueurService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nom = req.getParameter("partieNom");//Récup le nom de la game
        Joueur user = (Joueur) req.getSession().getAttribute("user");//Recupère l'utilisateur connecté
        partieServ.creerNouvellePartie(nom);//Crée la nouvelle partie
        Partie partie = partieServ.listerPartiesNonDemarrees().get(partieServ.listerPartiesNonDemarrees().size()-1);//Récup la dernière partie créée
        JoueurServ.rejoindrePartie(user.getPseudo(), user.getAvatar(), partie.getId());//Rejoint la partie
        Joueur joueur = JoueurServ.joueurByName(user.getPseudo());
        req.getSession().setAttribute("user", joueur);
        Partie partieActuelle = user.getPartieActuelle();
        req.getSession().setAttribute("partieActuelle", partieActuelle);
        resp.sendRedirect("./lobby?idPartie="+partie.getId());//Redirect sur lobby
    }

    
}
