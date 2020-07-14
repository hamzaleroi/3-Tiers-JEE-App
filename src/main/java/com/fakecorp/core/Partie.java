package com.fakecorp.core;


// gerer les donn�es d'une partie de jeu et le score

import com.fakecorp.DAO.Question;
import com.fakecorp.DAO.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Partie {
	private List<Question> serieQuestions;       // la s�rie de questions jou�es dans la partie
	private QuestionRepository questionRepository;
	private int scorePartie;                     // le score obtenu dans la partie 
	private String idJoueur;                     // le joueur
	
	
	public Partie(String idJoueur) {
		super();
		this.serieQuestions = new ArrayList<>();
		this.scorePartie = 0;                            // le score de d�part est nul
		this.idJoueur = idJoueur;
	}

	public QuestionRepository getQuestionRepository() {
		return questionRepository;
	}

	public void setQuestionRepository(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}

	public List<Question> getSerieQuestions() {
		return serieQuestions;
	}
	public int getDerSocre() {
		return scorePartie;
	}
	public String getIdJoueur() {
		return idJoueur;
	}
	
// charger une question dans le vecteur des questions � jouer dans la partie
	public void setSerieQuestions(String [] uneQuestion) {

		for (String s:uneQuestion){
			System.out.println(s);
		}
		Question quest = new Question();
		String [] rpPossible = new String[Question.nbRepQst];
		quest.setEnonceQuestion(uneQuestion[0]);               //le texte de la question
		for (int i=0;i<Question.nbRepQst;i++) rpPossible[i]=uneQuestion[i+1]; // ses r�ponses possibles
		quest.setRepPossibles(rpPossible);
		quest.setBonneRepIndx(Integer.valueOf(uneQuestion[Question.nbRepQst+1])-1); // l'indice de la bonne r�ponse bas� 0 donc -1
		quest.setScore(Integer.valueOf(uneQuestion[Question.nbRepQst+2]));        // le bar�me de la question
		this.serieQuestions.add(quest);
	}
	public void setDerSocre(int derSocre) {
		this.scorePartie = derSocre;
	}

	public void persistData(){
		Random rand = new Random();
		for (Question q:serieQuestions){
			questionRepository.addQuestion(rand.nextInt(Integer.MAX_VALUE),q);
		}

	}

	public void recoverData(){
		this.serieQuestions.addAll(questionRepository.getQuestions());
	}
	
	public void setIdJoueur(String idJoueur) {
		this.idJoueur = idJoueur;
	}

// renvoyer la taille de la s�rie de questions mises en jeux dans la partie
	public int lengSerieQuest() {
		return this.serieQuestions.size();
	}
	
// jouer une question en la transformant en un tableau de cha�nes pour le rendu
	public String[] playQuestion(int indxQuestion) {
		String[] questCour = new String [Question.nbRepQst+2];
		questCour[0]=this.serieQuestions.get(indxQuestion).getEnonceQuestion();                         // le texte de la question
		questCour[1]=String.valueOf(this.serieQuestions.get(indxQuestion).getScore());                  // le bar�me de la question
		for (int i=0;i<Question.nbRepQst;i++) questCour[i+2]=this.serieQuestions.get(indxQuestion).getRepPossibles()[i];  // les r�ponses possibles
		return questCour;
	}
	
// verifier la r�ponse
	public boolean verifRep(int indxQuestion, int indxRepJoueur) {
		boolean result = serieQuestions.get(indxQuestion).repJuste(indxRepJoueur); 
     	if (result) this.scorePartie++;                          //reponse juste augmente le score de la partie
		return result;
	}
}
