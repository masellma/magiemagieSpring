/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.service;

import atos.magiemagie.dao.CarteDAO;
import atos.magiemagie.dao.JoueurDAO;
import atos.magiemagie.dao.PartieDAO;
import atos.magiemagie.entity.Carte;
import atos.magiemagie.entity.Joueur;
import atos.magiemagie.entity.Partie;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrateur
 */
@Service
public class JoueurService {
    
    private JoueurDAO dao = new JoueurDAO();
    private PartieDAO partieDao = new PartieDAO();
    private CarteDAO CarteDao = new CarteDAO();
    private CarteService carteServ = CarteService.instantiate();
    
    public Joueur rejoindrePartie(String nomJoueur, String avatar, long idPartie){
        
        //Recherche si le joueur existe déjà
        Joueur joueur = dao.rechercherParPseudo(nomJoueur);
        if (joueur == null){
            // Le joueur n'existe pas encore
            joueur = new Joueur();
            joueur.setPseudo(nomJoueur);
            joueur.setNbPartiesGagnees(0);
            joueur.setNbPartiesJouees(0);
        }
        
        joueur.setAvatar(avatar);
        joueur.setEtatJoueur(Joueur.EtatJoueur.PAS_LA_MAIN);
        joueur.setOrdre(dao.definirOrdre(idPartie));
        
        // Associe le joueur à la partie et vice-versa
        Partie p = partieDao.rechercherParId(idPartie);
        joueur.setPartieActuelle(p);
        List<Joueur> listeJoueurs = p.getJoueurs();
        listeJoueurs.add(joueur);
        
        if (joueur.getId() ==  null) {
            dao.ajouter(joueur);
        } else {
            dao.modifier(joueur);
        }
        
        return joueur;
        
    }
    
    public void passerSonTour(String pseudo, Long idPartie){
        Joueur joueur = dao.rechercherParPseudo(pseudo);
        
        carteServ.piocherCarte(joueur);
        definirJoueurSuivant(pseudo, idPartie);
        
    }
    
    
    public Joueur definirJoueurSuivant(String pseudo, Long idPartie){
        
        Partie partie = partieDao.rechercherParId(idPartie);
        //Création d'une nouvelle entité joueur
        Joueur joueur = new Joueur();
        //Association de l'objet joueur à son contenu de BDD
        joueur = dao.rechercherParPseudo(pseudo);
        //Le joueur pioche une carte
        //Change EtatJoueur du joueur en PAS_LA_MAIN
        joueur.setEtatJoueur(Joueur.EtatJoueur.PAS_LA_MAIN);
        dao.modifier(joueur);
        
        // Instance du joueur suivant
        Joueur joueurSuivant = new Joueur();
        Long ordreSuivant = joueur.getOrdre()+1;
        Long max = (long) partie.getJoueurs().size();
        
        
        
        if (ordreSuivant > max) 
            joueurSuivant = dao.rechercherParOrdre(1L, idPartie);
        else
            joueurSuivant = dao.rechercherParOrdre(ordreSuivant, idPartie);
            
        if (!joueurSuivant.getEtatJoueur().equals(Joueur.EtatJoueur.PAS_LA_MAIN) ){
            while (!joueurSuivant.getEtatJoueur().equals(Joueur.EtatJoueur.PAS_LA_MAIN)) {  
                
                if (joueurSuivant.getEtatJoueur().equals(Joueur.EtatJoueur.SOMMEIL_PROFOND)){
                    joueurSuivant.setEtatJoueur(Joueur.EtatJoueur.PAS_LA_MAIN);
                    dao.modifier(joueurSuivant);
                }
                
                ordreSuivant++;
                if (ordreSuivant > max)
                    joueurSuivant = dao.rechercherParOrdre(1L, idPartie);
                else
                    joueurSuivant = dao.rechercherParOrdre(ordreSuivant, idPartie);
            }
        }
            joueurSuivant.setEtatJoueur(Joueur.EtatJoueur.A_LA_MAIN);
            dao.modifier(joueurSuivant);
        return joueur;
    }
    
    public void reOrder(Long idPartie, String pseudoJoueur){
        
        Joueur joueur = dao.rechercherParPseudo(pseudoJoueur);
        List<Joueur> joueurs = dao.reOrderQuery(joueur.getOrdre(), idPartie);
        
        if (joueurs == null){
            System.out.println("--- Aucun changement disponible ---");
        } else {
            for (Joueur j : joueurs){
                Long reOrder = j.getOrdre()-1;
                System.out.print(j.getOrdre()+" -> ");
                System.out.println(reOrder);
                j.setOrdre(reOrder);
                dao.modifier(j);
            }
        }
        
    }

    public void afficherCarte(String pseudoJoueurActif, Long idPartie) {
        
        Joueur joueur = dao.rechercherParPseudo(pseudoJoueurActif);
        Partie p = partieDao.rechercherParId(idPartie);
        List<Carte> cartesJoueur = CarteDao.getJoueurCarte(pseudoJoueurActif, p);
        
        for (Carte carte : cartesJoueur){
            System.out.println(carte.getId()+":"+carte.getTypeCarte()+" | ");
        }
        
    }

    public boolean joueurExist(String login) {

        if(dao.joueurExist(login) == true){
            return true;
        } else {
            return false;
        }
        
    }

    public Joueur joueurByName(String login) {

        return dao.rechercherParPseudo(login);
    }
    
    
    
    
    
    
    
    
    
}
