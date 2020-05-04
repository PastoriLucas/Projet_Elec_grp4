
public class MVCProjet {

	public static void main(String[] args) {
		
		ProjetModel theModel = new ProjetModel();
		ProjetView theView = new ProjetView(theModel);
		ProjetController theController = new ProjetController(theView, theModel);
		
	}
}
