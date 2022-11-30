package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAORelationalAbstractFactory extends DAOAbstractFactory {
    Connection cn;

    protected DAORelationalAbstractFactory() {

        String url = "jdbc:oracle:thin:@162.38.222.149:1521:iut";
        String login = "ahamadad";
        String mdp = "21092020";
        try {
            Class<?> driverClass = Class.forName("oracle.jdbc.driver.OracleDriver");
            cn = DriverManager.getConnection(url, login, mdp);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public I_DAOProduit createDAOProduit() {
        return new DAOProduit(cn);
    }

    @Override
    public I_DAOCatalogue createDAOCatalogue() {
        return new DAOCatalogue(cn);
    }

}
