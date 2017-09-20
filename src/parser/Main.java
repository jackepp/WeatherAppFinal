package parser;

/**
 * @author Jacob Permansson 2017-09-13
 *
 * This is the main class where we creates the objects and connects them together.
 *
 */

import controller.Controller;
import gui.GUI;

/**
 *This is the main class where we create the objects and connects them together.
 */
public class Main {

    public static void main(String[] args) {
        Parser parser = new Parser();
        Controller controller = new Controller(parser);
        GUI gui = new GUI(controller);

    }
}
