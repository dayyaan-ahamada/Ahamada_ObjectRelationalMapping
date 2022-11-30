package dao;

import model.I_Produit;

import java.util.List;

public class DAOProduitXMLAdaptater implements DAO {
    DAOProduitXML dao_xml = new DAOProduitXML();

    @Override
    public boolean create(I_Produit p) {
        return dao_xml.creer(p);
    }

    @Override
    public I_Produit read(String nomProduit) {
        return dao_xml.lire(nomProduit);
    }

    @Override
    public boolean update(I_Produit p) {
        return dao_xml.maj(p);
    }

    @Override
    public boolean delete(I_Produit p) {
        return dao_xml.supprimer(p);
    }

    @Override
    public List<I_Produit> readAll() {
        return dao_xml.lireTous();
    }
}
