
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class ProjetController implements SerialPortEventListener {
	private ProjetView theView;
	private ProjetModel theModel;
	
	public ProjetController(ProjetView theView, ProjetModel theModel){
		this.theView = theView;
		this.theModel = theModel;
		
		this.theView.calculerDistance(new DistanceListener());
		
	}
	
	class DistanceListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				theModel.setSeuil(theView.getSeuil());
				theView.setDistance(theModel.getSeuil());
			}
			catch(NumberFormatException ex){
				
			}
			
		}
		
	}
	
	@Override
	public void serialEvent(SerialPortEvent arg0) {
		/*try {
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
		*/
	}
}
