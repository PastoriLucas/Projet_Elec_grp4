package Final;

import gnu.io.*;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TooManyListenersException;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class Controller implements SerialPortEventListener {
	GUI window = null;		//Définition de la GUI
	Model model = null;		//Définition du modèle
	
	public Controller(Model model) {
		this.model = model;	//Création du modèle
	}
	
	public void AddGUI(GUI window){
		this.window = window;	//Création de la GUI
	}
	
	public void searchForPorts() {														//Recherche des ports COM ouverts sur la machine
        model.ports = CommPortIdentifier.getPortIdentifiers();							//Enregistre les ports
        while (model.ports.hasMoreElements()) {											//Tant que des ports sont trouvés...
            CommPortIdentifier curPort = (CommPortIdentifier)model.ports.nextElement();	//... prend l'élément suivant
            if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {				//Ne choisit que les ports Serial
            	window.cboxPorts.addItem(curPort.getName());							//Ajoute le port Serial au choix de port
                model.portMap.put(curPort.getName(), curPort);							//Ajoute le nom du port à la hashmap
            }
        }
    }
	
	public void connect() {																		//Connection au port Serial sélectionné
        String selectedPort = (String)window.cboxPorts.getSelectedItem();						//Enregistre le port sélectionné							
        model.selectedPortIdentifier = (CommPortIdentifier)model.portMap.get(selectedPort);		//Enregistre le nom du port sélectionné
        CommPort commPort = null;
        try {
            commPort = model.selectedPortIdentifier.open("Controller", model.TIMEOUT);			//Ouvre le port sélectionné
            model.serialPort = (SerialPort)commPort;											//Le port COM est casté sous forme de port Serial
            model.portConnected = true;															//Indique qu'un port est déjà sélectionné
            System.out.println(selectedPort + " opened successfully."); 						//Affiche en console que la connexion est réussie       
        }
        catch (PortInUseException e) {
        	System.out.println(selectedPort + " is in use. (" + e.toString() + ")"); 			//Erreur si un port est déjà connecté
        }
        catch (Exception e) {
        	System.out.println("Failed to open " + selectedPort + "(" + e.toString() + ")") ;	//Si toute autre erreur
        }
    }
	
    public void disconnect() {															//Déconnecte le port actuellement en écoute 
        try {
            model.serialPort.removeEventListener();										//Enleve l'event listener
            model.serialPort.close();													//Ferme le port serial
            model.input.close();														//Ferme l'inputStream
            model.output.close();														//Ferme l'outputStream
            model.portConnected = false;												//Indique que plus aucun port est connecté	
            System.out.println("Disconnected.");										//Indique en console que la déconnexion est réussie
        }
        catch (Exception e) {
        	System.out.println("Failed to close " + model.serialPort.getName()+ "(" + e.toString() + ")"); //Si erreur
        }
    }
	

    public boolean initIOStream() {														//Initialise les inputStream et outputStream
        boolean successful = false;														//Renvoie si l'initialisation s'est bien passée ou pas
        try {
            model.input = model.serialPort.getInputStream();							//Définit l'inputStream dans une variable
            model.output = model.serialPort.getOutputStream();							//Définit l'outputStream dans une variable
            writeData(50);																//Envoie le seuil par défaut (100cm)
            successful = true;															//Initialisation ok
            return successful;															
        }
        catch (IOException e) {
        	System.out.println("I/O Streams failed to open. (" + e.toString() + ")"); 	//Si erreurs lors de l'initialisation
            return successful;															//Initialisation pas ok
        }
    }
    
    public void initListener() {														//Event listener lorsque de la data valide est disponible
        try {
            model.serialPort.addEventListener(this);									//Ajout de l'event listener
            model.serialPort.notifyOnDataAvailable(true);								//Alerte lorsque de la data est disponible
        }
        catch (TooManyListenersException e) {
        	System.out.println("Too many listeners. (" + e.toString() + ")");
        }
    }
    
    public void serialEvent(SerialPortEvent evt) {								//Méthode lorsque le port série recoit la distance la plaque de projet
        if (evt.getEventType() == SerialPortEvent.DATA_AVAILABLE) {				//Si la distance est disponible
            try {
            	byte singleData = (byte)model.input.read();						//Lit la distance sous forme de byte
            	model.caractRecu = new String(new byte[] {singleData}); 		//Transforme la distance sous forme de String                  
            	if(model.caractRecu.equalsIgnoreCase("$")) {					//Vérifie si l'on est en début de trame
            		window.resultat.setText(model.distanceRecue);				//Définit le résultat à l'écran
            		if(Integer.parseInt(model.distanceRecue) > model.seuil) {	//Vérifie si la distance est sous le seuil maximal
            			window.alerte.setText("DISTANCE TROP GRANDE !!!");		//Lance l'alerte de distance trop grande
            			window.alerte.setForeground(Color.red);					//Définit la couleur en rouge
            		}
            		else {
            			window.alerte.setText("RIEN A SIGNALER.");				//Lance la 'non alerte'
            			window.alerte.setForeground(Color.green);				//Définit la couleur en vert
            		}
            		model.distanceRecue = "";									//Réinitialise la variable récupérant la distance
            	}
            	else {
            		model.distanceRecue += model.caractRecu;          			//Ajoute la data recue à la distance finale      		
            	}
            }
            catch (Exception e)
            {
            	System.out.println("Failed to read data. (" + e.toString() + ")"); //Impossible de lire les données
            }
        }
    }
    
  
    public void writeData(int seuil) {														//Méthode qui envoie le seuil à la plaque de projet
        try {       	
        	if (seuil >=100) {
        	    model.output.write(("!"+seuil).getBytes());
        	}
        	else if (seuil >= 10) {
        	    model.output.write(("!0"+seuil).getBytes());
        	}
        	else {
        	    model.output.write(("!00"+seuil).getBytes());
        	}
            model.output.flush();															//Vide l'outputStream            
            model.seuil = seuil;															//Redéfinit le nouveau seuil
            window.labelAfficherSeuil.setText("Seuil maximal actuel " + seuil + " cm");		//Affiche le nouveau seuil à l'écran
        }
        catch (Exception e) {
        	System.out.println("Failed to write data. (" + e.toString() + ")");				//Si erreurs
        }
    }
}
