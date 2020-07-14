package com.fakecorp.core;


import com.fakecorp.core.IMatch;
import com.fakecorp.core.Match;
import com.fakecorp.core.Partie;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/testservice")
public class MatchController implements IMatch {
	
	
	Match match = null;
	
	public MatchController() {
		match = new Match();
	}
	
	
	 public boolean estceBonneRep(int repElue){
		 return match.estceBonneRep(repElue);
	 }




	 @GET
	public int getScore() {
		// TODO Auto-generated method stub
		return match.getScore();
	}


	public String getIdJoueur() {
		// TODO Auto-generated method stub
		return match.getIdJoueur();
	}


	public String[] tirerQuestion() {
		// TODO Auto-generated method stub
		return match.tirerQuestion();
	}


	public int getNumQuestCour() {
		// TODO Auto-generated method stub
		return match.getNumQuestCour();
	}


	public int getCptQstJouee() {
		// TODO Auto-generated method stub
		return match.getCptQstJouee();
	}


	public Partie getManche() {
		// TODO Auto-generated method stub
		return match.getManche();
	}


	public void incCptQstJouee() {
		// TODO Auto-generated method stub
		match.incCptQstJouee();
		
	}


	public void stopMatch(String stockPartie) {
		// TODO Auto-generated method stub
		match.stopMatch(stockPartie);
	}


	public void setTracRepJoueur(int rep) {
		// TODO Auto-generated method stub
		match.setTracRepJoueur(rep);
	}


	public void setInfoJoueur(String[] infoJoueur) {
		// TODO Auto-generated method stub
		match.setInfoJoueur(infoJoueur);
	}


	public void setBanqueFichier(String ficheBanque) {
		// TODO Auto-generated method stub
		
		match.setBanqueFichier(ficheBanque);
		
	}
	 

}
