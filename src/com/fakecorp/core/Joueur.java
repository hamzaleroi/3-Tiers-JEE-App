package com.fakecorp.core;


// Les données concernant le joueur
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class Joueur {
    private String idJoueur;
    private String nomJouer;
    private String mail;
    private List<Partie> partiesJouees;
    
   public Joueur() {
	   super();
	   this.partiesJouees = new ArrayList<Partie>();
   }
    public String getIdJoueur() {
		return idJoueur;
	}
	public String getNomJouer() {
		return nomJouer;
	}
	public String getMail() {
		return mail;
	}
	public List<Partie> getPartiesJouees() {
		return partiesJouees;
	}
	public void setIdJoueur(String idJoueur) {
		this.idJoueur = idJoueur;
	}
	public void setNomJouer(String nomJouer) {
		this.nomJouer = nomJouer;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
// on charge la liste des parties (historique)	
	public void setPartiesJouees(List<Partie> partiesJouees) {
		this.partiesJouees = partiesJouees;
	} 
//ajouter une partie jouée à la liste 
   public void sauvPartie(Partie partieGardee, Vector <Integer> qestJouee) {
	   Partie lotQuestJoue = partieGardee;
	   
	   for (int rang=0; rang<lotQuestJoue.getSerieQuestions().size();rang++) 
		   if(! qestJouee.contains(rang)) lotQuestJoue.getSerieQuestions().remove(rang);
	   
	   this.partiesJouees.add(lotQuestJoue);
   }
    
}
