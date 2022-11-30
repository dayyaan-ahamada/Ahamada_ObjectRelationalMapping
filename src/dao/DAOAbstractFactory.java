package dao;

public abstract class DAOAbstractFactory implements I_DAOFactory {
    private static DAOAbstractFactory instance;

    protected DAOAbstractFactory() {
    }

    public static synchronized DAOAbstractFactory getInstance() {
        if (instance == null)
            instance = new DAORelationalAbstractFactory();
        return instance;
    }

    @Override
    public abstract I_DAOProduit createDAOProduit();

    @Override
    public abstract I_DAOCatalogue createDAOCatalogue();
}
