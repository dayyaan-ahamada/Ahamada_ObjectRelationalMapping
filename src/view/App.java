package view;

import controller.ControllerStock;

public class App {
    public static void main(String[] args) {
        ControllerStock.initialisation();
        new FenetrePrincipale();
    }
}
