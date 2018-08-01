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
import atos.magiemagie.main.Main;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class CarteService {
    
    private CarteDAO carteDAO = new CarteDAO();
    private JoueurDAO joueurDAO = new JoueurDAO();
    private PartieDAO PartieDAO = new PartieDAO();
    private JoueurService joueurServ = new JoueurService();
    private static CarteService singleton = new CarteService();
//    private PartieService partieServ = new PartieService();
    
    private CarteService(){}
    
    public static CarteService instantiate(){
        
        return CarteService.singleton;
    }
    
    public Carte piocherCarte(Joueur joueur){
        Carte carte = new Carte();
                    Carte.TypeCarte ingredient[] = Carte.TypeCarte.values();
                    Random r = new Random();
                    int random = r.nextInt(ingredient.length);
                    
                   
                    carte.setTypeCarte(ingredient[random]);
                    carte.setJoueur(joueur);
                    carteDAO.addCarte(carte);
                    return carte;
                    
    }
    
    public void lancerSort(Long idCarte1, Long idCarte2, String joueurPseudo, Long idPartie){
        //Récuperation de la partie
        Partie partie = PartieDAO.rechercherParId(idPartie);
        
        //Récuperation du joueur
        Joueur joueur = joueurDAO.rechercherParPseudo(joueurPseudo);
        
        // Récupération des deux cartes choisies
        Carte carte1 = carteDAO.rechercherParId(idCarte1);
        Carte carte2 = carteDAO.rechercherParId(idCarte2);
        String card1Compare = carte1.getTypeCarte().toString();
        String card2Compare = carte2.getTypeCarte().toString();
        
        //Check dans le tableau des sorts si correspondance
        if ( (card1Compare == "CORNE_LICORNE" && card2Compare == "BAVE_CRAPAUD") || (card1Compare == "BAVE_CRAPAUD" && card2Compare == "CORNE_LICORNE"))
            sortInvisibilite(idPartie, joueurPseudo);
        else if ( (card1Compare == "CORNE_LICORNE" && card2Compare == "MANDRAGORE") || (card1Compare == "MANDRAGORE" && card2Compare == "CORNE_LICORNE"))
            sortHypnose(idPartie, joueurPseudo);
        else if ((card1Compare == "LAPIS_LAZULI" && card2Compare == "AILE_CHAUVE_SOURIS") || (card1Compare == "AILE_CHAUVE_SOURIS" && card2Compare == "LAPIS_LAZULI"))
            sortDivination(idPartie);
        else if ((card1Compare == "LAPIS_LAZULI" && card2Compare == "BAVE_CRAPAUD") || (card1Compare == "BAVE_CRAPAUD" && card2Compare == "LAPIS_LAZULI"))
            sortPhiltreAmour(joueurPseudo, idPartie);
        else if ((card1Compare == "AILE_CHAUVE_SOURIS" && card2Compare == "MANDRAGORE") || (card1Compare == "MANDRAGORE" && card2Compare == "AILE_CHAUVE_SOURIS"))
            sortSommeilProfond();
        
        
        //Supprime les cartes de la main du joueur
        carteDAO.supprimerCarte(carte1);
        carteDAO.supprimerCarte(carte2);
        
        
        //Passe à un autre joueur
        joueurServ.definirJoueurSuivant(joueurPseudo, idPartie);
        
        
    } 
    
    
    public void sortInvisibilite(Long idPartie, String nomLanceur) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("---------------------------");
        System.out.println("--- Invisibilité lancée ---");
        System.out.println("---------------------------");
        List<Joueur> joueurs = PartieDAO.getJoueurPartie(idPartie);
        
        for (Joueur joueur : joueurs){
            volerUneCarte(nomLanceur, joueur.getPseudo(), idPartie);
        }
    }
    
    public void sortHypnose(Long idPartie, String nomLanceur) {
        System.out.println("----------------------");
        System.out.println("--- Hypnose lancée ---");
        System.out.println("----------------------");
        Joueur joueurLanceur = joueurDAO.rechercherParPseudo(nomLanceur);
         if (joueurLanceur.getCartes().size() < 3){
             System.out.println("--- Vous n'avez pas assez de cartes pour lancer ce sort. Veuillez catapulter vos morts ---");
             joueurServ.passerSonTour(nomLanceur, idPartie);
         }
        
        Joueur joueurCible = choisirCible();
        
        if (joueurCible == null) {
            while (joueurCible == null) {                
                
            System.out.println("--- Aucun joueur à cette table ne porte ce nom. Veuillez entrer un nom valide ---");
            joueurCible = choisirCible();
            }
        }
        
        Partie partie = PartieDAO.rechercherParId(idPartie);
        List<Carte> joueurCibleCarte = carteDAO.getJoueurCarte(joueurCible.getPseudo(), partie);
        
        
        if (joueurCible.getCartes().size() < 3 ){
            for (int i = 0; i < joueurCibleCarte.size(); i++){
                volerUneCarte(nomLanceur, joueurCible.getPseudo(), idPartie);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                volerUneCarte(nomLanceur, joueurCible.getPseudo(), idPartie);
            }
        }
        System.out.print("--- Choisissez une carte: ");
        Scanner selectCard = new Scanner(System.in);
        Long card = selectCard.nextLong();
        Carte carteChoisi = carteDAO.rechercherParId(card);
        carteChoisi.setJoueur(joueurCible);
        carteDAO.modifier(carteDAO.rechercherParId(card));
        
        
        
        
    }

    public void sortDivination(Long idPartie) {
        System.out.println("-------------------------");
        System.out.println("--- Divination lancée ---");
        System.out.println("-------------------------");
   Partie partie = PartieDAO.rechercherParId(idPartie);
   List<Joueur> joueurs = PartieDAO.getJoueurPartie(idPartie);
   
   for (Joueur joueur : joueurs){
       System.out.println("--- "+joueur.getPseudo()+" possède dans sa main les cartes suivantes:");
       List<Carte> cartesJoueur = carteDAO.getJoueurCarte(joueur.getPseudo(), partie);
        for (Carte carteJoueur : cartesJoueur){
            System.out.print(carteJoueur.getTypeCarte()+" - ");
        }
        System.out.println("");
   }
        
        
    }

    public void sortPhiltreAmour(String pseudoLanceur, Long idPartie) {
        System.out.println("-----------------------------");
        System.out.println("--- Philtre d'Amour lancé ---");
        System.out.println("-----------------------------");
        Joueur joueurCible = choisirCible();
        List<Carte> cartesJoueur = joueurCible.getCartes();
        int nbCartesVolees;
        if (cartesJoueur.size() % 2 == 0){
            nbCartesVolees = cartesJoueur.size()/2;
            for (int i = 0; i < nbCartesVolees; i++) {
                volerUneCarte(pseudoLanceur, joueurCible.getPseudo(), idPartie);
            }
        } else if (cartesJoueur.size() == 1){
            volerUneCarte(pseudoLanceur, joueurCible.getPseudo(), idPartie);
        }else{
            nbCartesVolees = ((cartesJoueur.size() - 1)/2)+1;
            for (int i = 0; i < nbCartesVolees; i++) {
                volerUneCarte(pseudoLanceur, joueurCible.getPseudo(), idPartie);
            }
        }
        
    }

    public void sortSommeilProfond() {
        
        System.out.println("-----------------------------");
        System.out.println("--- Sommeil profond lancé ---");
        System.out.println("-----------------------------");
        Joueur joueurCible = choisirCible();
        
        if (joueurCible == null) {
            while (joueurCible == null) {                
                
            System.out.println("--- Aucun joueur à cette table ne porte ce nom. Veuillez entrer un nom valide ---");
            Scanner selectTarget = new Scanner(System.in);
            String cible = selectTarget.nextLine();
            joueurCible = joueurDAO.rechercherParPseudo(cible);
            }
        }
        
        joueurCible.setEtatJoueur(Joueur.EtatJoueur.SOMMEIL_PROFOND);
        joueurDAO.modifier(joueurCible);
        System.out.println("--- "+joueurCible.getPseudo()+" dort jusqu'au tour prochain ---");
        
        
    }
    
    
    public void volerUneCarte(String pseudoLanceur, String pseudoCible, Long idPartie) {
        
        Joueur joueur = joueurDAO.rechercherParPseudo(pseudoLanceur);
        Partie partie = PartieDAO.rechercherParId(idPartie);
        
        List<Carte> cartes = carteDAO.getJoueurCarte(pseudoCible, partie);
        
        //Création du random number
        Random r = new Random();
        int random = r.nextInt(cartes.size());
        Carte carteVolee = cartes.get(random);
        
        //Change de proprio la carte volée
        carteVolee.setJoueur(joueur);
        carteDAO.modifier(carteVolee);
        
    }

    public Joueur choisirCible() {
        System.out.print("--- Choisissez votre cible: ");
        Scanner selectTarget = new Scanner(System.in);
        String cible = selectTarget.nextLine();
        Joueur joueurCible = joueurDAO.rechercherParPseudo(cible);
        
        if (joueurCible.getEtatJoueur().equals(Joueur.EtatJoueur.PERDU)) {
            System.out.println("--- Ce joueur ne peut être ciblé ---");
            choisirCible();
        }
        return joueurCible;
    }
    
    public Carte choisirCarte(){
        Scanner scan1 = new Scanner(System.in);
        Long carte1 = scan1.nextLong();
        Carte card = carteDAO.rechercherParId(carte1);
        return card;
    }

    public void cleanCard(Long idPartie) {
        
        Partie partie = PartieDAO.rechercherParId(idPartie);
        List<Joueur> joueurs = partie.getJoueurs();
        
        for (Joueur j : joueurs){
            List<Carte> jCartes = j.getCartes();
            for (Carte c : jCartes){
                carteDAO.supprimerCarte(c);
            }
        }
        
    }
    
    
}
