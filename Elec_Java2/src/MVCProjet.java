
public class MVCProjet {

	public static void main(String[] args) {
		ProjetView theView = new ProjetView();
		ProjetModel theModel = new ProjetModel();
		ProjetController theController = new ProjetController(theView, theModel);
	
		theView.setVisible(true);
	}
}
