//package dao;
//
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import static com.mongodb.client.model.Projections.*;
//import org.bson.Document;
//
//public class DAONoSQLCatalogue implements I_DAOCatalogue {
//    MongoDatabase database;
//
//    public DAONoSQLCatalogue(MongoDatabase mongoDatabase) {
//        database = mongoDatabase;
//    }
//
//    @Override
//    public boolean create(String nom) {
//        Document catalogue = new Document()
//                .append("nomCatalogue", nom);
//        MongoCollection<Document> collectionCatalogues = database.getCollection("Catalogues");
//        collectionCatalogues.insertOne(catalogue);
//        return true;
//    }
//
//    @Override
//    public boolean delete(String nom) {
//        database.getCollection("Catalogues").deleteMany(new Document("nomCatalogue", nom));
//        return true;
//    }
//
//    @Override
//    public String[] readAll() {
//        MongoCollection<Document> collectionCatalogues = database.getCollection("Catalogues");
//        String[] catalogues = new String[(int) collectionCatalogues.count()];
//        int i = 0;
//        for (Document d : collectionCatalogues.find().projection(fields(include("nomCatalogue"),
//                excludeId()))) {
//            String toPrettify = String.valueOf(d.values());
//            catalogues[i] = prettify(toPrettify);
//            i++;
//        }
//        return catalogues;
//    }
//
//    public String prettify(String toPrettify) {
//        return toPrettify
//                .replace("[", "")
//                .replace("]", "");
//    }
//}
