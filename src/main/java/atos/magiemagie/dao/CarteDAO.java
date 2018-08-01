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
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.text.html.HTML;

/**
 *
 * @author Administrateur
 */
public class CarteDAO {
    
    
    JoueurDAO joueurDao = new JoueurDAO();
    PartieDAO partieDao = new PartieDAO();
    
    public void addCarte(Carte card) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
                
        em.getTransaction().begin();
        em.persist(card);
        em.getTransaction().commit();
        
    }

    public Carte rechercherParId(Long idCarte) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT c FROM Carte c JOIN c.joueur j WHERE c.id = :idCarte");
        query.setParameter("idCarte", idCarte);
        
        List<Carte> cartes = query.getResultList();
        return cartes.get(0);
    }


    public void supprimerCarte(Carte carte) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Carte card = em.find(Carte.class, carte.getId());
        
        em.getTransaction().begin();
        em.remove(card);
        em.getTransaction().commit();
        
        
    }

    public List<Carte> getJoueurCarte(String pseudoCible, Partie partie){
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT c FROM Carte c JOIN c.joueur j JOIN j.partieActuelle p WHERE j.pseudo = :pseudo AND j.partieActuelle = :idPartie ");
        query.setParameter("pseudo", pseudoCible);
        query.setParameter("idPartie", partie);
        
        List<Carte> cartes = query.getResultList();
        return cartes;
    }

    public void modifier(Carte carteVolee) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        em.getTransaction().begin();
        em.merge(carteVolee);
        em.getTransaction().commit();
        
    }

    
    
    
    
    
    
    
    
}
