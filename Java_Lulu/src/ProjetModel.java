import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class ProjetModel {

	//static Enumeration portList;
    //static CommPortIdentifier portId;
    //static String messageString = "0";
    //static SerialPort monPortSerie;

	public int distanceRecue;	
	public int seuil = 0;
	public SerialPort monPortSerie;
	public CommPortIdentifier portId;
    public Enumeration portList;
    public CommPort monCommPort;
    InputStream inputStream;
    OutputStream outputStream;
    Thread readThread;
    
	public void setSeuil(int val) {
		this.seuil = val;
	}
	
	public int getSeuil() {
		return seuil;
	}
	
	public void setDistance(String dist) {
		int tempo = Integer.parseInt(dist);
		this.distanceRecue = tempo;
	}
	
	public int getDistance() {
		return distanceRecue;
	}
}