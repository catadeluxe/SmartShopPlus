package ca.qc.bdeb.p55.smartshopplus.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import ca.qc.bdeb.p55.smartshopplus.R;
import ca.qc.bdeb.p55.smartshopplus.modele.Magasin;
import ca.qc.bdeb.p55.smartshopplus.modele.Produit;

/**
 * Created by Catalin on 2016-09-14.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "MyActivity";

    private static final String DB_NAME = "app.db";
    public static final int DBVERSION = 1;
    private Context context;
    private static DbHelper instanceDbHelper = null;

    private static final String NOM_TABLE_MAGASIN = "magasin";
    private static final String NOM_TABLE_PRODUIT = "produit";

    private static final String MAGASIN_ID = "_id";
    private static final String MAGASIN_NOM = "nom";
    private static final String MAGASIN_IMAGE = "image";

    private static final String PRODUIT_ID = "_id";
    private static final String PRODUIT_ID_MAGASIN_FK = "id_magasin_fk";
    private static final String PRODUIT_NOM = "nom";
    private static final String PRODUIT_QUANTITE = "qualtite";
    private static final String PRODUIT_TYPE_QUANTITE = "type_quantite";
    private static final String PRODUIT_PRIX = "prix";
    private static final String PRODUIT_IMAGE = "image";


    private ArrayList<Magasin> listeMagasins = new ArrayList<Magasin>();
    private ArrayList<Produit> listeProduits = new ArrayList<Produit>();


    public static DbHelper getInstance(Context context) {
        if (instanceDbHelper == null) {
            instanceDbHelper = new DbHelper(context.getApplicationContext());
        }
        return instanceDbHelper;
    }

    private DbHelper(Context context) {
        super(context, DB_NAME, null, DBVERSION);
        this.context = context;
    }

    /**
     * Création table bases de données
     *
     * @param db la base de données
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Bitmap imageMagasin = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_store_black_48dp);

        Bitmap imageProduit = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

        // Ajout magasins par défaut
        listeMagasins.add(new Magasin("Super C", imageMagasin));
        listeMagasins.add(new Magasin("Dollarama", imageMagasin));
        listeMagasins.add(new Magasin("Costco", imageMagasin));
        listeMagasins.add(new Magasin("Marchés TAU", imageMagasin));
        listeMagasins.add(new Magasin("Marché Jean-Talon", imageMagasin));
        listeMagasins.add(new Magasin("Sami Fruits", imageMagasin));
        listeMagasins.add(new Magasin("Tim Hortons", imageMagasin));
        listeMagasins.add(new Magasin("Bombardier", imageMagasin));

        // Ajout produits par défaut
        listeProduits.add(new Produit(1, "Coca-Cola", 2000, "ml", 1.99, imageProduit));


        // Création Strings commandes SQL à exécuter
        String commandeSqlPermettreClesEtrangeres = "PRAGMA foreign_keys = ON;";

        String commandeSqlCreationTableMagasin =
                "CREATE TABLE " + NOM_TABLE_MAGASIN +
                        "(" + MAGASIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        MAGASIN_NOM + " TEXT," +
                        MAGASIN_IMAGE + " BLOB)";

        String commandeSqlCreationTableProduit =
                "CREATE TABLE " + NOM_TABLE_PRODUIT +
                        "(" + PRODUIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        PRODUIT_ID_MAGASIN_FK + " INTEGER," +
                        PRODUIT_NOM + " TEXT," +
                        PRODUIT_QUANTITE + " REAL," +
                        PRODUIT_TYPE_QUANTITE + " TEXT," +
                        PRODUIT_PRIX + " REAL," +
                        PRODUIT_IMAGE + " BLOB," +
                        "FOREIGN KEY (" + PRODUIT_ID_MAGASIN_FK +
                        ") REFERENCES " + NOM_TABLE_MAGASIN + "(" + MAGASIN_ID + "))";

        // Exécution commandes SQL
        db.execSQL(commandeSqlPermettreClesEtrangeres);
        db.execSQL(commandeSqlCreationTableMagasin);
        db.execSQL(commandeSqlCreationTableProduit);

        ContentValues values = new ContentValues();

        for (Magasin magasin : listeMagasins) {
            values.put(MAGASIN_NOM, magasin.getNom());
            values.put(MAGASIN_IMAGE, DbBitmapUtility.getBytes(magasin.getImage()));

            long id = db.insert(NOM_TABLE_MAGASIN, null, values);

            magasin.setId(id);
        }

        values = new ContentValues();

        for (Produit prod : listeProduits) {
            values.put(PRODUIT_ID_MAGASIN_FK, prod.getIdMagasinFk());
            values.put(PRODUIT_NOM, prod.getNom());
            values.put(PRODUIT_QUANTITE, prod.getQuantite());
            values.put(PRODUIT_TYPE_QUANTITE, prod.getTypeQuantite());
            values.put(PRODUIT_PRIX, prod.getPrix());
            values.put(PRODUIT_IMAGE, DbBitmapUtility.getBytes(prod.getImage()));

            long id = db.insert(NOM_TABLE_PRODUIT, null, values);

            prod.setId(id);

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Retourne le magasin dont l'id est passé par paramètre
     *
     * @param id l'id du magasin à retourner
     * @return le magasin dont l'id a été passé en paramètre
     */
    public Magasin getMagasin(long id) {
        SQLiteDatabase db = this.getReadableDatabase(); // On veut lire dans la BD

        Cursor cursor = db.query(NOM_TABLE_MAGASIN,
                new String[]{MAGASIN_ID, MAGASIN_NOM, MAGASIN_IMAGE},
                MAGASIN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        Magasin magasin = null;
        if (cursor != null) {
            cursor.moveToFirst();

            magasin = new Magasin(cursor.getLong(0), cursor.getString(1),
                    DbBitmapUtility.getImage(cursor.getBlob(2)));
        }


        cursor.close();
        db.close(); // Fermer la connexion

        // Retourner le magasin
        return magasin;
    }

    /**
     * Retourne la liste de tous les clients dans la base de données
     *
     * @return la liste de tous les clients
     */
    public List<Magasin> getListeMagasins() {

        List<Magasin> listeMagasins = new ArrayList<Magasin>();

        SQLiteDatabase data = this.getReadableDatabase();

        Magasin magasin;

        Cursor cursor = data.query(NOM_TABLE_MAGASIN,
                new String[]{MAGASIN_ID, MAGASIN_NOM, MAGASIN_IMAGE},
                null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    magasin = new Magasin(cursor.getLong(0), cursor.getString(1),
                            DbBitmapUtility.getImage(cursor.getBlob(2)));

                    listeMagasins.add(magasin);
                } while (cursor.moveToNext());

            }
        }
        cursor.close();
        return listeMagasins;
    }

    /**
     * Supprime un magasin de la base de données
     *
     * @param magasin le magasin à supprimer
     */
    public void deleteMagasin(Magasin magasin) {
        SQLiteDatabase db = this.getWritableDatabase(); // On veut écrire dans la BD
        db.delete(NOM_TABLE_MAGASIN, MAGASIN_ID + " = ?",
                new String[]{String.valueOf(magasin.getId())});
        db.close();
    }

    /**
     * Ajoute un magasin à la base de données.
     *
     * @param magasinAAjouter Le client à ajouter.
     * @return L'id du client ajouté.
     */
    public long ajouterMagasin(Magasin magasinAAjouter) {
        long idNouveauMagasin = -1;

        SQLiteDatabase db = this.getWritableDatabase(); // On veut écrire dans la BD
        ContentValues values = new ContentValues();
        values.put(MAGASIN_NOM, magasinAAjouter.getNom());
        values.put(MAGASIN_IMAGE, DbBitmapUtility.getBytes(magasinAAjouter.getImage()));

        idNouveauMagasin = db.insert(NOM_TABLE_MAGASIN, null, values);

        magasinAAjouter.setId(idNouveauMagasin);

        db.close(); // Fermer la connexion

        return idNouveauMagasin;
    }

    /**
     * Met à jour un magasin dans la BD
     *
     * @param magasin le magasin à mettre à jour avec les nouvelles informations
     * @return true  si update réussi, sinon false;
     */
    public boolean updateMagasin(Magasin magasin) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MAGASIN_NOM, magasin.getNom());
        values.put(MAGASIN_IMAGE, DbBitmapUtility.getBytes(magasin.getImage()));

        int nbMAJ = db.update(NOM_TABLE_MAGASIN, values, MAGASIN_ID + " = ?",
                new String[]{String.valueOf(magasin.getId())});

        return (nbMAJ > 0); // True si update réussi, sinon false
    }


    //


    // MAGASIN EN HAUT


    //

    // PRODUIT EN BAS


    //

    /**
     * Retourne le produit dont l'id est passé par paramètre
     *
     * @param id l'id du produit à retourner
     * @return le produit dont l'id a été passé en paramètre
     */
    public Produit getProduit(long id) {
        SQLiteDatabase db = this.getReadableDatabase(); // On veut lire dans la BD

        Cursor cursor = db.query(NOM_TABLE_PRODUIT,
                new String[]{PRODUIT_ID, PRODUIT_ID_MAGASIN_FK, PRODUIT_NOM, PRODUIT_QUANTITE,
                        PRODUIT_TYPE_QUANTITE, PRODUIT_PRIX, PRODUIT_IMAGE},
                PRODUIT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        Produit produit = null;
        if (cursor != null) {
            cursor.moveToFirst();

            produit = new Produit(cursor.getLong(0), cursor.getLong(1),
                    cursor.getString(2), cursor.getLong(3), cursor.getString(4), cursor.getLong(5),
                    DbBitmapUtility.getImage(cursor.getBlob(6)));
        }


        cursor.close();
        db.close(); // Fermer la connexion

        // Retourner le produit
        return produit;
    }

    /**
     * Retourne la liste de tous les produits du magasin dont l'id a été passé en paramètre
     *
     * @return la liste de tous les produits du magasin dont l'id a été passé en paramètre
     */
    public List<Produit> getListeProduitsMagasin(long id_magasin) {

        List<Produit> listeProduits = new ArrayList<Produit>();

        SQLiteDatabase data = this.getReadableDatabase();

        Produit produit;

        Cursor cursor = data.query(NOM_TABLE_PRODUIT,
                new String[]{
                        PRODUIT_ID,
                        PRODUIT_ID_MAGASIN_FK,
                        PRODUIT_NOM,
                        PRODUIT_QUANTITE,
                        PRODUIT_TYPE_QUANTITE,
                        PRODUIT_PRIX,
                        PRODUIT_IMAGE},
                PRODUIT_ID_MAGASIN_FK + "=?",
                new String[]{String.valueOf(id_magasin)}, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    produit = new Produit(
                            cursor.getLong(0),
                            cursor.getLong(1),
                            cursor.getString(2),
                            cursor.getDouble(3),
                            cursor.getString(4),
                            cursor.getDouble(5),
                            DbBitmapUtility.getImage(cursor.getBlob(6)));

                    listeProduits.add(produit);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return listeProduits;
    }

    /**
     * Supprime un produit de la base de données
     *
     * @param produit le produit à supprimer
     */
    public void deleteProduit(Produit produit) {
        SQLiteDatabase db = this.getWritableDatabase(); // On veut écrire dans la BD
        db.delete(NOM_TABLE_PRODUIT, PRODUIT_ID + " = ?",
                new String[]{String.valueOf(produit.getId())});
        db.close();
    }
    
    /**
     * Ajoute un produit à la base de données.
     *
     * @param nouveauProduit Le produit à ajouter.
     * @return L'id du produit ajouté.
     */
    public long ajouterProduit(Produit nouveauProduit) {
        long idNouveauProduit = -1;

        SQLiteDatabase db = this.getWritableDatabase(); // On veut écrire dans la BD
        ContentValues values = new ContentValues();
        values.put(PRODUIT_ID_MAGASIN_FK, nouveauProduit.getIdMagasinFk());
        values.put(PRODUIT_NOM, nouveauProduit.getNom());
        values.put(PRODUIT_QUANTITE, nouveauProduit.getQuantite());
        values.put(PRODUIT_TYPE_QUANTITE, nouveauProduit.getTypeQuantite());
        values.put(PRODUIT_PRIX, nouveauProduit.getPrix());
        values.put(PRODUIT_IMAGE, DbBitmapUtility.getBytes(nouveauProduit.getImage()));

        idNouveauProduit = db.insert(NOM_TABLE_MAGASIN, null, values);

        nouveauProduit.setId(idNouveauProduit);

        db.close(); // Fermer la connexion

        return idNouveauProduit;
    }

    /**
     * Met à jour un produit dans la BD
     *
     * @param nouveauProduit le produit à mettre à jour avec les nouvelles informations
     * @return true  si update réussi, sinon false;
     */
    public boolean updateProduit(Produit nouveauProduit) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PRODUIT_ID_MAGASIN_FK, nouveauProduit.getIdMagasinFk());
        values.put(PRODUIT_NOM, nouveauProduit.getNom());
        values.put(PRODUIT_QUANTITE, nouveauProduit.getQuantite());
        values.put(PRODUIT_TYPE_QUANTITE, nouveauProduit.getTypeQuantite());
        values.put(PRODUIT_PRIX, nouveauProduit.getPrix());
        values.put(PRODUIT_IMAGE, DbBitmapUtility.getBytes(nouveauProduit.getImage()));

        int nbMAJ = db.update(NOM_TABLE_MAGASIN, values, MAGASIN_ID + " = ?",
                new String[]{String.valueOf(nouveauProduit.getId())});

        return (nbMAJ > 0); // True si update réussi, sinon false
    }
}