/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.main;

import atos.magiemagie.dao.CarteDAO;
import atos.magiemagie.dao.JoueurDAO;
import atos.magiemagie.dao.PartieDAO;
import atos.magiemagie.entity.Carte;
import atos.magiemagie.entity.Joueur;
import atos.magiemagie.entity.Partie;
import atos.magiemagie.service.CarteService;
import atos.magiemagie.service.JoueurService;
import atos.magiemagie.service.PartieService;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Administrateur
 */
public class Main {
    JoueurService joueurServ = new JoueurService();
    CarteService carteServ = CarteService.instantiate();
    PartieService partieServ = new PartieService();
    CarteDAO carteDao = new CarteDAO();
    JoueurDAO joueurDao = new JoueurDAO();
    PartieDAO partieDao = new PartieDAO();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
//        JoueurService joueurServ = new JoueurService();
//        CarteService carteServ = CarteService.instantiate();
//        CarteDAO carteDao = new CarteDAO();

          
       //panelAccueil();
       new Main();



        
        
    }
    
    public Main() throws InterruptedException{
        panelAccueil();
    }
    
    
    public void panelAccueil() throws InterruptedException{
        
        System.out.println("<----------------------------------------");
        System.out.println("---     Que souhaitez-vous faire?     ---");
        System.out.println("-----------------------------------------");
        System.out.println("---    1. Creer une nouvelle partie   ---");
        System.out.println("---    2. Rejoindre une partie        ---");
        System.out.println("---    3. Le saucisson, c'est bon!    ---");
        System.out.println("-----------------------------------------");
        
        Scanner actionScan = new Scanner(System.in);
        int actionChoix = actionScan.nextInt();
        
        if (actionChoix == 1) {
            System.out.println("Entrez le nom de la partie:");
            Scanner nomPartieScan = new Scanner(System.in);
            String nomPartie = nomPartieScan.nextLine();
            partieServ.creerNouvellePartie(nomPartie);
            System.out.println("La partie "+nomPartie+" a bien été créée.");
            panelAccueil();
            
        } else if (actionChoix == 2){
            
            List<Partie> partieNDM = partieServ.listerPartiesNonDemarrees();
            
            for (int i = 0; i < partieNDM.size(); i++) {
                
                System.out.println(partieNDM.get(i).getId()+": "+partieNDM.get(i).getNom());
                
            }
            System.out.println("Rejoindre quelle partie?");
            Scanner joinPartieScan = new Scanner(System.in);
            int joinPartie = joinPartieScan.nextInt();
//            System.out.println("Combien de joueurs?");
//            Scanner nbJoueurScan = new Scanner(System.in);
//            int nbJoueur = nbJoueurScan.nextInt();
            
//                for (int i = 0; i < nbJoueur; i++) {
            System.out.println("Veuillez entrer votre pseudo joueur ");
            Scanner nomJoueurScan = new Scanner(System.in);
            String nomJoueur = nomJoueurScan.nextLine();

            System.out.println("Veuillez choisir votre sorcière");
            Scanner avatarScan = new Scanner(System.in);
            String avatar = avatarScan.nextLine();

            joueurServ.rejoindrePartie(nomJoueur, avatar, joinPartie);
//                }
                panelLobby(joinPartie, nomJoueur);
            
        } else if (actionChoix == 3){
            
            System.out.println("Ca c'est bien vrai !");
            panelAccueil();
        
        } else {
            System.out.println("1, 2 ou 3, c'est trop compliqué pour ton cerveau étriqué? Allez recommence !");
            panelAccueil();
        }
        
        
        
    }
    
    
    private void panelLobby(long idPartie, String pseudoJoueur) throws InterruptedException {
        
        List<Joueur> joueurs = partieDao.getJoueurPartie(idPartie);
        Joueur j = joueurDao.rechercherParPseudo(pseudoJoueur);
        
        
        //Vérifie si la partie est lancée
        for (Joueur player : joueurs){
            if (player.getEtatJoueur().equals(Joueur.EtatJoueur.A_LA_MAIN))
                panelPlateauDeJeu(idPartie, pseudoJoueur);
        }
        System.out.println("Vous êtes actuellement "+joueurs.size());
        if (joueurs.size() < 2){
            int joueurNeed = 2-joueurs.size();
            System.out.println("--- Vous n'êtes pas assez pour jouer. En attente de "+joueurNeed+" joueur(s) ---");
            while(joueurs.size() < 2){
                joueurs = partieDao.getJoueurPartie(idPartie);
                Thread.sleep(1000);
            }
            panelLobby(idPartie, pseudoJoueur);
            
        } else { // Début du Else
           if (j.getOrdre() == 1) {
                    
                System.out.println("--- Voulez vous lancer la partie? Y: Oui / Q: Quit ---");
                Scanner startQuitScan = new Scanner(System.in);
                String startQuit = startQuitScan.nextLine();

                if (startQuit.equals("Y") ){

                    partieServ.demarrerPartie(idPartie);
                    panelPlateauDeJeu(idPartie, pseudoJoueur);

                } else if (startQuit.equals("Q")){

                        joueurServ.reOrder(idPartie, pseudoJoueur);
                        joueurDao.remove(pseudoJoueur);
                        System.out.println("--- Vous avez bien été retiré de la partie ---");

                   panelAccueil();

                } else {
                    System.out.println("--- Je n'ai pas compris votre demande. Catapultes tes morts, je te prie ---");
                    panelLobby(idPartie, pseudoJoueur);
                }
               
            } else {
               System.out.println("--- En attente du lancement de la partie ---");
               System.out.println("---     A: Actualiser / Q: Quitter       ---");
               Scanner attenteScan = new Scanner(System.in);
               String attente = attenteScan.nextLine();
               
               if (attente.equals("A")) {
                   panelLobby(idPartie, pseudoJoueur);
               } else if (attente.equals("Q")) {
                   joueurServ.reOrder(idPartie, pseudoJoueur);
                   System.out.println("--- Vous avez bien été retiré de la partie ---");
                   joueurDao.remove(attente);
               } else {
                   panelLobby(idPartie, pseudoJoueur);
               }
           }
            
            
        }//Fin du Else
    }
    
    
    public void panelPlateauDeJeu(Long idPartie, String pseudoJoueur) throws InterruptedException{
        
        Long joueurActif = joueurDao.compteJoueurActif(idPartie);
        System.out.println("Joueurs en lice: "+joueurActif);
        Joueur joueurALaMain = partieDao.JoueurALaMain(idPartie);
        
        if (joueurActif == 1){
            
            joueurALaMain.setEtatJoueur(Joueur.EtatJoueur.GAGNE);
            joueurDao.modifier(joueurALaMain);
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("--- "+joueurALaMain.getPseudo()+" est le vainqueur du combat de Magie ! ---");
            System.out.println("---------------------------------------------------------------------------------");
            carteServ.cleanCard(idPartie);
            panelLobby(idPartie, pseudoJoueur);
            
        } else {
         if (!joueurALaMain.getPseudo().equals(pseudoJoueur)){
             System.out.println("--- C'est au tour de "+joueurALaMain.getPseudo()+" de jouer ! ---");
             while (!joueurALaMain.getPseudo().equals(pseudoJoueur)) {                 
                 
                 joueurALaMain = partieDao.JoueurALaMain(idPartie);
                 Thread.sleep(1000);
             }
             
             tourDeJeu(joueurALaMain.getPseudo(), idPartie);
             
         } else {
             tourDeJeu(pseudoJoueur, idPartie);
         }
        
        
        }
        
    }
    
    public void tourDeJeu(String pseudoJoueurActif, Long idPartie) throws InterruptedException{
        Partie partie = partieDao.rechercherParId(idPartie);
        System.out.println("-------------------------------------------------");
        System.out.println("---          C'est à vous de jouer !          ---");
        System.out.println("-------------------------------------------------");
        System.out.println("--- Quelle est votre prochaine action? 0: Lancer un sort | 1: Passer son tour ---");
        joueurServ.afficherCarte(pseudoJoueurActif, idPartie);
        Scanner actionScan = new Scanner(System.in);
        int actionChoisie = actionScan.nextInt();
        
        
        if (actionChoisie == 0){
            System.out.println("--------------------------------------------------");
            System.out.println("---              Liste des sorts               ---");
            System.out.println("---                                            ---");
            System.out.println("---      Divination : Lapis & aile de ChS      ---");
            System.out.println("---      Invisibilité : Licorne & Crapaud      ---");
            System.out.println("---      Hypnose : Licorne & Mandragore        ---");
            System.out.println("---      Philtre d'Amour : Lapis & Crapaud     ---");
            System.out.println("--- Sommeil Profond : Aile de ChS & Mandragore ---");
            System.out.println("--------------------------------------------------");
            System.out.println("---      Choisissez la première carte          ---");
            Carte carte1 = carteServ.choisirCarte();

            System.out.println("---      Choisissez la seconde carte           ---");
            Carte carte2 = carteServ.choisirCarte();
    //        
            System.out.println("---  Vous avez choisi:"+carte1.getTypeCarte()+" & "+carte2.getTypeCarte()+"  ---");
       
            //Lancement du sort
            carteServ.lancerSort(carte1.getId(), carte2.getId(), pseudoJoueurActif, idPartie);
        } else if (actionChoisie == 1){
            
            joueurServ.passerSonTour(pseudoJoueurActif, idPartie);
            
        } else {
            System.out.println("--- Choix non valide, veuillez choisir un numéro de la liste ---");
            tourDeJeu(pseudoJoueurActif, idPartie);
        }
        
        //Check les perdants
        
        List<Joueur> joueurs = partie.getJoueurs();
        for (Joueur j : joueurs){
            if (j.getCartes().isEmpty())
                j.setEtatJoueur(Joueur.EtatJoueur.PERDU);
                joueurDao.modifier(j);
            
        }
        
        panelPlateauDeJeu(idPartie, pseudoJoueurActif);
        
        
        
//        
    }

    

    
    
}
