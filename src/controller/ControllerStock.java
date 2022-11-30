package controller;

import model.Catalogue;

public class ControllerStock {
    private static final Catalogue catalogue = Catalogue.getInstanceCatalogue();

    public static String afficherStocks(){
        return catalogue.toString();
    }
    public static String[] getNomProduits(){
        return catalogue.getNomProduits();
    }
    public static String getNomCatalogue(){
        return catalogue.getNom();
    }
}
