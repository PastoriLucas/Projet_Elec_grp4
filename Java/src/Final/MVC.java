package Final;

public class MVC {

	public static void main(String[] args) {
		Model model = new Model();							//Création Modèle
		Controller controller = new Controller(model);		//Création Contrôleur
		GUI gui = new GUI();								//Création Vue
		gui.addController(controller);
		controller.AddGUI(gui);
		controller.searchForPorts();						//Recherche des ports COM ouverts
	}
}