package com.fakecorp.GUI;


/*
 * Tâche principal de la GUI : montrer les questions et récupérer les réponses
 */
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Color;
import javax.swing.UIManager;

import com.fakecorp.core.Match;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JouerQuestion extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Match match;
    private JTextArea cntntQuest;
    JTextArea evalReponse;
    private JComboBox<String> repList;
    private JLabel nQuest;
    private JLabel scorQuest;
    private JLabel totalScroe;
    protected int flaqFinDepartie; // 0 si tout se termine bien 1 si le joueur à interompu le jeux avant la fin de questions
    
    //private DefaultComboBoxModel<String> lstRep;
        
	public JouerQuestion(Match match) {
		flaqFinDepartie=1;     //au départ la partie n'est pas terminée ... donc risque de fin anormale
		WindowAdapter manipQuestFen = new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				stopMatch();
			}
		};
		addWindowListener(manipQuestFen);
	    this.match = match;
	    this.setTitle("Joueur ="+match.getIdJoueur());
		setSize(new Dimension(298, 252));
		setLocationRelativeTo(null);
		setBounds(100, 100, 564, 347);
	
		JLabel score = new JLabel(" ");
		score.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().setLayout(null);
				
		cntntQuest = new JTextArea();
		cntntQuest.setWrapStyleWord(true);
		cntntQuest.setEditable(false);
		cntntQuest.setBounds(21, 61, 517, 114);
		getContentPane().add(cntntQuest);
		
		repList = new JComboBox<String>();
		repList.setForeground(UIManager.getColor("ComboBox.foreground"));
		repList.addItemListener(new ItemListener() {   // Une réponse est jouée ...
			public void itemStateChanged(ItemEvent arg0) {
				evaluerReponse();
			}
		});
		repList.setModel(new DefaultComboBoxModel<String>(new String[] {"", "", ""}));
		repList.setBounds(21, 186, 517, 37);
		getContentPane().add(repList);
		
		JButton btnNewButton = new JButton("Question Suivante");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chargerUneQuestion();
			}
		});
		btnNewButton.setBounds(21, 234, 190, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Arr\u00EAter");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				manipQuestFen.windowClosed(null);
			}
		});
		btnNewButton_1.setBounds(381, 234, 157, 23);
		getContentPane().add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("Question N\u00B0");
		lblNewLabel.setBounds(21, 36, 66, 14);
		getContentPane().add(lblNewLabel);
		
		nQuest = new JLabel(" ");
		nQuest.setBounds(97, 36, 46, 14);
		getContentPane().add(nQuest);
		
		scorQuest = new JLabel(" ");
		scorQuest.setBounds(394, 36, 46, 14);
		getContentPane().add(scorQuest);
		
		JLabel lblScore = new JLabel("BAREME");
		lblScore.setBounds(318, 36, 66, 14);
		getContentPane().add(lblScore);
		
		JLabel lblScore_2 = new JLabel("SCORE ");
		lblScore_2.setBounds(158, 11, 72, 14);
		getContentPane().add(lblScore_2);
		
		totalScroe = new JLabel(" ");
		totalScroe.setBounds(205, 11, 66, 14);
		getContentPane().add(totalScroe);
		
		evalReponse = new JTextArea();
		evalReponse.setBackground(Color.GRAY);
		evalReponse.setForeground(Color.BLACK);
		//evalReponse.setBounds(158, 275, 203, 22);
		getContentPane().add(evalReponse);
        // on charge une première question
		chargerUneQuestion();
	}
	// Charger une question à jouer
	private void chargerUneQuestion() {
		String [] questCour = match.tirerQuestion();        // préparer le conteneur (tableau)
		if (questCour==null) { bnqEpuisee(); return; }  // soit la banque est epuisée soit elle vide...
  		String [] repQstCour = new String [questCour.length-2];
		cntntQuest.setText(questCour[0]);                                         // la question (énoncé)
		for (int i=0; i<questCour.length-2;i++) repQstCour[i]=questCour[i+2];    // les réponses possibles dans la liste déroulante
		repList.setModel(new DefaultComboBoxModel<String>(repQstCour));           //mise en liste déroulante
	    repList.setBackground(Color.WHITE);
      	repList.setForeground(Color.BLACK);
      	repList.setEnabled(true);
      	evalReponse.setBackground(Color.GRAY);
      	evalReponse.setText("");
      	//evalReponse.setVisible(false);
	    nQuest.setText(String.valueOf(match.getCptQstJouee()));                    //numéro de la question
	    scorQuest.setText(String.valueOf(questCour[1]));                           // le barème
	    totalScroe.setText(String.valueOf(match.getScore()));                     // le score cummulé du joueur
	    match.setTracRepJoueur(0);
	    this.setVisible(true);
	}
	
	 // évaluer la réponse du joueur: si vrai incrémenter le score sinon afficher un message d'erreur et passer à la suivante
		private void evaluerReponse() {
			match.incCptQstJouee();                                //comptabiliser la question
			repList.setEnabled(false);                             //ne pas permettre une autre tentative
			if (match.estceBonneRep(repList.getSelectedIndex())) {   // cas de réponse juste
				totalScroe.setText(String.valueOf(match.getScore()));    // le score cummulé du joueur
				repList.setBackground(Color.GREEN);
	          	repList.setForeground(Color.WHITE);
				evalReponse.setBackground(Color.GREEN);
	          	evalReponse.setForeground(Color.WHITE);
	          	evalReponse.setText("BONNE REPONSE (:>)");
	          	evalReponse.setVisible(true);
			} else {
				repList.setBackground(Color.RED);
	          	repList.setForeground(Color.BLACK);			
				evalReponse.setBackground(Color.RED);
	          	evalReponse.setForeground(Color.BLACK);
	          	evalReponse.setText("MAUVAISE REPONSE (:o)");
	          	evalReponse.setVisible(true);
			  }
			
		}
	
	
	
    // Fin de partie banque epuisée (totues les questions utilisée) fin normale 0 dégats
	private void bnqEpuisee() {
	  int reponse = JOptionPane.showConfirmDialog(null, "cette partir est terminé voulez vous enregistrer ses résultats ?", 
				"OUI", JOptionPane.YES_NO_OPTION);
			if(reponse == JOptionPane.YES_OPTION){
				if (match.getCptQstJouee()==0) JOptionPane.showMessageDialog(null, " vous n'avez répondu à aucune question donc partie non enregistrée ... Merci ... ") ;
				 else {
					   JFileChooser partArchiv= new JFileChooser();
					   try {
    					   int usrChoix=partArchiv.showSaveDialog(JouerQuestion.this);
						   if(usrChoix==JFileChooser.APPROVE_OPTION){   // un fichier a été choisi et approuvé 
							  match.stopMatch(partArchiv.getSelectedFile());
							  JOptionPane.showMessageDialog(JouerQuestion.this, " Partie enregistrée ... Merci ... ") ;
					        } else JOptionPane.showMessageDialog(JouerQuestion.this, " Partie non enregistrée ... Merci ... ") ; 
					   } catch (Exception e) {e.printStackTrace();}
				 }
			}
			flaqFinDepartie=0;
			this.dispose();		
	}
	
	// Arrêt demandée par le joueur fin anormal 1
	private void stopMatch() {
		//vérifier si il n'y a pas eu de fin normal
		if (flaqFinDepartie==0) return;
		int reponse = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir quitter la partie en cours ?","OUI", JOptionPane.YES_NO_OPTION);
	    if(reponse == JOptionPane.YES_OPTION){
			if (match.getCptQstJouee()>1) {					
				reponse = JOptionPane.showConfirmDialog(null, "voulez enregister la partie ?", "OUI", JOptionPane.YES_NO_OPTION);
				if(reponse == JOptionPane.YES_OPTION){
					JFileChooser partArchiv= new JFileChooser();
					try {
    					int usrChoix=partArchiv.showSaveDialog(JouerQuestion.this);
						if(usrChoix==JFileChooser.APPROVE_OPTION){   // un fichier a été choisi et approuvé 
							match.stopMatch(partArchiv.getSelectedFile());
							JOptionPane.showMessageDialog(null, " Partie interompue et enregistrée ... Merci ... ") ;
					       } else JOptionPane.showMessageDialog(null, " Partie interompue et non enregistrée ... Merci ... ") ; 
					    	 
					} catch (Exception e) {e.printStackTrace();}
				}
			}
			flaqFinDepartie=1; // dégats et interuption
		this.dispose();
		}
	}
	
 
} // la fin de la classe JouerQuestion