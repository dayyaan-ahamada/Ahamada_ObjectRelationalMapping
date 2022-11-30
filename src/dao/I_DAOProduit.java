package dao;

import model.I_Produit;

import java.util.List;

public interface I_DAOProduit {

    boolean create(I_Produit p,String nomCatalogue);

    I_Produit read(String nomProduit,String nomCatalogue);

    boolean update(I_Produit p,String nomCatalogue);

    boolean delete(I_Produit p,String nomCatalogue);

    List<I_Produit> readAll(String nomCatalogue);

}
