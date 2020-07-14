package com.fakecorp.core;



public interface IMatch {

	void setInfoJoueur(String[] infoJoueur);
	
	void setBanqueFichier(String ficheBanque);

	// récuperer le score du joueur
	int getScore();

	String getIdJoueur();

	String[] tirerQuestion();

	
	int getNumQuestCour();

	// vérifier la réponse donnée était bonne.
	boolean estceBonneRep(int repElue);

	int getCptQstJouee();

	Partie getManche();

	void incCptQstJouee();


	void setTracRepJoueur(int rep);

}