package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOCatalogue implements I_DAOCatalogue {
    private final Connection cn;

    public DAOCatalogue(Connection connection) {
        cn = connection;
    }

    @Override
    public boolean create(String nom) {
        CallableStatement cst;
        try {
            cst = cn.prepareCall("{CALL nouveauCatalogue(?)}");
            cst.setString(1, nom);
            return cst.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String nom) {
        CallableStatement cst;
        try {
            cst = cn.prepareCall("{CALL suppressionCatalogue(?)}");
            cst.setString(1, nom);
            return cst.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public String[] readAll() {
        PreparedStatement pst;
        ResultSet rs;
        List<String> listCatalogues = new ArrayList<>();
        try {
            pst = cn.prepareStatement("" +
                    "SELECT nomCatalogue FROM Catalogue ORDER BY nomCatalogue");
            rs = pst.executeQuery();
            while (rs.next())
                listCatalogues.add(rs.getString(1));
            return listCatalogues.toArray(new String[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
