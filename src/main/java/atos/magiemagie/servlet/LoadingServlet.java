/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.servlet;

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
@WebServlet(name = "LoadingServlet", urlPatterns = {"/loading"})
public class LoadingServlet extends AutowireServlet {

    PartieService partieServ = new PartieService();
   

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

         Long idPartie = Long.parseLong(req.getParameter("idPartie"));
         partieServ.demarrerPartie(idPartie);
         resp.sendRedirect("./game?idPartie="+idPartie);
    }
            
    
}
