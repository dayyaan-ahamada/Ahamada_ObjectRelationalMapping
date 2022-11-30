package model;

public interface I_Produit {

	boolean ajouter(int qteAchetee);
	boolean enlever(int qteVendue);
	double getPrixUnitaireHT();
	double getPrixUnitaireTTC();
	double getPrixStockTTC();
	String getNom();
	int getQuantite();
	String toString();
}