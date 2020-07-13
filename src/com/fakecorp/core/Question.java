package com.fakecorp.core;


// Une question du quiz
public class Question {
	  static final int nbRepQst = 4;
	  private String enonceQuestion;           // la question
	  private String [] repPossibles;          // liste des réponses possibles
	  private int score;                       // le barème de la question
	  private int bonneRepIndx;                // la position de la réponse correcte

   public Question()
	  {
		  repPossibles = new String [nbRepQst];        // 4 réponses possibles pour chaque question
	  }

	public String getEnonceQuestion() {
		return enonceQuestion;
	}

	public String[] getRepPossibles() {
			return repPossibles;
		}
    public int getScore() {
		return score;
	}

	public int getBonneRepIndx() {
		return bonneRepIndx;
	}

	public void setEnonceQuestion(String enonceQuestion) {
		this.enonceQuestion = enonceQuestion;
	}
	public void setRepPossibles(String[] repPossibles) {
		this.repPossibles = repPossibles;
	}	
	public void setScore(int score) {
		this.score = score;
	}

	public void setBonneRepIndx(int bonneRepIndx) {
		this.bonneRepIndx = bonneRepIndx;
	}
	
      // vérifier si la réponse choisie est juste
 	public boolean repJuste(int reponse)
	  {
	    if (bonneRepIndx == reponse) return true; else return false; 
	  }

	
}
