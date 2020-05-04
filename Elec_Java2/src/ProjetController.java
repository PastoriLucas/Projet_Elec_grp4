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

public class ProjetController implements SerialPortEventListener {
	private ProjetView theView;
	private ProjetModel theModel;
	
	public ProjetController(ProjetView theView, ProjetModel theModel/*, CommPort monCommPort*/){
		this.theView = theView;
		this.theModel = theModel;
		this.theView.calculerDistance(new DistanceListener());	
		this.theView.recherchePorts(new RechercheListener());
	}
	
	public void SimpleRead(String com) {
        theModel.portList = CommPortIdentifier.getPortIdentifiers();
        while (theModel.portList.hasMoreElements()) {
        	theModel.portId = (CommPortIdentifier) theModel.portList.nextElement();
            if (theModel.portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (theModel.portId.getName().equals(com)) {
                    //                if (portId.getName().equals("/dev/term/a")) {
                    try {
                    	theModel.serialPort = (SerialPort) theModel.portId.open("SimpleReadApp", 2000);
                    } catch (PortInUseException e) {
                        System.out.println(e);
                    }
                    try {
                    	theModel.inputStream = theModel.serialPort.getInputStream();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    try {
                    	theModel.serialPort.addEventListener(this);
                    } catch (TooManyListenersException e) {
                        System.out.println(e);
                    }
                    theModel.serialPort.notifyOnDataAvailable(true);
                    try {
                    	theModel.serialPort.setSerialPortParams(9600,
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
	
	/*@Override
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
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}*/

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
                    while (theModel.inputStream.available() > 0) {
                        //int numBytes = inputStream.read(readBuffer);
                    	theModel.inputStream.read(readBuffer);
                    }

                    String y = new String(readBuffer);
                    System.out.print(new String(readBuffer));
                    theView.setDistance("    " + y + "    ");
                    waiting(1);
                } catch (IOException e) {
                    System.out.println(e);
                }
                break;
        }
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
				theView.setSeuil(theModel.getSeuil());
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
}
