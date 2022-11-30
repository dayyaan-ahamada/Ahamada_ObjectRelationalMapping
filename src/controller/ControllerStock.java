package controller;

import dao.DAO;
import dao.DAOFactory;
import model.Catalogue;

public class ControllerStock {
    private static final DAO dao = DAOFactory.getInstance().createDao();
    private static final Catalogue catalogue = Catalogue.getInstanceCatalogue();

    public static String afficherStocks() {
        return catalogue.toString();
    }

    public static String[] getNomProduits() {
        return catalogue.getNomProduits();
    }

    public static void initialisation() {
        catalogue.addProduits(dao.readAll());
    }
}
