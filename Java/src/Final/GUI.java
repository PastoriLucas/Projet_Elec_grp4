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
	Controller controller = null;
    
    JLabel titre = new JLabel("PROJET D'ELECTRONIQUE");
    JLabel groupe = new JLabel("Groupe 4");
    JComboBox<String> cboxPorts = new JComboBox<>();
	JButton btonConnect = new JButton("Connect");
	JButton btonDisconnect = new JButton("Disconnect");
	JLabel labelAfficherSeuil = new JLabel("Seuil maximal actuel (cm) : 100");	
	JLabel labelEntrerSeuil = new JLabel("Nouveau seuil maximal (cm):");
	JTextField entrerSeuil = new JTextField(3);
	JButton appliquerSeuil = new JButton("Appliquer");
	JLabel alerte = new JLabel("Pas d'alertes");
	JLabel resultatPhrase = new JLabel("DISTANCE RECUE : ");
	JLabel cm = new JLabel("CM.");
	JLabel resultat = new JLabel();

	
	public void addController(Controller controller){
		this.controller = controller;
	}
	

    public GUI() {
    	JFrame window = new JFrame();
		window.setTitle("Electronique Groupe 4");
		window.setSize(400, 600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setBackground(Color.white);
		window.setLayout(null);
		Font titreFont = new Font("Comic Sans MS", Font.PLAIN, 15);
	
		titre.setBounds(90, 10, 300, 20);
		titre.setForeground(Color.black);
		titre.setFont(titreFont);
		window.add(titre);
		groupe.setBounds(165, 30, 300, 20);
		groupe.setForeground(Color.black);
		groupe.setFont(titreFont);
		window.add(groupe);
		
		cboxPorts.setBounds(155, 70, 80, 20);
		window.add(cboxPorts);
		btonConnect.setBounds(90, 100, 100, 20);
		btonConnect.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                	controller.connect();
                    if (controller.model.bConnected == true)
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
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                controller.disconnect();
            }
        });
		window.add(btonDisconnect);
		
		labelAfficherSeuil.setFont(titreFont);
		labelAfficherSeuil.setBounds(100, 150, 250, 20);
		labelAfficherSeuil.setForeground(Color.RED);
		window.add(labelAfficherSeuil);
		
		labelEntrerSeuil.setBounds(20, 200, 200, 20);
		window.add(labelEntrerSeuil);
		entrerSeuil.setText("100");
		entrerSeuil.setBounds(190, 201, 50, 20);
		window.add(entrerSeuil);	
		appliquerSeuil.setBounds(260, 201, 100, 20);
		appliquerSeuil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                controller.writeData(Integer.parseInt(entrerSeuil.getText()));
            }
        });
		window.add(appliquerSeuil);
		
		resultatPhrase.setBounds(90, 310, 150, 20);
		resultatPhrase.setFont(titreFont);
		window.add(resultatPhrase);
		
		resultat.setBounds(240, 310, 50, 20);
		resultat.setBackground(Color.lightGray);
		window.add(resultat);
		
		cm.setBounds(270, 310, 150, 20);
		cm.setFont(titreFont);
		window.add(cm);
		
		alerte.setForeground(Color.green);
		alerte.setBounds(150, 450, 150, 20);
		window.add(alerte);
	

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