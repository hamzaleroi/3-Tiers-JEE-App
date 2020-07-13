package com.fakecorp;

import java.awt.EventQueue;

import com.fakecorp.GUI.QuizJeux;

public class Program {
	public static void main(String[] args) {
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
