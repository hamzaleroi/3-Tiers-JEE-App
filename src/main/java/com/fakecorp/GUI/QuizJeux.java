package com.fakecorp.GUI;


// l'application tr�s basic du Quiz .... monoposte, mono utilisateur : l'objectif est de ne pas from scratch
import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class QuizJeux extends JFrame {

	private JPanel contentPane; 
	private InscrirJoueur inscription;
	private JFileChooser ouvreBanqueQuest;
	private File bnQuest;


	

	public QuizJeux() {
		WindowAdapter manipAppFen = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fermerTout();
			}
		};
		addWindowListener(manipAppFen);
		setBackground(new Color(169, 169, 169));
		setTitle("QUIZ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 564, 347);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		ouvreBanqueQuest = new JFileChooser();
		
		JPanel panel = new JPanel();

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 742, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 329, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(26, Short.MAX_VALUE))
		);
		panel.setLayout(null);
		JButton btnLancement = new JButton("COMMENCER");
		btnLancement.setBounds(10, 275, 167, 23);
		panel.add(btnLancement);

		JButton btnChargerBase = new JButton("CHARGER");
		btnChargerBase.setBounds(180, 275, 167, 23);
		panel.add(btnChargerBase);
		
		JButton btnArrter = new JButton("Arr\u00EAter");
		btnArrter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manipAppFen.windowClosing(null);
			}
		});
		btnArrter.setBounds(372, 274, 162, 23);
		panel.add(btnArrter);
		
		JTextArea txtrBienvenuDansLe = new JTextArea();
		txtrBienvenuDansLe.setBounds(10, 11, 534, 450);
		panel.add(txtrBienvenuDansLe);
		txtrBienvenuDansLe.setWrapStyleWord(true);
		txtrBienvenuDansLe.setForeground(UIManager.getColor("MenuItem.foreground"));
		txtrBienvenuDansLe.setBackground(UIManager.getColor("Panel.background"));
		txtrBienvenuDansLe.setLineWrap(true);
		txtrBienvenuDansLe.setFont(new Font("Gabriola", Font.BOLD, 15));
		txtrBienvenuDansLe.setEditable(false);
		txtrBienvenuDansLe.setText("                                 BIENVENU DANS LE QUIZ\r\n" +
				"Ceci est un mod\u00E8le simpliste d'un quiz desktop.\r\n" +
				"Il sera utilis\u00E9 pour mettre en oeuvre des architetcures N-TIERs.\r\n\r\n" +
				"1.CHOISIR LA BANQUE DE QUESTIONS A UTILISER (FORMAT CSV avec ;)\r\n" +
				"(enonce;reponse1;reponse 2;reponse 3;reponse 4;indice reponse corecte; bar\u00E8me)\r\n" +
				"2.INSCRIRIE LES INFORMATIONS DU JOUEUR\r\n3.LANCER LA PARTIE" +
				"\n" +
				"CHARGER: permet de charger les questions depuis la base données");
		btnLancement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 1. ouvrir la banque de question
				int bienDerouler=chargBanque();
				// 2. inscrire le joueur (si tout va bien) au quiz
				if (bienDerouler==0) return; else bienDerouler=inscJoueur();
			}
		});

		btnChargerBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 2. inscrire le joueur (si tout va bien) au quiz
				inscJoueur();
			}
		});
		contentPane.setLayout(gl_contentPane);
	}

	//  ouvrir la banque de question
	private int chargBanque() {
		try {
			int usrChoix=ouvreBanqueQuest.showOpenDialog(QuizJeux.this);
			if(usrChoix==JFileChooser.APPROVE_OPTION){   // un fichier a �t� choisi et approuv� 
			    bnQuest = ouvreBanqueQuest.getSelectedFile();
		       } else return 0; // pas de fichier choisi on fait rien
		} catch (Exception e) {e.printStackTrace();	return 0; }
		return 1;
	}
	// inscrire le joueur 
	private int inscJoueur() {
		inscription= new InscrirJoueur();
		inscription.setLocationRelativeTo(this);
		inscription.setVisible(true);
		return 1;
	}
	// FIn du Quiz.
	private void fermerTout() {
		int reponse = JOptionPane.showConfirmDialog(null, "�tes-vous s�r de vouloir quitter le QUIZ ?", 
				"OUI", JOptionPane.YES_NO_OPTION);
			if(reponse == JOptionPane.YES_OPTION){
				JOptionPane.showMessageDialog(null, "Merci") ;
				
				this.dispose();
			} else this.setVisible(true);
			
	}
}
