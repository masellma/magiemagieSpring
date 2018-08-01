/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.servlet;

import atos.magiemagie.entity.Joueur;
import atos.magiemagie.entity.Partie;
import atos.magiemagie.service.CarteService;
import atos.magiemagie.service.JoueurService;
import atos.magiemagie.service.PartieService;
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
@WebServlet(name = "InGamePlayersServlet", urlPatterns = {"/in-game-players"})
public class InGamePlayersServlet extends AutowireServlet {
    PartieService partieServ = new PartieService();
    JoueurService joueurServ = new JoueurService();
    CarteService carteServ = CarteService.instantiate();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Joueur user =(Joueur) req.getSession().getAttribute("user");
        Long idPartie = (Long) req.getSession().getAttribute("PartieEnCours");
        
        List<Joueur> joueurs = partieServ.getJoueurs(idPartie);
        Joueur joueurALaMain = partieServ.joueurALaMain(idPartie);
        
        req.setAttribute("joueurs", joueurs);
        req.setAttribute("joueurALaMain", joueurALaMain);
        
        req.getRequestDispatcher("./in-game-player.jsp").forward(req, resp);
        
        
    }

    
}
