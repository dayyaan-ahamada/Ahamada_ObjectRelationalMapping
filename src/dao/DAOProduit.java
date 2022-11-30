package dao;

import model.I_Produit;
import model.Produit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOProduit implements DAO {
    protected Connection cn;

    public DAOProduit() {
        String url = "jdbc:oracle:thin:@162.38.222.149:1521:iut";
        String login = "ahamadad";
        String mdp = "21092020";
        try {
            Class<?> driverClass = Class.forName("oracle.jdbc.driver.OracleDriver");
            cn = DriverManager.getConnection(url, login, mdp);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    @Override
    public boolean create(I_Produit p) {
        CallableStatement cst;
        String nom = p.getNom();
        double prix = p.getPrixUnitaireHT();
        int qte = p.getQuantite();
        try {
            cst = cn.prepareCall("{CALL nouveauProduit(?,?,?)}");
            cst.setString(1, nom);
            cst.setDouble(2, prix);
            cst.setInt(3, qte);
            return cst.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public I_Produit read(String nomProduit) {
        PreparedStatement pst;
        ResultSet rs;
        try {
            pst = cn.prepareStatement("" +
                    "SELECT nomProduit, prixProduit, quantiteStock FROM Produit WHERE nomProduit = ?");
            pst.setString(1, nomProduit);
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
    public boolean update(I_Produit p) {
        PreparedStatement pst;
        try {
            pst = cn.prepareStatement("" +
                    "UPDATE Produit " +
                    "SET quantiteStock = ? " +
                    "WHERE nomProduit = ?");
            pst.setInt(1, p.getQuantite());
            pst.setString(2, p.getNom());
            return pst.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(I_Produit p) {
        PreparedStatement pst;
        try {
            pst = cn.prepareStatement("" +
                    "DELETE FROM Produit WHERE nomProduit = ?");
            pst.setString(1, p.getNom());
            return pst.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public List<I_Produit> readAll() {
        PreparedStatement pst;
        ResultSet rs;
        List<I_Produit> listProduits = new ArrayList<>();
        try {
            pst = cn.prepareStatement("" +
                    "SELECT nomProduit, prixProduit, quantiteStock FROM Produit");
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
