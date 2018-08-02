/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie;

import atos.magiemagie.service.PartieService;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Administrateur
 */
public class PartieDAOCrudTest {
    
    @Autowired
    private PartieService partieServ;
    
    @Test
    public void test(){
        partieServ.creerNouvellePartie("Ok");
        System.out.println("OK");
    }
}
