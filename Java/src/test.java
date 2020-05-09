import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Observable;
import java.util.Observer;
import java.util.TooManyListenersException;

import javax.swing.*;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

public class test implements Runnable, SerialPortEventListener, ActionListener{
	static CommPortIdentifier portId;
    static Enumeration portList;
    InputStream inputStream;
    SerialPort serialPort;
    Thread readThread;
    
	private ProjetModel model = new ProjetModel();
	private JLabel titre = new JLabel("PROJET D'ELECTRONIQUE");
	private JLabel labelChoixPort = new JLabel("Choisissez le port : ");
	private String[] ports ;
	private JComboBox<String> choixPort = new JComboBox<>();
	private JLabel labelAfficherSeuil = new JLabel("Seuil de distance maximal actuel : 100");	
	private JLabel labelEntrerSeuil = new JLabel("  Nouveau seuil de distance: ");
	private JTextField entrerSeuil = new JTextField(3);
	private JButton appliquerSeuil = new JButton("Appliquer");
	private JLabel alerte = new JLabel("Pas d'alertes");
	private JButton recherche = new JButton("Recherche ports");
	private JTextArea reponse = new JTextArea();
	

	public test() {
		
		this.model = model;
		
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
		
		
		
		labelChoixPort.setBounds(90, 70, 150, 20);
		window.add(labelChoixPort);
		choixPort.setBounds(210, 70, 70, 20);
		choixPort.addActionListener(this);
		window.add(choixPort);
		
		
		labelEntrerSeuil.setBounds(80, 137, 200, 20);
		window.add(labelEntrerSeuil);
		entrerSeuil.setText("100");
		entrerSeuil.setBounds(250, 138, 50, 20);
		window.add(entrerSeuil);	
		appliquerSeuil.setBounds(150, 170, 100, 20);
		window.add(appliquerSeuil);
		
		labelAfficherSeuil.setBounds(85, 200, 250, 20);
		window.add(labelAfficherSeuil);
		
		alerte.setForeground(Color.green);
		alerte.setBounds(140, 260, 100, 20);
		window.add(alerte);
		
		recherche.setBounds(160, 300, 200, 20);
		window.add(recherche);
		
		reponse.setBounds(160,340,200,20);
		window.add(reponse);

		window.setVisible(true);
	}

	

	public void SimpleRead(String com) {
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals(com)) {
                    //                if (portId.getName().equals("/dev/term/a")) {
                    try {
                        serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
                    } catch (PortInUseException e) {
                        System.out.println(e);
                    }
                    try {
                        inputStream = serialPort.getInputStream();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    try {
                        serialPort.addEventListener(this);
                    } catch (TooManyListenersException e) {
                        System.out.println(e);
                    }
                    serialPort.notifyOnDataAvailable(true);
                    try {
                        serialPort.setSerialPortParams(9600,
                                SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
                    } catch (UnsupportedCommOperationException e) {
                        System.out.println(e);
                    }
                    readThread = new Thread(this);
                    readThread.start();
                }
            }
        }

    }

    public void run() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:
                byte[] readBuffer = new byte[12];

                try {
                    while (inputStream.available() > 0) {
                        //int numBytes = inputStream.read(readBuffer);
                        inputStream.read(readBuffer);
                    }

                    String y = new String(readBuffer);
                    System.out.print(new String(readBuffer));
                    reponse.setText("    " + y + "    ");
                    waiting(1);
                } catch (IOException e) {
                    System.out.println(e);
                }
                break;
        }
    }

    ////////
    public static void waiting(int n) {

        long t0, t1;

        t0 = System.currentTimeMillis();

        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < (n * 1000));
    }
    ////////////

	@Override
	public void actionPerformed(ActionEvent event) {
		Object e = event.getSource();
		if (e == recherche ) {
			test();
		}
		
		if (e == choixPort){
			Object selectedItem = choixPort.getSelectedItem();

	        String com = selectedItem.toString();
	        SimpleRead(com);
		}
		
		
	}
	
	public void test(){
		java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        int i = 0;
        String[] r = new String[5];

        while (portEnum.hasMoreElements() && i < 5) {
            CommPortIdentifier portIdentifier = portEnum.nextElement();

            r[i] = portIdentifier.getName();//+  " - " +  getPortTypeName(portIdentifier.getPortType()) ;
            System.out.println(r[i]);

            i++;


        }

        choixPort.setModel(new javax.swing.DefaultComboBoxModel(r));
	}
	java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
    
	public static void main(String[] args) {
		test test = new test();
	}
	
	
}