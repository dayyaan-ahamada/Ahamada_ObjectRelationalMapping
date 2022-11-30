DROP TABLE Produit;
DROP TABLE Catalogue;
PURGE RECYCLEBIN;

CREATE TABLE Catalogue
(
    numCatalogue NUMBER,
    nomCatalogue VARCHAR(25),
    CONSTRAINT pk_Catalogue PRIMARY KEY (numCatalogue),
    CONSTRAINT un_nomCatalogue UNIQUE (nomCatalogue)
);
CREATE TABLE Produit
(
    numProduit    NUMBER,
    nomProduit    VARCHAR(50),
    prixProduit   NUMBER,
    quantiteStock NUMBER,
    numCatalogue  NUMBER,
    CONSTRAINT pk_Produit PRIMARY KEY (numProduit),
    CONSTRAINT ck_prix_Produit CHECK (prixProduit > 0),
    CONSTRAINT ck_qte_Produit CHECK (quantiteStock >= 0),
    CONSTRAINT fk_numCatalogue FOREIGN KEY (numCatalogue) REFERENCES Catalogue (numCatalogue)
);

CREATE SEQUENCE seqCat
    START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE seqPro
    START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE PROCEDURE nouveauCatalogue(p_nomCatalogue IN Catalogue.nomCatalogue%TYPE) IS
BEGIN
    INSERT INTO Catalogue VALUES (seqCat.NEXTVAL, p_nomCatalogue);
END;

CREATE OR REPLACE PROCEDURE suppressionCatalogue(p_nomCatalogue IN Catalogue.nomCatalogue%TYPE) IS
BEGIN

    DELETE
    FROM Produit
    WHERE numCatalogue =
          (SELECT numCatalogue
           FROM Catalogue
           WHERE nomCatalogue = p_nomCatalogue);

    DELETE
    FROM Catalogue
    WHERE nomCatalogue = p_nomCatalogue;
END;

CREATE OR REPLACE PROCEDURE nouveauProduit(p_nomProduit IN Produit.nomProduit%TYPE,
                                           p_prixProduit IN Produit.prixProduit%TYPE,
                                           p_quantiteStock IN Produit.quantiteStock%TYPE,
                                           p_nomCatalogue IN Catalogue.nomCatalogue%TYPE) IS

    v_nombreProduit NUMBER;
    v_numCatalogue  Produit.numCatalogue%TYPE;

BEGIN

    SELECT numCatalogue
    INTO v_numCatalogue
    FROM Catalogue
    WHERE nomCatalogue = p_nomCatalogue;

    SELECT COUNT(*)
    INTO v_nombreProduit
    FROM Produit
    WHERE nomProduit = p_nomProduit
      AND numCatalogue = v_numCatalogue;

    IF v_nombreProduit = 1 THEN
        RAISE_APPLICATION_ERROR(-20003, 'Ajout impossible; Un produit a déjà ce nom');
    END IF;

    INSERT INTO Produit
    VALUES (seqPro.NEXTVAL, p_nomProduit, p_prixProduit, p_quantiteStock, v_numCatalogue);

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20008, 'Ajout impossible; Aucun catalogue de ce nom');

END;

CALL nouveauCatalogue('Noitaicossa');
CALL nouveauCatalogue('Le Redoutable');
CAll nouveauCatalogue('Formacia');

CALL nouveauProduit('Séjour à Hanga Roa', 2400, 3, 'Noitaicossa');
CALL nouveauProduit('Séjour à Luleå', 970, 6, 'Noitaicossa');
CALL nouveauProduit('Séjour à Antigua-et-Barbuda', 3780, 2, 'Noitaicossa');
CALL nouveauProduit('Séjour à La Réunion', 1999, 1, 'Noitaicossa');
CALL nouveauProduit('Nuitée chambre simple', 70, 75, 'Le Redoutable');
CALL nouveauProduit('Nuitée chambre double', 130, 30, 'Le Redoutable');
CALL nouveauProduit('Nuitée suite nuptiale', 400, 2, 'Le Redoutable');
CALL nouveauProduit('Nuitée suite royale', 750, 1, 'Le Redoutable');
CALL nouveauProduit('Mars', 1.2, 5, 'Formacia');
CALL nouveauProduit('Feuille A4', 0.1, 550, 'Formacia');
CALL nouveauProduit('Casque WH-1000XM3', 300, 15, 'Formacia');
CALL nouveauProduit('Tomate', 0.8, 30, 'Formacia');
CALL nouveauProduit('Tamarin', 1, 66, 'Formacia');

CALL suppressionCatalogue('Noitaicossa');


SELECT *
FROM Catalogue
         NATURAL JOIN Produit;