package controller;

import dao.DAO;
import dao.DAOFactory;
import model.Catalogue;
import model.Produit;

public class ControllerProduit {
    private static final DAO dao = DAOFactory.getInstance().createDao();
    private static final Catalogue catalogue = Catalogue.getInstanceCatalogue();

    public static boolean createProduit(String nom, String prix, String quantite) {
        if (isNotPositiveDecimal(prix) || isNotPositiveNumeric(quantite)) return false;
        double prixProduit = Double.parseDouble(prix);
        int quantiteProduit = Integer.parseInt(quantite);

        boolean isAdding = catalogue.addProduit(nom, prixProduit, quantiteProduit);
        boolean isInserting = dao.create(new Produit(nom, prixProduit, quantiteProduit));

        return isAdding && isInserting;
    }

    public static boolean supprimerProduit(String nom) {
        boolean isRemoving = catalogue.removeProduit(nom);
        boolean isDeleting = dao.delete(dao.read(nom));

        return isRemoving && isDeleting;
    }

    private static boolean isNotPositiveDecimal(String prix) {
        return !prix.matches("-?\\d+(\\.\\d+)?");
    }

    private static boolean isNotPositiveNumeric(String quantite) {
        return !quantite.matches("-?\\d+");
    }
}