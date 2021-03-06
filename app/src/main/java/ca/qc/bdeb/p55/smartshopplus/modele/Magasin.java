package ca.qc.bdeb.p55.smartshopplus.modele;

import android.graphics.Bitmap;

/**
 * Created by Catalin on 2016-09-14.
 */
public class Magasin {
    private long id;
    private String nom;
    private Bitmap image;

    /**
     * Constructer par défaut qui ne prend aucun paramètre
     */
    public Magasin() {
        id = -1;
    }
    
    /**
     * Constructeur qui prend en paramètre le nom du magasin
     *
     * @param nom le nom du magasin
     */
    public Magasin(String nom) {
        id = -1;
        this.nom = nom;
    }

    /**
     * Constructeur qui prend en paramètre le nom du magasin et l'ivwImageProduit
     *
     * @param nom   nom du magasin
     * @param image ivwImageProduit du magasin
     */
    public Magasin(String nom, Bitmap image) {
        id = -1;
        this.nom = nom;
        this.image = image;
    }

    /**
     * Constructeur qui prend en paramètre l'id, le nom et l'ivwImageProduit du magasin
     *
     * @param id    id du magasin
     * @param nom   nom du magasib
     * @param image ivwImageProduit du magasin
     */
    public Magasin(long id, String nom, Bitmap image) {
        this.id = id;
        this.nom = nom;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
