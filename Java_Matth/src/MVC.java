package Final;

public class MVC {

    public static void main(String[] args) {
        GUI gui = new GUI();
        Model model = new Model();
        Controller controller = new Controller(gui, model);
        controller.searchForPorts();

    }
}