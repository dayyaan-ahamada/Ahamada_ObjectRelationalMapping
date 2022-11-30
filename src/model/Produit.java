package model;

public class Produit implements I_Produit {
    private int quantiteStock;
    private String nom;
    private double prixUnitaireHT;
    private static final double tauxTVA = 0.2;

    public Produit(String nom, double prixUnitaireHT, int quantiteStock) {
        this.quantiteStock = quantiteStock;
        this.nom = nom;
        this.prixUnitaireHT = prixUnitaireHT;
    }

    @Override
    public boolean ajouter(int qteAchetee) {
        if (qteAchetee <= 0)
            return false;
        quantiteStock += qteAchetee;
        return true;
    }

    @Override
    public boolean enlever(int qteVendue) {
        if (qteVendue <= 0 || quantiteStock - qteVendue < 0)
            return false;
        quantiteStock -= qteVendue;
        return true;
    }

    @Override
    public double getPrixUnitaireHT() {
        return prixUnitaireHT;
    }

    @Override
    public double getPrixUnitaireTTC() {
        return prixUnitaireHT * (1 + tauxTVA);
    }

    @Override
    public double getPrixStockTTC() {
        return getPrixUnitaireTTC() * quantiteStock;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public int getQuantite() {
        return quantiteStock;
    }

    @Override
    public String toString() {
        return nom +
                " - prix HT : " + getPrixUnitaireHT() +
                " € - prix TTC : " + getPrixUnitaireTTC() +
                " € - quantité en stock : " + quantiteStock + "\n";
    }
}
