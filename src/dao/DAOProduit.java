package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.I_Produit;
import model.Produit;

public class DAOProduit implements I_DAOProduit {
    private final Connection cn;

    public DAOProduit(Connection connection) {
        cn = connection;
    }

    @Override
    public boolean create(I_Produit p,String nomCatalogue) {
        CallableStatement cst;
        String nom = p.getNom();
        double prix = p.getPrixUnitaireHT();
        int qte = p.getQuantite();
        try {
            cst = cn.prepareCall("{CALL nouveauProduit(?,?,?,?)}");
            cst.setString(1, nom);
            cst.setDouble(2, prix);
            cst.setInt(3, qte);
            cst.setString(4, nomCatalogue);
            return cst.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public I_Produit read(String nomProduit,String nomCatalogue) {
        PreparedStatement pst;
        ResultSet rs;
        try {
            pst = cn.prepareStatement("" +
                    "SELECT nomProduit, prixProduit, quantiteStock FROM Produit " +
                    "NATURAL JOIN Catalogue WHERE nomProduit = ? AND nomCatalogue = ?");
            pst.setString(1, nomProduit);
            pst.setString(2, nomCatalogue);
            rs = pst.executeQuery();
            if (rs.next())
                return new Produit(rs.getString(1), rs.getDouble(2), rs.getInt(3));
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(I_Produit p,String nomCatalogue) {
        PreparedStatement pst;
        try {
            pst = cn.prepareStatement("" +
                    "UPDATE Produit " +
                    "SET quantiteStock = ? " +
                    "WHERE numProduit = " +
                    "(SELECT numProduit FROM Produit NATURAL JOIN Catalogue " +
                    "WHERE nomProduit = ? AND nomCatalogue = ?)");
            pst.setInt(1, p.getQuantite());
            pst.setString(2, p.getNom());
            pst.setString(3, nomCatalogue);
            return pst.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(I_Produit p,String nomCatalogue) {
        PreparedStatement pst;
        try {
            pst = cn.prepareStatement("" +
                    "DELETE FROM Produit WHERE numProduit =" +
                    " (SELECT numProduit FROM Produit NATURAL JOIN Catalogue " +
                    "WHERE nomProduit = ? AND nomCatalogue = ?)");
            pst.setString(1, p.getNom());
            pst.setString(2, nomCatalogue);
            return pst.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public List<I_Produit> readAll(String nomCatalogue) {
        PreparedStatement pst;
        ResultSet rs;
        List<I_Produit> listProduits = new ArrayList<>();
        try {
            pst = cn.prepareStatement("" +
                    "SELECT nomProduit,prixProduit,quantiteStock, nomCatalogue FROM Catalogue NATURAL JOIN Produit WHERE nomCatalogue = ? ");
            pst.setString(1, nomCatalogue);
            rs = pst.executeQuery();
            while (rs.next())
                listProduits.add(new Produit(rs.getString(1), rs.getDouble(2), rs.getInt(3)));
            return listProduits;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
