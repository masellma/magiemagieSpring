/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.servlet;

import atos.magiemagie.dao.PartieDAO;
import atos.magiemagie.entity.Joueur;
import atos.magiemagie.entity.Partie;
import atos.magiemagie.spring.AutowireServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrateur
 */
@WebServlet(name = "JoueurInLobby", urlPatterns = {"/joueur-in-lobby"})
public class JoueurInLobby extends AutowireServlet {
    PartieDAO partieDao = new PartieDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String idPartie = (String) req.getSession().getAttribute("partieActuelle");
        Long partieActuelle = Long.parseLong(idPartie);
        List<Joueur> joueurs = partieDao.getJoueurPartie(partieActuelle);
        req.setAttribute("idPartie", idPartie);
        req.setAttribute("joueurs", joueurs);
        
        
        PrintWriter out = resp.getWriter();
        boolean partieDemmarre = false;
        for(Joueur joueur : joueurs){
            if (joueur.getEtatJoueur().toString().equals("A_LA_MAIN")) {
                partieDemmarre = true;
            }
        }
        if (partieDemmarre == false) {
            req.getRequestDispatcher("./joueurInLobby.jsp").forward(req, resp);
        } else {
            
            out.println("true");
        }
            
    }
    
    
}
