//package dao;
//
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import static com.mongodb.client.model.Projections.*;
//import org.bson.Document;
//import java.util.ArrayList;
//import java.util.List;
//
//import model.I_Produit;
//import model.Produit;
//
//public class DAONoSQLProduit implements I_DAOProduit {
//    MongoDatabase database;
//
//    public DAONoSQLProduit(MongoDatabase mongoDatabase) {
//        database = mongoDatabase;
//    }
//
//    @Override
//    public boolean create(I_Produit p, String nomCatalogue) {
//        Document produit = new Document()
//                .append("nomProduit", p.getNom())
//                .append("prixProduit", String.valueOf(p.getPrixUnitaireHT()))
//                .append("quantiteStock", String.valueOf(p.getQuantite()))
//                .append("nomCatalogue", nomCatalogue);
//        MongoCollection<Document> collectionProduits = database.getCollection("Produits");
//        collectionProduits.insertOne(produit);
//        return true;
//    }
//
//    @Override
//    public I_Produit read(String nomProduit, String nomCatalogue) {
//        MongoCollection<Document> collectionProduits = database.getCollection("Produits");
//        I_Produit produit = null;
//        for (Document d : collectionProduits.find((new Document("nomProduit", nomProduit)
//                .append("nomCatalogue", nomCatalogue))).projection(fields(
//                include("prixProduit", "quantiteStock"),
//                excludeId()))) {
//            String[] valeurProduit = d.values().toArray(new String[0]);
//            double prixProduit = Double.parseDouble(valeurProduit[0]);
//            int quantiteProduit = Integer.parseInt(valeurProduit[1]);
//            produit = new Produit(nomProduit, prixProduit, quantiteProduit);
//        }
//        return produit;
//    }
//
//    @Override
//    public boolean update(I_Produit p, String nomCatalogue) {
//        MongoCollection<Document> collectionProduits = database.getCollection("Produits");
//        collectionProduits.updateOne(new Document("nomProduit",p.getNom())
//                .append("nomCatalogue", nomCatalogue),
//                new Document("$set",new Document("quantiteStock",String.valueOf(p.getQuantite()))));
//        return true;
//    }
//
//    @Override
//    public boolean delete(I_Produit p, String nomCatalogue) {
//        Document produit = new Document()
//                .append("nomProduit", p.getNom())
//                .append("nomCatalogue", nomCatalogue);
//        database.getCollection("Produits").deleteMany(produit);
//        return true;
//    }
//
//    @Override
//    public List<I_Produit> readAll(String nomCatalogue) {
//        List<I_Produit> produits = new ArrayList<>();
//        MongoCollection<Document> collectionProduits = database.getCollection("Produits");
//        for (Document d : collectionProduits.find((new Document("nomCatalogue", nomCatalogue)))
//                .projection(fields(include("nomProduit", "prixProduit", "quantiteStock"),
//                excludeId()))) {
//            String[] valeurProduit = d.values().toArray(new String[0]);
//            double prixProduit = Double.parseDouble(valeurProduit[1]);
//            int quantiteProduit = Integer.parseInt(valeurProduit[2]);
//            produits.add(new Produit(valeurProduit[0], prixProduit, quantiteProduit));
//        }
//        return produits;
//    }
//}
