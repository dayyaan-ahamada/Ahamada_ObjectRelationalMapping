package dao;

import model.I_Produit;

import java.util.List;

public interface DAO {

    boolean create(I_Produit p);

    I_Produit read(String nomProduit);

    boolean update(I_Produit p);

    boolean delete(I_Produit p);

    List<I_Produit> readAll();
}
