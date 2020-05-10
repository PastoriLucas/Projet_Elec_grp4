package Final;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends javax.swing.JFrame {
	Controller controller = null;												//Défini un nouveau contrôleur
    
	//Définition des variables
    JLabel titre = new JLabel("PROJET D'ELECTRONIQUE");							//Titre
    JLabel groupe = new JLabel("Groupe 4");										//N° du groupe
    JComboBox<String> cboxPorts = new JComboBox<>();							//Choix du port
	JButton btonConnect = new JButton("Connect");								//Bouton connexion
	JButton btonDisconnect = new JButton("Disconnect");							//Bouton déconnexion
	JLabel labelAfficherSeuil = new JLabel("Seuil maximal actuel : 100 cm");	//Seuil actuel
	JLabel labelEntrerSeuil = new JLabel("Nouveau seuil maximal (cm):");		//Phrase modifier le seuil
	JTextField entrerSeuil = new JTextField(3);									//Input nouveau seuil
	JButton appliquerSeuil = new JButton("Appliquer");							//Aplliquer le nouveau seuil
	JLabel alerte = new JLabel("Pas d'alertes");								//Phrase d'alerte (ou non)
	JLabel resultatPhrase = new JLabel("DISTANCE RECUE : ");					//Phrase début distance recue
	JLabel cm = new JLabel("CM.");												//Phrase fin distance recue
	JLabel resultat = new JLabel();												//Affichage distance recue

	//Définition du contrôleur
	public void addController(Controller controller){
		this.controller = controller;
	}
	
	
    public GUI() {
    	
    	//Création de la JFrame
    	JFrame window = new JFrame();
		window.setTitle("Electronique Groupe 4");
		window.setSize(400, 600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setBackground(Color.white);
		window.setLayout(null);
		Font titreFont = new Font("Comic Sans MS", Font.PLAIN, 15);
	
		//Ajout du titre et du groupe
		titre.setBounds(90, 10, 300, 20);
		titre.setForeground(Color.black);
		titre.setFont(titreFont);
		window.add(titre);
		groupe.setBounds(165, 30, 300, 20);
		groupe.setForeground(Color.black);
		groupe.setFont(titreFont);
		window.add(groupe);
		
		//Ajout du choix de port, bouton connexion et déconnexion
		cboxPorts.setBounds(155, 70, 80, 20);
		window.add(cboxPorts);
		btonConnect.setBounds(90, 100, 100, 20);
		btonConnect.addActionListener(new java.awt.event.ActionListener() {
			//Définition de l'action lors du clic sur connexion
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	controller.connect();
                if (controller.model.portConnected == true)
                {
                    if (controller.initIOStream() == true)					
                    {
                    	controller.initListener();
                    }
                }
            }
        });
		window.add(btonConnect);
		btonDisconnect.setBounds(200, 100, 100, 20);
		btonDisconnect.addActionListener(new java.awt.event.ActionListener() {
			//Définition de l'action lors du clic sur déconnexion
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                controller.disconnect();
            }
        });
		window.add(btonDisconnect);
		
		//Ajout de l'affichage du seuil
		labelAfficherSeuil.setFont(titreFont);
		labelAfficherSeuil.setBounds(100, 150, 250, 20);
		labelAfficherSeuil.setForeground(Color.RED);
		window.add(labelAfficherSeuil);
		
		//Ajout de la modification du seuil
		labelEntrerSeuil.setBounds(20, 200, 200, 20);
		window.add(labelEntrerSeuil);
		entrerSeuil.setText("100");
		entrerSeuil.setBounds(190, 201, 50, 20);
		window.add(entrerSeuil);	
		appliquerSeuil.setBounds(260, 201, 100, 20);
		appliquerSeuil.addActionListener(new java.awt.event.ActionListener() {
			//Définition de l'action lors du clic sur appliquer
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                controller.writeData(Integer.parseInt(entrerSeuil.getText()));               
            }
        });
		window.add(appliquerSeuil);
		
		//Ajout de la phrase introduisant la distance recue
		resultatPhrase.setBounds(90, 310, 150, 20);
		resultatPhrase.setFont(titreFont);
		window.add(resultatPhrase);
		
		//Ajout de la distance recue
		resultat.setBounds(240, 310, 50, 20);
		resultat.setBackground(Color.lightGray);
		window.add(resultat);
		
		//Ajout de l'unité de mesure (cm)
		cm.setBounds(270, 310, 150, 20);
		cm.setFont(titreFont);
		window.add(cm);
		
		//Ajout de la zone d'affichage d'alertes
		alerte.setForeground(Color.green);
		alerte.setBounds(150, 450, 150, 20);
		window.add(alerte);
	
		//Rend la JFrame visible
		window.setVisible(true);
    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI();
            }
        });
    }
}
