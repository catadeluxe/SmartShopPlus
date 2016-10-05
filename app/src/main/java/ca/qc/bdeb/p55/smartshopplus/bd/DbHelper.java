package ca.qc.bdeb.p55.smartshopplus.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ca.qc.bdeb.p55.smartshopplus.modele.Client;

/**
 * Created by Catalin on 2016-09-14.
 */
public class DbHelper extends SQLiteOpenHelper { // DO NOT USE CLASS Client,  CLASS COPIED FOR REFERENCE PURPOSES ONLY

    private static final String TAG = "MyActivity";

    private static final String DB_NAME = "app.db";
    public static final int DBVERSION = 1;
    private Context context;
    private static DbHelper instanceDbHelper = null;

    private static final String NOM_TABLE_CLIENTS = "clients";

    private static final String CLIENT_ID = "_id";
    private static final String CLIENT_NOM = "nom";
    private static final String CLIENT_AGE = "age";
    private static final String CLIENT_SEXE = "Sexe";
    private static final String CLIENT_ADRESSE = "adresse";
    private static final String CLIENT_VILLE = "ville";
    private static final String CLIENT_COURRIEL = "courriel";


    private ArrayList<Client> listClients = new ArrayList<Client>();

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

    @Override
    public void onCreate(SQLiteDatabase db) {
        listClients.add(new Client("Katelyn O'Dwyer", 23, Client.Sexe.FEMME,
                "10974 rue Tanguay", "Montréal",
                "katelyn@gmail.com"));
        listClients.add(new Client("Martin McFly", 28, Client.Sexe.HOMME,
                "32 rue des Champignons magiques", "Laval",
                "martinmcfly@gmail.com"));
        listClients.add(new Client("Dominick Tremblay", 26, Client.Sexe.AUTRE,
                "10974 avenue du Gazon", "Terrebonne",
                "dominicktremblay@gmail.com"));
        listClients.add(new Client("4444", 44, Client.Sexe.INCONNU,
                "44 44e rue", "Quarante-Quatre-Ville",
                "Quarante.4@Quarante4.com"));


        String sqlClient = "CREATE TABLE " + NOM_TABLE_CLIENTS + "(" + CLIENT_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + CLIENT_NOM +
                " TEXT," + CLIENT_AGE + " INTEGER," + CLIENT_SEXE + " TEXT," +
                CLIENT_ADRESSE + " TEXT," + CLIENT_VILLE + " TEXT," + CLIENT_COURRIEL + " TEXT)";
        db.execSQL(sqlClient);

        ContentValues values = new ContentValues();

        for (Client client : listClients) {
            values.put(CLIENT_NOM, client.getNom());
            values.put(CLIENT_AGE, client.getAge());
            values.put(CLIENT_SEXE, client.getSexe().getSexeInt());
            values.put(CLIENT_ADRESSE, client.getAdresse());
            values.put(CLIENT_VILLE, client.getVille());
            values.put(CLIENT_COURRIEL, client.getCourriel());

            long id = db.insert(NOM_TABLE_CLIENTS, null, values);

            client.setId(id);


        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Retourne le client dont l'id est passé par paramètre
     *
     * @param id l'id du client à retourner
     * @return le client dont l'id a été passé en paramètre
     */
    public Client getClient(long id) {
        SQLiteDatabase db = this.getReadableDatabase(); // On veut lire dans la BD

        Cursor cursor = db.query(NOM_TABLE_CLIENTS,
                new String[]{CLIENT_ID, CLIENT_NOM, CLIENT_AGE, CLIENT_SEXE, CLIENT_ADRESSE,
                        CLIENT_VILLE, CLIENT_COURRIEL},
                CLIENT_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        Client client = null;
        if (cursor != null) {
            cursor.moveToFirst();

            client = new Client(cursor.getLong(0), cursor.getString(1), cursor.getInt(2),
                    Client.Sexe.getSexeFromEntier(cursor.getInt(3)),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6));
        }


        cursor.close();
        db.close(); // Fermer la connexion

        // Retourner le client
        return client;
    }

    /**
     * Retourne la liste de tous les clients dans la base de données
     *
     * @return la liste de tous les clients
     */
    public List<Client> getListeClient() {

        List<Client> listeClients = new ArrayList<Client>();

        SQLiteDatabase data = this.getReadableDatabase();

        Client client;

        Cursor cursor = data.query(NOM_TABLE_CLIENTS,
                new String[]{CLIENT_ID, CLIENT_NOM, CLIENT_AGE, CLIENT_SEXE, CLIENT_ADRESSE,
                        CLIENT_VILLE, CLIENT_COURRIEL},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {

            do {
                client = new Client(cursor.getLong(0), cursor.getString(1), cursor.getInt(2),
                        Client.Sexe.getSexeFromEntier(cursor.getInt(3)), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6));

                listeClients.add(client);
            } while (cursor.moveToNext());

        }

        cursor.close();
        return listeClients;
    }

    /**
     * Supprime un client de la base de données
     *
     * @param client le client à supprimer
     */
    public void deleteClient(Client client) {
        SQLiteDatabase db = this.getWritableDatabase(); // On veut écrire dans la BD
        db.delete(NOM_TABLE_CLIENTS, CLIENT_ID + " = ?",
                new String[]{String.valueOf(client.getId())});
        db.close();
    }

    /**
     * Ajoute un client à la base de données.
     *
     * @param clientAAjouter Le client à ajouter.
     * @return L'id du client ajouté.
     */
    public long ajouterClient(Client clientAAjouter) {
        long idNouveauClient = -1;

        SQLiteDatabase db = this.getWritableDatabase(); // On veut écrire dans la BD
        ContentValues values = new ContentValues();
        values.put(CLIENT_NOM, clientAAjouter.getNom());
        values.put(CLIENT_AGE, clientAAjouter.getAge());
        values.put(CLIENT_SEXE, clientAAjouter.getSexe().getSexeInt());
        values.put(CLIENT_ADRESSE, clientAAjouter.getAdresse());
        values.put(CLIENT_VILLE, clientAAjouter.getVille());
        values.put(CLIENT_COURRIEL, clientAAjouter.getCourriel());

        idNouveauClient = db.insert(NOM_TABLE_CLIENTS, null, values);

        clientAAjouter.setId(idNouveauClient);

        db.close(); // Fermer la connexion

        return idNouveauClient;
    }


    /**
     * Met à jour un client dans la BD
     *
     * @param client le client à mettre à jour avec les nouvelles informations
     * @return true  si update réussi, sinon false;
     */
    public boolean updateClient(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CLIENT_NOM, client.getNom());
        values.put(CLIENT_AGE, client.getAge());
        values.put(CLIENT_SEXE, client.getSexe().getSexeInt());
        values.put(CLIENT_ADRESSE, client.getAdresse());
        values.put(CLIENT_VILLE, client.getVille());
        values.put(CLIENT_COURRIEL, client.getCourriel());

        int nbMAJ = db.update(NOM_TABLE_CLIENTS, values, CLIENT_ID + " = ?",
                new String[]{String.valueOf(client.getId())});

        return (nbMAJ > 0); // True si update réussi, sinon false
    }
}