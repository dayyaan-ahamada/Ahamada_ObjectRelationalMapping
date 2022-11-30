package dao;


public interface I_DAOCatalogue {

    boolean create(String  nom);

    boolean delete(String nom);

    String[] readAll();

}
