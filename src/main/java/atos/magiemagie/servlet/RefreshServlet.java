/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.servlet;

import atos.magiemagie.entity.Joueur;
import atos.magiemagie.service.PartieService;
import atos.magiemagie.spring.AutowireServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrateur
 */
@WebServlet(name = "RefreshServlet", urlPatterns = {"/refresh-change"})
public class RefreshServlet extends AutowireServlet {
    PartieService partieServ = new PartieService();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        Long idPartie = (Long) req.getSession().getAttribute("PartieEnCours");// ID de la partie en cours
        List<Joueur> JoueurRefreshed = partieServ.getJoueurs(idPartie);//Joueur en base
        Joueur jALMRefreshed = partieServ.joueurALaMain(idPartie);// Joueur
        boolean changement = false;
        List<Joueur> joueurs = new ArrayList<>();
        
        Joueur jALM = (Joueur) req.getSession().getAttribute("jALM");
        for (int y = 0; y < JoueurRefreshed.size(); y++) {
            joueurs.add((Joueur)req.getSession().getAttribute("joueur"+y)) ;
        }
        
        for(Joueur j : joueurs){//Pour chaque joueur enregistré en session
            for(Joueur jRef : JoueurRefreshed){// Pour chaque joueur enregistré en base
                if (Objects.equals(j.getId(), jRef.getId())) {// Si leurs IDs correspondent
                    
                    if (j.getEtatJoueur() == jRef.getEtatJoueur()){// Verifie un changement d'État_Joueur
                        changement = true;//Si Oui renvoie True
                    } else if (j.getCartes().size() != jRef.getCartes().size()){//Verifie un changement du nombre de carte de sa main
                       changement = true;// Si oui renvoie True
                    }
                }
            }
        }
        // Fin Double boucle
        
        if (!Objects.equals(jALMRefreshed.getId(), jALM.getId())) {
            changement = true;
        } 
        
        if (changement == true) {
            resp.getWriter().print("true");
            req.getSession().setAttribute("joueursPartie", joueurs);
            req.getSession().setAttribute("jALM", jALMRefreshed);
        } else {
            resp.getWriter().print("false");
        }
    }

    
}
