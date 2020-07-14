package com.fakecorp.GUI;


/*
 * T�che principal de la GUI : montrer les questions et r�cup�rer les r�ponses
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
    protected int flaqFinDepartie; // 0 si tout se termine bien 1 si le joueur � interompu le jeux avant la fin de questions
    
    //private DefaultComboBoxModel<String> lstRep;
        
	public JouerQuestion(Match match) {
		flaqFinDepartie=1;     //au d�part la partie n'est pas termin�e ... donc risque de fin anormale
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
		repList.addItemListener(new ItemListener() {   // Une r�ponse est jou�e ...
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
        // on charge une premi�re question
		chargerUneQuestion();
	}
	// Charger une question � jouer
	private void chargerUneQuestion() {
		String [] questCour = match.tirerQuestion();        // pr�parer le conteneur (tableau)
		if (questCour==null) { bnqEpuisee(); return; }  // soit la banque est epuis�e soit elle vide...
  		String [] repQstCour = new String [questCour.length-2];
		cntntQuest.setText(questCour[0]);                                         // la question (�nonc�)
		for (int i=0; i<questCour.length-2;i++) repQstCour[i]=questCour[i+2];    // les r�ponses possibles dans la liste d�roulante
		repList.setModel(new DefaultComboBoxModel<String>(repQstCour));           //mise en liste d�roulante
	    repList.setBackground(Color.WHITE);
      	repList.setForeground(Color.BLACK);
      	repList.setEnabled(true);
      	evalReponse.setBackground(Color.GRAY);
      	evalReponse.setText("");
      	//evalReponse.setVisible(false);
	    nQuest.setText(String.valueOf(match.getCptQstJouee()));                    //num�ro de la question
	    scorQuest.setText(String.valueOf(questCour[1]));                           // le bar�me
	    totalScroe.setText(String.valueOf(match.getScore()));                     // le score cummul� du joueur
	    match.setTracRepJoueur(0);
	    this.setVisible(true);
	}
	
	 // �valuer la r�ponse du joueur: si vrai incr�menter le score sinon afficher un message d'erreur et passer � la suivante
		private void evaluerReponse() {
			match.incCptQstJouee();                                //comptabiliser la question
			repList.setEnabled(false);                             //ne pas permettre une autre tentative
			if (match.estceBonneRep(repList.getSelectedIndex())) {   // cas de r�ponse juste
				totalScroe.setText(String.valueOf(match.getScore()));    // le score cummul� du joueur
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
	
	
	
    // Fin de partie banque epuis�e (totues les questions utilis�e) fin normale 0 d�gats
	private void bnqEpuisee() {
	  int reponse = JOptionPane.showConfirmDialog(null, "cette partir est termin� voulez vous enregistrer ses r�sultats ?", 
				"OUI", JOptionPane.YES_NO_OPTION);
			if(reponse == JOptionPane.YES_OPTION){
				if (match.getCptQstJouee()==0) JOptionPane.showMessageDialog(null, " vous n'avez r�pondu � aucune question donc partie non enregistr�e ... Merci ... ") ;
				 else {
					   JFileChooser partArchiv= new JFileChooser();
					   try {
    					   int usrChoix=partArchiv.showSaveDialog(JouerQuestion.this);
						   if(usrChoix==JFileChooser.APPROVE_OPTION){   // un fichier a �t� choisi et approuv� 
							  match.stopMatch(partArchiv.getSelectedFile().getAbsolutePath());
							  JOptionPane.showMessageDialog(JouerQuestion.this, " Partie enregistr�e ... Merci ... ") ;
					        } else JOptionPane.showMessageDialog(JouerQuestion.this, " Partie non enregistr�e ... Merci ... ") ; 
					   } catch (Exception e) {e.printStackTrace();}
				 }
			}
			flaqFinDepartie=0;
			this.dispose();		
	}
	
	// Arr�t demand�e par le joueur fin anormal 1
	private void stopMatch() {
		//v�rifier si il n'y a pas eu de fin normal
		if (flaqFinDepartie==0) return;
		int reponse = JOptionPane.showConfirmDialog(null, "�tes-vous s�r de vouloir quitter la partie en cours ?","OUI", JOptionPane.YES_NO_OPTION);
	    if(reponse == JOptionPane.YES_OPTION){
			if (match.getCptQstJouee()>1) {					
				reponse = JOptionPane.showConfirmDialog(null, "voulez enregister la partie ?", "OUI", JOptionPane.YES_NO_OPTION);
				if(reponse == JOptionPane.YES_OPTION){
					JFileChooser partArchiv= new JFileChooser();
					try {
    					int usrChoix=partArchiv.showSaveDialog(JouerQuestion.this);
						if(usrChoix==JFileChooser.APPROVE_OPTION){   // un fichier a �t� choisi et approuv� 
							match.stopMatch(partArchiv.getSelectedFile().getAbsolutePath());
							JOptionPane.showMessageDialog(null, " Partie interompue et enregistr�e ... Merci ... ") ;
					       } else JOptionPane.showMessageDialog(null, " Partie interompue et non enregistr�e ... Merci ... ") ; 
					    	 
					} catch (Exception e) {e.printStackTrace();}
				}
			}
			flaqFinDepartie=1; // d�gats et interuption
		this.dispose();
		}
	}
	
 
} // la fin de la classe JouerQuestion