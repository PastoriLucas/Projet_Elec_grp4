public class MVCProjet {

	public static void main(String[] args) {
		ProjetView theView = new ProjetView();
		ProjetModel theModel = new ProjetModel();
		ProjetController theController = new ProjetController(theView, theModel, theModel.monCommPort);
		 
		Object selectedItem = theView.choixPort.getSelectedItem();

	        String com = selectedItem.toString();
	        theController.SimpleRead(com);
	}
}
