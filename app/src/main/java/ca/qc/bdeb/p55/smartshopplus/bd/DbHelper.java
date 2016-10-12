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


    private ArrayList<Magasin> listeMagasins = new ArrayList<Magasin>();
    // private ArrayList<Produit> listeProduits = new ArrayList<Produit>();


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
        Bitmap imageMagasin = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_store_black_48dp);

        listeMagasins.add(new Magasin("Super C", imageMagasin));
        listeMagasins.add(new Magasin("Dollarama", imageMagasin));
        listeMagasins.add(new Magasin("Costco", imageMagasin));
        listeMagasins.add(new Magasin("Marchés TAU", imageMagasin));
        listeMagasins.add(new Magasin("Marché Jean-Talon", imageMagasin));
        listeMagasins.add(new Magasin("Tim Hortons", imageMagasin));
        listeMagasins.add(new Magasin("Bombardier", imageMagasin));

        // Création Strings commandes SQL à exécuter
        String commandeSqlPermettreClesEtrangeres = "PRAGMA foreign_keys = 1;";

        String commandeSqlCreationTableMagasin =
                "CREATE TABLE " + NOM_TABLE_MAGASIN +
                        "(" + MAGASIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        MAGASIN_NOM + " TEXT," +
                        MAGASIN_IMAGE + " BLOB);";

        String commandeSqlCreationTableProduit =
                "CREATE TABLE " + NOM_TABLE_PRODUIT +
                        "(" + PRODUIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        PRODUIT_ID_MAGASIN_FK + " INTEGER," +
                        PRODUIT_NOM + " TEXT," +
                        PRODUIT_QUANTITE + " REAL," +
                        PRODUIT_TYPE_QUANTITE + " TEXT," +
                        PRODUIT_PRIX + " REAL);";

        // Exécution commandes SQL
        db.execSQL(commandeSqlPermettreClesEtrangeres);
        db.execSQL(commandeSqlCreationTableMagasin);
        db.execSQL(commandeSqlCreationTableProduit);

        ContentValues values = new ContentValues();

        for (Magasin magasin : listeMagasins) {
            values.put(MAGASIN_NOM, magasin.getNom());
            values.put(MAGASIN_IMAGE, DbBitmapUtility.getBytes(magasin.getImage()));

            long id = db.insert(NOM_TABLE_MAGASIN, null, values);

            // TODO effacer cette ligne pour la remise, elle est inutile, sauf pour des raisons de déboggage
            magasin.setId(id);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Retourne le client dont l'id est passé par paramètre
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
     * Ajoute un client à la base de données.
     *
     * @param magasinAAjouter Le client à ajouter.
     * @return L'id du client ajouté.
     */
    public long ajouterClient(Magasin magasinAAjouter) {
        long idNouveauClient = -1;

        SQLiteDatabase db = this.getWritableDatabase(); // On veut écrire dans la BD
        ContentValues values = new ContentValues();
        values.put(MAGASIN_NOM, magasinAAjouter.getNom());
        values.put(MAGASIN_IMAGE, DbBitmapUtility.getBytes(magasinAAjouter.getImage()));

        idNouveauClient = db.insert(NOM_TABLE_MAGASIN, null, values);

        magasinAAjouter.setId(idNouveauClient);

        db.close(); // Fermer la connexion

        return idNouveauClient;
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
}