package Final;

import gnu.io.*;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TooManyListenersException;

public class Model {	
	int seuil; 											//Contient le seuil de distance maximal authorisé
    String distanceRecue = "";														
    String caractRecu;
    Enumeration ports = null;							//Contient tous les ports trouvés lors de la recherche de ports
    HashMap portMap = new HashMap();					//Contient le nom des ports sous forme de hashmapt
    
    CommPortIdentifier selectedPortIdentifier = null;	//Contient les ports ouverts
    SerialPort serialPort = null;						//Contient le serial port actuellement ouvert

    InputStream input = null;							//Stream pour la réception de data
    OutputStream output = null;							//Stream pour l'envoi de data

    boolean portConnected = false;							//Booleen pour savoir si on est deja connecté à un port ou pas
    final static int TIMEOUT = 2000;					//Timeout pour la connexion à un port
}
