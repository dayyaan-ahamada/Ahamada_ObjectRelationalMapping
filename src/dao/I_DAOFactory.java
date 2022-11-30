package dao;

public interface I_DAOFactory {

    I_DAOProduit createDAOProduit();

    I_DAOCatalogue createDAOCatalogue();
}
