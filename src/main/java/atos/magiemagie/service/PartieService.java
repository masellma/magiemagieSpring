/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.service;

import atos.magiemagie.dao.CarteDAO;
import atos.magiemagie.dao.JoueurDAO;
import atos.magiemagie.dao.JoueurDAOCrud;
import atos.magiemagie.dao.PartieDAO;
import atos.magiemagie.dao.PartieDAOCrud;
import atos.magiemagie.entity.Carte;
import atos.magiemagie.entity.Joueur;
import atos.magiemagie.entity.Partie;
import atos.magiemagie.main.Main;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *Liste les parties dont aucun joueur n'est à l'état A_LA_MAIN ou GAGNE
 * @author Administrateur
 */
@Service
public class PartieService {
    
    @Autowired
    private PartieDAOCrud partieDaoCrud;
    @Autowired
    private JoueurDAOCrud joueurDaoCrud;
    private PartieDAO dao = new PartieDAO();
    private JoueurDAO JoueurDAO = new JoueurDAO();
    private CarteDAO CarteDAO = new CarteDAO();
    private CarteService carteServ = CarteService.instantiate();
    
    public List<Partie> listerPartiesNonDemarrees(){
        return partieDaoCrud.listerPartiesNonDemarrees();
    }
    
    
    public Partie creerNouvellePartie(String nom){
        Partie p = new Partie();
        p.setNom(nom);
        partieDaoCrud.save(p);
        return p;
    }
    
    public Partie demarrerPartie(Long idPartie){
        //Séléction de la partie par ID
        Partie p = dao.rechercherParId(idPartie);
        
        // Vérifie que le nombre de joueurs ne soit pas inférieur à 2
        if (p.getJoueurs().size() < 1){
            System.out.println("Vous n'avez pas assez de joueurs (Minimum:2 / Actuel:"+p.getJoueurs().size()+")");
        } else {
            //Change l'état du joueur 
            Joueur joueur1 = joueurDaoCrud.findOneByOrdreAndPartie(1L, p);
            
            joueur1.setEtatJoueur(Joueur.EtatJoueur.A_LA_MAIN);
            JoueurDAO.modifier(joueur1);
            joueurDaoCrud.save(joueur1);
            
            List<Joueur> joueursPartie = p.getJoueurs();
            
            //Attribut les cartes
            for (Joueur j : joueursPartie){
                
                for (int i = 0; i < 7; i++) {
                    carteServ.piocherCarte(j);
                }
            }
        }

        return p;
    }

    public List<Joueur> getJoueurs(Long idPartie) {
        return joueurDaoCrud.findAllByPartieId(idPartie);
//        return dao.getJoueurPartie(idPartie);
        
        
    }
    public Joueur joueurALaMain(Long idPartie) {
//       return dao.JoueurALaMain(idPartie);
       return joueurDaoCrud.findOneByEtatJoueurAndPartieId(atos.magiemagie.entity.Joueur.EtatJoueur.A_LA_MAIN, idPartie);
    }

    

    

}
