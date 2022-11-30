package model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Catalogue implements I_Catalogue {

    private String nom;
    private List<I_Produit> lesProduits;
    private static Catalogue instanceCatalogue;

    public Catalogue() {
        this.lesProduits = new ArrayList<>();
    }

    public synchronized static Catalogue getInstanceCatalogue() {
        if (instanceCatalogue == null)
            instanceCatalogue = new Catalogue();
        return instanceCatalogue;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean addProduit(String nom, double prix, int qte) {
        String nomSansEspace = nom.replaceAll("\\t", " ").trim();
        for (String nomProduit : getNomProduits())
            if (nomSansEspace.equals(nomProduit))
                return false;
        if (prix <= 0 || qte < 0)
            return false;
        return lesProduits.add(new Produit(nomSansEspace, prix, qte));
    }

    @Override
    public boolean addProduit(I_Produit produit) {
        if (produit != null)
            return addProduit(produit.getNom(), produit.getPrixUnitaireHT(), produit.getQuantite());
        return false;
    }

    @Override
    public int addProduits(List<I_Produit> l) {
        int initialSize = lesProduits.size();
        if (l != null)
            for (I_Produit produit : l)
                addProduit(produit);

        return lesProduits.size() - initialSize;
    }

    @Override
    public boolean removeProduit(String nom) {
        return lesProduits.removeIf(p -> p.getNom().equals(nom));
    }

    @Override
    public boolean acheterStock(String nomProduit, int qteAchetee) {
        for (I_Produit produit : lesProduits)
            if (produit.getNom().equals(nomProduit))
                return produit.ajouter(qteAchetee);
        return false;
    }

    @Override
    public boolean vendreStock(String nomProduit, int qteVendue) {
        for (I_Produit produit : lesProduits)
            if (produit.getNom().equals(nomProduit))
                return produit.enlever(qteVendue);
        return false;
    }

    @Override
    public String[] getNomProduits() {
        String[] nomProduits = new String[lesProduits.size()];
        for (int i = 0; i < lesProduits.size(); i++)
            nomProduits[i] = lesProduits.get(i).getNom().replaceAll("\\t", " ");
        Arrays.sort(nomProduits);
        return nomProduits;
    }

    @Override
    public double getMontantTotalTTC() {
        double montantTotalTTC = 0.0;
        for (I_Produit produit : lesProduits)
            montantTotalTTC += produit.getPrixStockTTC();
        return (double) Math.round(montantTotalTTC * 100) / 100;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.FRANCE));
        StringBuilder produits = new StringBuilder();
        for (I_Produit produit : lesProduits)
            produits.
                    append(produit.getNom()).
                    append(" - prix HT : ").
                    append(df.format(produit.getPrixUnitaireHT())).
                    append(" € - prix TTC : ").
                    append(df.format(produit.getPrixUnitaireTTC())).
                    append(" € - quantité en stock : ").
                    append(produit.getQuantite()).
                    append("\n");

        produits.
                append("\nMontant total TTC du stock : ").
                append(df.format(getMontantTotalTTC())).
                append(" €");

        return produits.toString();
    }

    @Override
    public void clear() {
        lesProduits.clear();
    }
}
