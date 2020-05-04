import java.io.InputStream;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class ProjetModel {

	public String seuil = "0";
	public SerialPort monPortSerie;
	public CommPortIdentifier portId;
    public Enumeration portList;
    InputStream inputStream;
    SerialPort serialPort;
    Thread readThread;
    
	public void setSeuil(String val) {
		this.seuil = val;
	}
	
	public String getSeuil() {
		return seuil;
	}
}
