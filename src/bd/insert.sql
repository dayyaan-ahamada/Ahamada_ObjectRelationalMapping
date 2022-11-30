DROP TABLE Produit;
PURGE RECYCLEBIN;

CREATE TABLE Produit
(
    numProduit    NUMBER,
    nomProduit    VARCHAR(25),
    prixProduit   NUMBER,
    quantiteStock NUMBER,
    CONSTRAINT pk_Produit PRIMARY KEY (numProduit),
    CONSTRAINT un_nom_Produit UNIQUE (nomProduit),
    CONSTRAINT ck_prix_Produit CHECK (prixProduit > 0),
    CONSTRAINT  ck_qte_Produit CHECK (quantiteStock >= 0)
);

CREATE SEQUENCE seqPro
    START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE PROCEDURE nouveauProduit(p_nomProduit IN Produit.nomProduit%TYPE,
                                p_prixProduit IN Produit.prixProduit%TYPE,
                                p_quantiteStock IN Produit.quantiteStock%TYPE) IS
BEGIN
    INSERT INTO Produit VALUES (seqPro.NEXTVAL, p_nomProduit, p_prixProduit, p_quantiteStock);
END;

CALL nouveauProduit('Mars',1.2,5);
CALL nouveauProduit('Volvic',0.4,75);
CALL nouveauProduit('Feuille A4',0.1,550);
CALL nouveauProduit('Casque WH-1000XM3',300,15);
CALL nouveauProduit('Tomate',0.6,8);