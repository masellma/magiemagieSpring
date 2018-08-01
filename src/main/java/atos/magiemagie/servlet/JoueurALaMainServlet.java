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
@WebServlet(name = "JoueurALaMainServlet", urlPatterns = {"/joueur-a-la-main"})
public class JoueurALaMainServlet extends AutowireServlet {
    PartieService partieServ = new PartieService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Joueur user = (Joueur) req.getSession().getAttribute("user");
        Long idPartie = (Long) req.getSession().getAttribute("PartieEnCours");
        Joueur joueurALaMain = partieServ.joueurALaMain(idPartie);
        
        if (Objects.equals(user.getId(), joueurALaMain.getId())) {
            resp.getWriter().print("true");
        } 
        
    }
    
    
}
