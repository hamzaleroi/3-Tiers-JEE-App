package com.fakecorp;

import com.fakecorp.DAO.Question;
import com.fakecorp.DAO.QuestionRepository;
import com.fakecorp.DAO.QuestionRepositoryImpl;
import com.fakecorp.GUI.QuizJeux;
import com.fakecorp.core.Match;

import java.awt.*;


public class Program {


	public static void main(String[] args) {

/*
		Match match = new Match();
		match.setInfoJoueur(new String[] {"444","hamza","benyamina"});
		//match.setBanqueFichier("/home/hamza/Desktop/data.csv");
		match.getManche().recoverData();
		for(Question q:match.getManche().getSerieQuestions()){
			System.out.println(q.getEnonceQuestion());
		}
		*/
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuizJeux jeuQuiz = new QuizJeux();
					jeuQuiz.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}



}



/*
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuizJeux jeuQuiz = new QuizJeux();
					jeuQuiz.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/