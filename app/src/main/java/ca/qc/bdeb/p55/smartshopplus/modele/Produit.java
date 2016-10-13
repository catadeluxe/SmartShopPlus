package ca.qc.bdeb.p55.smartshopplus.modele;

import android.graphics.Bitmap;

/**
 * Created by C A T A on 2016-10-07.
 */

public class Produit {

    private long id;
    private long idMagasinFk;
    private String nom;
    private long quantite;
    private String typeQuantite;
    private double prix;
    private double prixUnitaire;
    private Bitmap image;


    /**
     * Constructeur par défaut ne nécessitrant aucun paramètre
     */
    public Produit() {
    }

    /**
     * Constructeur nécessitant tous les paramètres sauf l'id du produit
     *
     * @param idMagasinFk  l'id du magasin où le produit se retrouve
     * @param nom          le nom du produit
     * @param quantite     la quantité de produit
     * @param typeQuantite le type de quantité (ex. l, ml, g, unités etc.)
     * @param prix         le txtPrix du produit
     * @param prixUnitaire le txtPrix unitaire du produit
     * @param image        l'ivwImageProduit du produit
     */
    public Produit(long idMagasinFk, String nom, long quantite, String typeQuantite, double prix,
                   double prixUnitaire, Bitmap image) {
        this.idMagasinFk = idMagasinFk;
        this.nom = nom;
        this.quantite = quantite;
        this.typeQuantite = typeQuantite;
        this.prix = prix;
        this.prixUnitaire = prixUnitaire;
        this.image = image;
    }

    /**
     * Constructeur nécessitant tous les paramètres
     *
     * @param id           l'id du produit
     * @param idMagasinFk  l'id du magasin où le produit se retrouve
     * @param nom          le nom du produit
     * @param quantite     la quantité de produit
     * @param typeQuantite le type de quantité (ex. l, ml, g, unités etc.)
     * @param prix         le txtPrix du produit
     * @param prixUnitaire le txtPrix unitaire du produit
     * @param image        l'ivwImageProduit du produit
     */
    public Produit(long id, long idMagasinFk, String nom, long quantite, String typeQuantite,
                   double prix, double prixUnitaire, Bitmap image) {
        this.id = id;
        this.idMagasinFk = idMagasinFk;
        this.nom = nom;
        this.quantite = quantite;
        this.typeQuantite = typeQuantite;
        this.prix = prix;
        this.prixUnitaire = prixUnitaire;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdMagasinFk() {
        return idMagasinFk;
    }

    public void setIdMagasinFk(long idMagasinFk) {
        this.idMagasinFk = idMagasinFk;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public long getQuantite() {
        return quantite;
    }

    public void setQuantite(long quantite) {
        this.quantite = quantite;
    }

    public String getTypeQuantite() {
        return typeQuantite;
    }

    public void setTypeQuantite(String typeQuantite) {
        this.typeQuantite = typeQuantite;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
