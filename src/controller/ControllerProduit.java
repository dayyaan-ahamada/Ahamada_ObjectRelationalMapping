package controller;

import dao.I_DAOProduit;
import dao.DAOAbstractFactory;
import model.Catalogue;
import model.Produit;

public class ControllerProduit {
    private static final I_DAOProduit daoProduit = DAOAbstractFactory.getInstance().createDAOProduit();
    private static final Catalogue catalogue = Catalogue.getInstanceCatalogue();

    public static boolean createProduit(String nom, String prix, String quantite) {
        if (isNotPositiveDecimal(prix) || isNotPositiveNumeric(quantite)) return false;
        double prixProduit = Double.parseDouble(prix);
        int quantiteProduit = Integer.parseInt(quantite);

        boolean isAdding = catalogue.addProduit(nom, prixProduit, quantiteProduit);
        boolean isInserting = daoProduit.create(new Produit(nom, prixProduit, quantiteProduit),catalogue.getNom());

        return isAdding && isInserting;
    }

    public static boolean supprimerProduit(String nom) {
        boolean isRemoving = catalogue.removeProduit(nom);
        boolean isDeleting = daoProduit.delete(daoProduit.read(nom,catalogue.getNom()),catalogue.getNom());

        return isRemoving && isDeleting;
    }

    private static boolean isNotPositiveDecimal(String prix) {
        return !prix.matches("-?\\d+(\\.\\d+)?");
    }

    private static boolean isNotPositiveNumeric(String quantite) {
        return !quantite.matches("-?\\d+");
    }
}