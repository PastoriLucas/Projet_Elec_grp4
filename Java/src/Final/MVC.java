package Final;

public class MVC {

	public static void main(String[] args) {
		Model model = new Model();
		Controller controller = new Controller(model);
		GUI gui = new GUI();
		gui.addController(controller);
		controller.AddGUI(gui);
		controller.searchForPorts();

	}
}