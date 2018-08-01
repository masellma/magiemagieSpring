/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.dao;

import atos.magiemagie.entity.Joueur;
import atos.magiemagie.entity.Partie;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Administrateur
 */

public interface PartieDAOCrud extends CrudRepository<Partie, Long>{
    
    @Query("SELECT p FROM Partie p EXCEPT SELECT p FROM Partie p JOIN p.joueurs j WHERE j.etatJoueur IN (atos.magiemagie.entity.Joueur.EtatJoueur.A_LA_MAIN, atos.magiemagie.entity.Joueur.EtatJoueur.GAGNE)")
    public List<Partie> listerPartiesNonDemarrees();
    
}
