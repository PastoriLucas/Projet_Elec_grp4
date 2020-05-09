import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TooManyListenersException;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

public class ProjetController implements SerialPortEventListener, Runnable  {
	private ProjetView theView;
	private ProjetModel theModel;
	
	public ProjetController(ProjetView theView, ProjetModel theModel, CommPort monCommPort){
		this.theView = theView;
		this.theModel = theModel;
		theModel.monPortSerie = (SerialPort) monCommPort;
		this.theView.calculerDistance(new DistanceListener());	
		this.theView.recherchePorts(new RechercheListener());
		this.theView.choisirPort(new ChoixPortListener());
	}
	
	public void SimpleRead(String com) {
        theModel.portList = CommPortIdentifier.getPortIdentifiers();

        while (theModel.portList.hasMoreElements()) {
        	theModel.portId = (CommPortIdentifier) theModel.portList.nextElement();
            if (theModel.portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (theModel.portId.getName().equals(com)) {
                    //                if (portId.getName().equals("/dev/term/a")) {
                    try {
                    	theModel.monPortSerie = (SerialPort) theModel.portId.open("SimpleReadApp", 2000);
                    } catch (PortInUseException e) {
                        System.out.println(e);
                    }
                    try {
                    	theModel.inputStream = theModel.monPortSerie.getInputStream();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    try {
                    	theModel.monPortSerie.addEventListener(this);
                    } catch (TooManyListenersException e) {
                        System.out.println(e);
                    }
                    theModel.monPortSerie.notifyOnDataAvailable(true);
                    try {
                    	theModel.monPortSerie.setSerialPortParams(9600,
                                SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
                    } catch (UnsupportedCommOperationException e) {
                        System.out.println(e);
                    }
                    theModel.readThread = new Thread();
                    theModel.readThread.start();
                }
            }
        }

    }
	@Override
	public void serialEvent(SerialPortEvent event) {
		try {
			BufferedReader myBuffer = new BufferedReader(new InputStreamReader(theModel.monPortSerie.getInputStream()));
			if(event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
				String dataReceived = myBuffer.readLine();
				System.out.println("Recu : " + dataReceived);
				
				if(dataReceived.compareToIgnoreCase("1") ==0) {
					theView.setAlerte("! ! ! ALERTE RECUE ! ! !", Color.red);
				}
				else if(dataReceived.compareToIgnoreCase("2")==0) {
					theView.setAlerte("Rien à signaler.", Color.green);
				}
				else {
					theView.setDistance("Distance reçue : " + dataReceived + "cm.");
				}
			}
			else {
				System.out.println("Ca marche pas tant que ça");
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void sendData() {
		System.out.println("Debut SendData()");
		java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
	    /*int i = 0;
	    String[] r = new String[5];
	    while (portEnum.hasMoreElements() && i < 5) {
	        CommPortIdentifier portIdentifier = portEnum.nextElement();
	        r[i] = portIdentifier.getName();//+  " - " +  getPortTypeName(portIdentifier.getPortType()) ;		
	        i++;	
	    }
	
	    theView.choixPort.setModel(new javax.swing.DefaultComboBoxModel(r));
		*/
	    //------------------------------------------------
	}
	
	public String ser(byte x , String com){	
        theModel.portList = CommPortIdentifier.getPortIdentifiers();
        while (theModel.portList.hasMoreElements()) {
        	theModel.portId = (CommPortIdentifier) theModel.portList.nextElement();
        	if (theModel.portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
        		if (theModel.portId.getName().equals(com)) {
        			try {
        				theModel.monPortSerie = (SerialPort)
        						theModel.portId.open("SimpleWriteApp", 2000);
        			} 
        			catch (PortInUseException e) { 
        				return "Port In Use";   
        			}
        			try {
        				theModel.outputStream = theModel.monPortSerie.getOutputStream();
        			} 
        			catch (IOException e) {
        				
        			}
        			try {
        				theModel.monPortSerie.setSerialPortParams(9600,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);
        			} 
        			catch (UnsupportedCommOperationException e) {
        				
        			}
        			try {
        				theModel.outputStream.write(x);                   
        			} 
        			catch (IOException e) {
        				return "Failed to Send Data";
        			}
        		}
        	}
        }
    return "Data Sent";  
	}
	
	public void closeSerial(){
		theModel.monPortSerie.close(); 
	}
	
	public static void waiting(int n) {

        long t0, t1;

        t0 = System.currentTimeMillis();

        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < (n * 1000));
    }
	
	public void run() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
	
	class DistanceListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				theModel.setSeuil(theView.getSeuil());
				theView.setPhraseSeuil(theModel.getSeuil());
				
				theView.setDistance("Distance reçue : " + theModel.distanceRecue);
				if(theModel.getDistance() > theModel.getSeuil()) {
					theView.setAlerte("DISTANCE TROP GRANDE", Color.red);
				}
				else {
					theView.setAlerte("Rien à signaler", Color.green);
				}
			}
			catch(NumberFormatException ex){
				
			}
			
		}
		
	}
	
	class RechercheListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		        int i = 0;
		        String[] r = new String[5];
		        while (portEnum.hasMoreElements() && i < 5) {
		            CommPortIdentifier portIdentifier = portEnum.nextElement();
		            r[i] = portIdentifier.getName();//+  " - " +  getPortTypeName(portIdentifier.getPortType()) ;
		            i++;
		        }
		        System.out.println(r);
		        theView.choixPort.setModel(new javax.swing.DefaultComboBoxModel(r));
			}
			catch(NumberFormatException ex){
				
			}	
		}
	}	
	
	class ChoixPortListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				Object selectedItem = theView.choixPort.getSelectedItem();

		        String com = selectedItem.toString();
		        SimpleRead(com);
			}
			catch(NumberFormatException ex){
				
			}	
		}
	}
}