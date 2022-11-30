package controller;

import dao.I_DAOProduit;
import dao.DAOAbstractFactory;
import model.Catalogue;
import model.I_Produit;

public class ControllerAchatVente {
    private static final I_DAOProduit daoProduit = DAOAbstractFactory.getInstance().createDAOProduit();
    private static final Catalogue catalogue = Catalogue.getInstanceCatalogue();

    public static boolean enregistrerAchat(String nom, String quantite) {
        if (isNotPositiveNumeric(quantite)) return false;

        int quantiteProduit = Integer.parseInt(quantite);
        I_Produit p = daoProduit.read(nom,catalogue.getNom());
        boolean isAdding = p.ajouter(quantiteProduit);

        boolean isUpdating = daoProduit.update(p,catalogue.getNom());
        boolean isBuyingSupplies = catalogue.acheterStock(nom, quantiteProduit);

        return isAdding && isBuyingSupplies && isUpdating;
    }

    public static boolean enregistrerVente(String nom, String quantite) {
        if (isNotPositiveNumeric(quantite)) return false;

        int quantiteProduit = Integer.parseInt(quantite);
        I_Produit p = daoProduit.read(nom,catalogue.getNom());
        boolean isRemoving = p.enlever(quantiteProduit);

        boolean isSellingSupplies = catalogue.vendreStock(nom, quantiteProduit);
        boolean isUpdating = daoProduit.update(p,catalogue.getNom());

        return  isRemoving && isSellingSupplies && isUpdating;
    }

    private static boolean isNotPositiveNumeric(String quantite) {
        return !quantite.matches("-?\\d+");
    }
}
