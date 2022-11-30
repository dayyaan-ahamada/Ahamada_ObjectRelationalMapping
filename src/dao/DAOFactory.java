package dao;

public class DAOFactory {
    private static DAOFactory instance;

    public DAO createDao() {
        return new DAOProduitXMLAdaptater();
    }

    public static synchronized DAOFactory getInstance() {
        if (instance == null)
            instance = new DAOFactory();
        return instance;
    }
}
