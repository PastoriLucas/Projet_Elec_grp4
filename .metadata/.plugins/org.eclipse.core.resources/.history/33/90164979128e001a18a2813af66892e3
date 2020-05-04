import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjetController {
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
			int seuil = 0;
			try {
				seuil = theView.getSeuil();
				
				theModel.getDistanceValue();
				
				theView.setDistance(theModel.getDistanceValue());
			}
			catch(NumberFormatException ex){
				
			}
			
		}
		
	}
}
