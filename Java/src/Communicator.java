import gnu.io.*;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TooManyListenersException;

import javax.swing.JFrame;

public class Communicator implements SerialPortEventListener {
JFrame window = new JFrame();
private SerialPort portSerie;
Communicator(JFrame window, CommPort commPort) {
	this.portSerie = (SerialPort) commPort;
	this.window = window;
	ecouterPort();
}

public void ecouterPort() {
	try {
		this.portSerie.addEventListener(this);
		this.portSerie.notifyOnDataAvailable(true);
		this.portSerie.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
	}
	catch(TooManyListenersException e) {
		e.printStackTrace();
	}
	catch(UnsupportedCommOperationException e) {
		e.printStackTrace();
	}
}

public void serialEvent(SerialPortEvent event) {
	try {
		BufferedReader monBuffer = new BufferedReader(new InputStreamReader(this.portSerie.getInputStream()));
		if(event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			String recu = monBuffer.readLine();
			System.out.println("Trame recue ! : " + recu);
			/*
			if(recu.compareToIgnoreCase("1") == 0) {
				window.getjLabelStatus().setText("Alerte !!!");
				window.getjLabelStatus().setForeground(Color.RED);
			}else if(recu.compareToIgnoreCase("2") == 0) {
				window.getjLabelStatus().setText("OK");
				window.getjLabelStatus().setForeground(Color.GREEN);
			}else {
				window.getjLabelStatus().setText(recu + " cm");
			}*/
		}
	}
	catch(IOException e) {
		e.printStackTrace();
	}
}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
