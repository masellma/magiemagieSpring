/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.servlet;

import atos.magiemagie.entity.Joueur;
import atos.magiemagie.entity.Partie;
import atos.magiemagie.service.PartieService;
import atos.magiemagie.spring.AutowireServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(name = "GameBoardServlet", urlPatterns = {"/game"})
public class GameBoardServlet extends AutowireServlet {
    PartieService partieServ = new PartieService();
    @Override
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idPartie = Long.parseLong(req.getParameter("idPartie"));
        req.getSession().setAttribute("PartieEnCours", idPartie);
        List<Joueur> joueurs = partieServ.getJoueurs(idPartie);
        Joueur joueurALM = partieServ.joueurALaMain(idPartie);
        
        for (int i = 0; i < joueurs.size(); i++) {
            req.getSession().setAttribute("joueur"+i, joueurs.get(i));
        }
        req.getSession().setAttribute("jALM", joueurALM);
        req.getRequestDispatcher("./game.jsp").forward(req, resp);
        
        
    }
    
    
}
