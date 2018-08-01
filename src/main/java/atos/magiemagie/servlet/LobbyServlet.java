/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.servlet;

import atos.magiemagie.dao.PartieDAO;
import atos.magiemagie.entity.Joueur;
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
@WebServlet(name = "LobbyServlet", urlPatterns = {"/lobby"})
public class LobbyServlet extends AutowireServlet {

    PartieService partieServ = new PartieService();
    JoueurService joueurServ = new JoueurService();
    PartieDAO partieDao = new PartieDAO();
    
    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("partieActuelle", req.getParameter("idPartie"));
        req.setAttribute("idPartie", req.getParameter("idPartie"));
        req.getRequestDispatcher("./lobby.jsp").forward(req, resp);
    }

    
}
