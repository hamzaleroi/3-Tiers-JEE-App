package com.fakecorp.core;



// tâche principal de Match = gérer une partie de jeux du quiz
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;



public class Match {
 private FileReader questBanque;           // la banque de question à utiliser dans le match
 private BufferedReader parcourBanque;
 private Joueur joueur;                  // identification du joueur
 private Partie manche;                    // la partie à jouer 
 private int numQuestCour;                 // le numéro de la question en cours dans la banque - aléatoire
 private int cptQstJouee;                  // compteur des questions jouées - séquentiel
 private Vector<Integer> tracQuest;       //  liste des questions déjà posées dans la partie
 private Vector<Integer> tracRepJoueur;   // Les réponses donnée par le joueur
 
 // Construire le match en initialisant la liste des questions et lancer une partie
 public Match(File fichBanque, String[] infoJoueur) {
    String[] lnQuestion;
  // créer le joueur à partir les infos fournis
    joueur = new Joueur();
    joueur.setIdJoueur(infoJoueur[0]);
    joueur.setNomJouer(infoJoueur[1]);
    joueur.setMail(infoJoueur[2]);
      
    this.numQuestCour =0;     // le numéro de la question en cours dans la banque des questions(toujours tirer aléatoiement)
    this.cptQstJouee=0;         // le numéro séquentielle de la question jouée à rendre au joueur.
    this.tracQuest = new Vector<Integer>();        // la trace est vide (pas de questions déjà tirées)
    this.tracRepJoueur = new Vector<Integer>();       // la trace des réponses données 
    
 // créer la partie (la manche) en chargant les questions du quiz
    this.manche = new Partie(infoJoueur[0]);
	try {
		 this.questBanque = new FileReader(fichBanque);
		 this.parcourBanque = new BufferedReader(questBanque);
		 while (parcourBanque.ready()) {
			  lnQuestion = parcourBanque.readLine().split(";");
			  if (! lnQuestion[0].equalsIgnoreCase("enonce")) // eviter la ligne de l'entête du fichier de la banque de question
			      this.manche.setSerieQuestions(lnQuestion);   
		 }
		 parcourBanque.close();
		 questBanque.close();
	} catch (FileNotFoundException e) {e.printStackTrace(); }
	  catch (IOException e) {e.printStackTrace();}
 }

 // récuperer le score du joueur
 public int getScore() {
		return manche.getDerSocre();
	}

 // récuperer l'id du joueur
 public String getIdJoueur() {
		return joueur.getIdJoueur();
 }
 
  //Tirage d'une question
 public String [] tirerQuestion() {
	 Random genNumQuest = new Random(System.currentTimeMillis());
	 String [] uneQuest = null;
	// prendre une question au hasard mais qui n'a pas déjà été utilisée
	 while (tracQuest.size() < manche.lengSerieQuest()) {       // stop si on a epuisé la banque des questions
	      numQuestCour=genNumQuest.nextInt(manche.lengSerieQuest());
	      if (! tracQuest.contains(numQuestCour)) {
	    	  tracQuest.add(numQuestCour);                      // on garde trace du numéro de la question
	    	  uneQuest = manche.playQuestion(numQuestCour);
	    	  break;
	      }
	 }   
	 return uneQuest;
}

 public int getNumQuestCour() {
	return numQuestCour; 
}

// vérifier la réponse donnée était bonne.
 public boolean estceBonneRep(int repElue){
	 tracRepJoueur.add(repElue);
     return manche.verifRep(numQuestCour, repElue);
 }
 
 
//numéro d'ordre des questions
public int getCptQstJouee() {
	return cptQstJouee;
}

public Partie getManche() {
	return manche;
}

//incrémenter le nombre de questions jouées
public void incCptQstJouee() {
	this.cptQstJouee++;
}
 
 
// fin du match : on  sauvegarde
 public void stopMatch(File stockPartie) {
	 joueur.sauvPartie(manche,tracQuest);
	 try {   // Sauvegarde de la partie du joueur : son identitié et les questions jouées
		     FileWriter sauvResMatch = new FileWriter(stockPartie);
			 sauvResMatch.write(joueur.getIdJoueur()+";");        
			 sauvResMatch.write(joueur.getNomJouer()+";");
			 sauvResMatch.write(joueur.getMail());
			 for (int i=0; i<joueur.getPartiesJouees().size();i++) {
				sauvResMatch.write(String.valueOf(";"+joueur.getPartiesJouees().get(i).getDerSocre()));
				for (int j=0; j<joueur.getPartiesJouees().get(i).getSerieQuestions().size();j++) {
				  sauvResMatch.write(";"+joueur.getPartiesJouees().get(i).getSerieQuestions().get(j).getEnonceQuestion());
				  sauvResMatch.write(";joueurRep:"+ String.valueOf(tracRepJoueur.get(j))); 
				}
			 }
			 sauvResMatch.write("\n");
			 sauvResMatch.close();
		} catch (FileNotFoundException e) { e.printStackTrace(); }
		  catch (IOException e) {e.printStackTrace();}
     this.numQuestCour =0;
 }

public void setTracRepJoueur(int rep) {
	tracRepJoueur.add(rep);
	
}

 

}// Fin de la classe Match


