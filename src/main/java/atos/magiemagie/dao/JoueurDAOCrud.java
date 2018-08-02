/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.dao;

import atos.magiemagie.entity.Joueur;
import atos.magiemagie.entity.Partie;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Administrateur
 */
public interface JoueurDAOCrud extends CrudRepository<Joueur, Long>{
    
    public Joueur findOneByOrdreAndPartie(Long l, Partie p);

    public List<Joueur> findAllByPartieId(Long idPartie);

    public Joueur findOneByEtatJoueurAndPartieId(Joueur.EtatJoueur etatJoueur, Long idPartie);
}
