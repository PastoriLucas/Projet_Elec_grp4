import java.io.InputStream;
import java.util.Enumeration;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class ProjetModel {

	public String distanceRecue = "111";
	
	public String seuil = "0";
	public SerialPort monPortSerie;
	public CommPortIdentifier portId;
    public Enumeration portList;
    public CommPort monCommPort;
    InputStream inputStream;
    Thread readThread;
    
	public void setSeuil(String val) {
		this.seuil = val;
	}
	
	public String getSeuil() {
		return seuil;
	}
	
	public String getDistance() {
		return distanceRecue;
	}
}
