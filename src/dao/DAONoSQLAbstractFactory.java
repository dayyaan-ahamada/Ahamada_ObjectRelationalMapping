//package dao;
//
//import com.mongodb.MongoClient;
//import com.mongodb.client.MongoDatabase;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class DAONoSQLAbstractFactory extends DAOAbstractFactory {
//    MongoClient mongoClient;
//    MongoDatabase mongoDatabase;
//
//    protected DAONoSQLAbstractFactory() {
//        mongoClient = new MongoClient("localhost",27017);
//        mongoDatabase = mongoClient.getDatabase("db_Ahamada");
//        Logger.getLogger("org.mongodb").setLevel(Level.WARNING);
//    }
//
//    @Override
//    public I_DAOProduit createDAOProduit() {
//        return new DAONoSQLProduit(mongoDatabase);
//    }
//
//    @Override
//    public I_DAOCatalogue createDAOCatalogue() {
//        return new DAONoSQLCatalogue(mongoDatabase);
//    }
//
//}
