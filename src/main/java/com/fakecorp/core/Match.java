package com.fakecorp.core;



// t�che principal de Match = g�rer une partie de jeux du quiz
import com.fakecorp.DAO.QuestionRepositoryImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;



public class Match implements IMatch {
 private FileReader questBanque;           // la banque de question � utiliser dans le match
 private BufferedReader parcourBanque;
 private Joueur joueur;                  // identification du joueur
 private Partie manche;                    // la partie � jouer 
 private int numQuestCour;                 // le num�ro de la question en cours dans la banque - al�atoire
 private int cptQstJouee;                  // compteur des questions jou�es - s�quentiel
 private Vector<Integer> tracQuest;       //  liste des questions d�j� pos�es dans la partie
 private Vector<Integer> tracRepJoueur;   // Les r�ponses donn�e par le joueur
 
 // Construire le match en initialisant la liste des questions et lancer une partie
 
 public Match() {
	joueur = new Joueur();
	this.numQuestCour =0;     // le num�ro de la question en cours dans la banque des questions(toujours tirer al�atoiement)
    this.cptQstJouee=0;         // le num�ro s�quentielle de la question jou�e � rendre au joueur.
    this.tracQuest = new Vector<Integer>();        // la trace est vide (pas de questions d�j� tir�es)
    this.tracRepJoueur = new Vector<Integer>();       // la trace des r�ponses donn�es 
 }
 
 public void setInfoJoueur(String[] infoJoueur) {
    

    joueur.setIdJoueur(infoJoueur[0]);
    joueur.setNomJouer(infoJoueur[1]);
    joueur.setMail(infoJoueur[2]);
    
 // cr�er la partie (la manche) en chargant les questions du quiz
    this.manche = new Partie(infoJoueur[0]);

    this.manche.setQuestionRepository(new QuestionRepositoryImpl());
	
 }

 public void setBanqueFichier(String ficheBanque) {
 	 System.out.println(ficheBanque);
	 File f = new File(ficheBanque);
	 String[] lnQuestion;
	 try {
		 this.questBanque = new FileReader(f);
		 this.parcourBanque = new BufferedReader(questBanque);
		 while (parcourBanque.ready()) {
			  lnQuestion = parcourBanque.readLine().split(",");
			  if (! lnQuestion[0].equalsIgnoreCase("enonce")) // eviter la ligne de l'ent�te du fichier de la banque de question
			      this.manche.setSerieQuestions(lnQuestion);   
		 }
		 this.manche.persistData();
		 parcourBanque.close();
		 questBanque.close();
	} catch (FileNotFoundException e) {e.printStackTrace(); }
	  catch (IOException e) {e.printStackTrace();}
 }
 // r�cuperer le score du joueur
 public int getScore() {
		return manche.getDerSocre();
	}

 // r�cuperer l'id du joueur

public String getIdJoueur() {
		return joueur.getIdJoueur();
 }
 
  //Tirage d'une question
 
public String [] tirerQuestion() {
	 Random genNumQuest = new Random(System.currentTimeMillis());
	 String [] uneQuest = null;
	// prendre une question au hasard mais qui n'a pas d�j� �t� utilis�e
	 while (tracQuest.size() < manche.lengSerieQuest()) {       // stop si on a epuis� la banque des questions
	      numQuestCour=genNumQuest.nextInt(manche.lengSerieQuest());
	      if (! tracQuest.contains(numQuestCour)) {
	    	  tracQuest.add(numQuestCour);                      // on garde trace du num�ro de la question
	    	  uneQuest = manche.playQuestion(numQuestCour);
	    	  break;
	      }
	 }   
	 return uneQuest;
}

 public int getNumQuestCour() {
	return numQuestCour; 
}

// v�rifier la r�ponse donn�e �tait bonne.
 public boolean estceBonneRep(int repElue){
	 tracRepJoueur.add(repElue);
     return manche.verifRep(numQuestCour, repElue);
 }
 
 
//num�ro d'ordre des questions

public int getCptQstJouee() {
	return cptQstJouee;
}


public Partie getManche() {
	return manche;
}

//incr�menter le nombre de questions jou�es

public void incCptQstJouee() {
	this.cptQstJouee++;
}
 
 
// fin du match : on  sauvegarde
 
public void stopMatch(String stockPartie) {
	 File f = new File(stockPartie);
	 joueur.sauvPartie(manche,tracQuest);
	 try {   // Sauvegarde de la partie du joueur : son identiti� et les questions jou�es
		     FileWriter sauvResMatch = new FileWriter(f);
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


