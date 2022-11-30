package controller;

import dao.DAOAbstractFactory;
import dao.I_DAOCatalogue;
import dao.I_DAOProduit;
import model.Catalogue;


public class ControllerCatalogue {
    private static final I_DAOCatalogue daoCatalogue = DAOAbstractFactory.getInstance().createDAOCatalogue();
    private static final I_DAOProduit daoProduit = DAOAbstractFactory.getInstance().createDAOProduit();

    public static boolean createCatalogue(String nom) {
        return daoCatalogue.create(nom);
    }

    public static boolean supprimerCatalogue(String nom) {
        return daoCatalogue.delete(nom);
    }

    public static String[] afficherCatalogues() {
        return daoCatalogue.readAll();
    }

    public static String[] afficherCataloguesEtNbProduits() {
        int nbProduits;
        String[] catalogues = afficherCatalogues();
        String[] cataloguesAvecNbProduits = new String[catalogues.length];
        for (int i = 0; i < catalogues.length; i++) {
            String catalogue = catalogues[i];
            nbProduits = daoProduit.readAll(catalogue).size();
            cataloguesAvecNbProduits[i] = catalogue + " : " + nbProduits + " produits";
        }
        return cataloguesAvecNbProduits;
    }

    public static int getNbCatalogues() {
        return daoCatalogue.readAll().length;
    }

    public static void selectionnerCatalogue(String nom){
        Catalogue catalogue = Catalogue.getInstanceCatalogue();
        catalogue.setNom(nom);
        catalogue.addProduits(daoProduit.readAll(nom));
    }
}
