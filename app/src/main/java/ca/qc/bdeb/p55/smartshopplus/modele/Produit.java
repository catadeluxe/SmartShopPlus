package ca.qc.bdeb.p55.smartshopplus.modele;

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


    /**
     * Constructeur par défaut ne nécessitrant aucun paramètre
     */
    public Produit() {
    }

    /**
     * Constructeur nécessitant tous les paramètres sauf l'id
     *
     * @param idMagasinFk l'id du magasin où le produit se retrouve
     * @param nom le nom du produit
     * @param quantite la quantité de produit
     * @param typeQuantite le type de quantité (ex. l, ml, g, unités etc.)
     * @param prix
     */
    public Produit(long idMagasinFk, String nom, long quantite, String typeQuantite, double prix) {
        this.idMagasinFk = idMagasinFk;
        this.nom = nom;
        this.quantite = quantite;
        this.typeQuantite = typeQuantite;
        this.prix = prix;
    }

    /**
     * Constructeur nécessitant tous les paramètres
     * @param id
     * @param idMagasinFk
     * @param nom
     * @param quantite
     * @param typeQuantite
     * @param prix
     */
    public Produit(long id, long idMagasinFk, String nom, long quantite, String typeQuantite, double prix) {
        this.id = id;
        this.idMagasinFk = idMagasinFk;
        this.nom = nom;
        this.quantite = quantite;
        this.typeQuantite = typeQuantite;
        this.prix = prix;
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
}