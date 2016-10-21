package ca.qc.bdeb.p55.smartshopplus.modele;

import android.graphics.Bitmap;

/**
 * Created by C A T A on 2016-10-07.
 */

public class Produit {

    public static final double MULTIPLICATEUR_PRIX_UNITAIRE = 100d;

    private long id;
    private long idMagasinFk;
    private String nom;
    private double quantite;
    private String typeQuantite;
    private double prix;
    private boolean enRabais;
    private float qualite;
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
     * @param prix         le prix du produit
     * @param enRabais     si l'item est en rabais
     * @param qualite      la qualité du produit
     * @param image        l'image du produit
     */
    public Produit(long idMagasinFk, String nom, double quantite, String typeQuantite, double prix,
                   boolean enRabais, float qualite, Bitmap image) {
        this.idMagasinFk = idMagasinFk;
        this.nom = nom;
        this.quantite = quantite;
        this.typeQuantite = typeQuantite;
        this.prix = prix;
        this.enRabais = enRabais;
        this.qualite = qualite;
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
     * @param prix         le prix du produit
     * @param enRabais     si l'item est en rabais
     * @param qualite      la qualité du produit
     * @param image        l'image du produit
     */
    public Produit(long id, long idMagasinFk, String nom, double quantite, String typeQuantite,
                   double prix, boolean enRabais, float qualite, Bitmap image) {
        this.id = id;
        this.idMagasinFk = idMagasinFk;
        this.nom = nom;
        this.quantite = quantite;
        this.typeQuantite = typeQuantite;
        this.prix = prix;
        this.enRabais = enRabais;
        this.qualite = qualite;
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

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
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

    public boolean isEnRabais() {
        return enRabais;
    }

    public void setEnRabais(boolean enRabais) {
        this.enRabais = enRabais;
    }

    public float getQualite() {
        return qualite;
    }

    public void setQualite(float qualite) {
        this.qualite = qualite;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
