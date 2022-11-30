package controller;

import dao.DAO;
import dao.DAOFactory;
import model.Catalogue;
import model.I_Produit;

public class ControllerAchatVente {
    private static final DAO dao = DAOFactory.getInstance().createDao();
    private static final Catalogue catalogue = Catalogue.getInstanceCatalogue();

    public static boolean enregistrerAchat(String nom, String quantite) {
        if (isNotPositiveNumeric(quantite)) return false;

        int quantiteProduit = Integer.parseInt(quantite);
        I_Produit p = dao.read(nom);
        boolean isAdding = p.ajouter(quantiteProduit);

        boolean isUpdating = dao.update(p);
        boolean isBuyingSupplies = catalogue.acheterStock(nom, quantiteProduit);

        return isAdding && isBuyingSupplies && isUpdating;
    }

    public static boolean enregistrerVente(String nom, String quantite) {
        if (isNotPositiveNumeric(quantite)) return false;

        int quantiteProduit = Integer.parseInt(quantite);
        I_Produit p = dao.read(nom);
        boolean isRemoving = p.enlever(quantiteProduit);

        boolean isSellingSupplies = catalogue.vendreStock(nom, quantiteProduit);
        boolean isUpdating = dao.update(p);

        return  isRemoving && isSellingSupplies && isUpdating;
    }

    private static boolean isNotPositiveNumeric(String quantite) {
        return !quantite.matches("-?\\d+");
    }
}
