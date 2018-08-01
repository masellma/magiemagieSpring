/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.dao;

import atos.magiemagie.entity.Carte;
import atos.magiemagie.entity.Joueur;
import atos.magiemagie.entity.Partie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class PartieDAO {
    
    public List<Partie> listerPartiesNonDemarrees(){
        
        EntityManager em =  Persistence.createEntityManagerFactory("PU").createEntityManager();
        
          Query query = em.createQuery("SELECT p FROM Partie p EXCEPT SELECT p FROM Partie p JOIN p.joueurs j WHERE j.etatJoueur IN (:EtatJoueur_gagne, :EtatJoueur_alamain)");
          query.setParameter("EtatJoueur_gagne", Joueur.EtatJoueur.GAGNE);
          query.setParameter("EtatJoueur_alamain", Joueur.EtatJoueur.A_LA_MAIN);
        
        return query.getResultList();
        
    }
    
    public void ajouterPartie(Partie p){
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }
    
    public List<Carte> listerCartesJoueurs(){
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT c FROM Carte c ");
         return query.getResultList();
    }

    public Partie rechercherParId(long idPartie) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        return em.find(Partie.class, idPartie);
        
     }
    
    public List<Joueur> getJoueurPartie(Long idPartie) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT j FROM Joueur j JOIN j.partieActuelle p WHERE p.id = :idPartie");
        query.setParameter("idPartie", idPartie);
        
        List<Joueur> joueurs = query.getResultList();
        return joueurs;
        
    }

    public Joueur JoueurALaMain(Long idPartie) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT j FROM Joueur j JOIN j.partieActuelle p WHERE j.etatJoueur=:etatJoueur AND p.id = :idPartie ");
        query.setParameter("idPartie", idPartie);
        query.setParameter("etatJoueur", Joueur.EtatJoueur.A_LA_MAIN);
        
        List<Joueur> joueurs = query.getResultList();
        Joueur joueur = joueurs.get(0);
        return joueur;
    }
}
