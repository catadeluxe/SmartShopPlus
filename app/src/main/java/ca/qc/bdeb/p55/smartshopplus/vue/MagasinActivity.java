package ca.qc.bdeb.p55.smartshopplus.vue;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import ca.qc.bdeb.p55.smartshopplus.R;
import ca.qc.bdeb.p55.smartshopplus.bd.DbHelper;
import ca.qc.bdeb.p55.smartshopplus.modele.ArrayAdapterProduits;
import ca.qc.bdeb.p55.smartshopplus.modele.Magasin;
import ca.qc.bdeb.p55.smartshopplus.modele.Produit;

public class MagasinActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "1";

    Magasin magasin;
    Long idMag;
    Intent intent;

    DbHelper dbHelper;

    ArrayAdapterProduits arrayAdapterProduits;
    ListView lvwProduits;
    List<Produit> listeProduits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magasin);

        // Effectue connexion vers la base de données
        dbHelper = DbHelper.getInstance(getApplicationContext());

        // Récupère l'id du magasin passé par l'activity qui lui envoie
        intent = getIntent();
        idMag = intent.getLongExtra(MainActivity.EXTRA_MESSAGE, -1);

        magasin = dbHelper.getMagasin(idMag);
        getSupportActionBar().setTitle(magasin.getNom() + " - " + getResources().getString(R.string.app_name));

        listeProduits = dbHelper.getListeProduitsMagasin(magasin.getId());
        lvwProduits = (ListView) findViewById(R.id.activity_magasin_lvw_produits);

        arrayAdapterProduits = new ArrayAdapterProduits(this,
                R.layout.relative_layout_liste_produits, listeProduits);

        lvwProduits.setAdapter(arrayAdapterProduits);

    }


    /**
     * Affiche et gonfle la barre de menu
     *
     * @param menu menu à gonfler
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_magasin_activity, menu);
        return true;
    }

    /**
     * Actions à faire lorsque l'utilisateur appuie sur les items du menu
     *
     * @param item l'item cliqué
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_ajouter:
                Intent intent = new Intent(this, AjouterProduitActivity.class);
                intent.putExtra(EXTRA_MESSAGE, magasin.getId());
                startActivity(intent);
                break;
            case R.id.mnu_supprimer:
                affichierfenetreconfirmationsuppressionMagasin();
                break;
            case R.id.mnu_supprimer_tous_produits:
                afficherFenetreSuppressionTousProduits();
                break;
        }
        return true;
    }

    /**
     * Affiche fenêtre confirmatiom suppression magasin
     */
    private void affichierfenetreconfirmationsuppressionMagasin() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.dialog_title_erase_store))
                .setMessage(getResources().getString(R.string.dialog_message_erase_store))
                .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_warning_black_48dp))
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                supprimerMagasin();
                            }
                        })
                .setNegativeButton(android.R.string.no, null).show();
    }

    /**
     * Supprime le magasin en cours selon les étapes suivantes:
     * 1 - Supprime tous les produits associés avec le magasin de la base de données.
     * 2 - Efface le magasin en cours de la base de données.
     * 3 - Affiche message de succès de suppression de magasin.
     * 4 - Termine l'activity
     */
    private void supprimerMagasin() {
        dbHelper.supprimerTousProduitsMagasin(idMag);
        dbHelper.deleteMagasin(magasin);
        Toast.makeText(MagasinActivity.this,
                getResources().getString(R.string.toast_store_successfully_erased),
                Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * Affiche fenêtre confirmation suppression tous les prodiuts du magasin
     */
    private void afficherFenetreSuppressionTousProduits() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.dialog_title_erase_all_products))
                .setMessage(getResources().getString(R.string.dialog_message_erase_all_products))
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                supprimerTousProduitsAvecMessage();
                            }
                        })
                .setNegativeButton(android.R.string.no, null).show();
    }

    /**
     * Supprime tous les produits du magasin en cours de la base de données selon les étapes
     * suivantes:
     * 1 - Supprime tous les produits associés avec le magasin de la base de données
     * 2 - Réinitialise l'activity pour que la vue corresponde aux données de la base de données
     * 3 - Affiche message confirmation de succès de suppression de tous les produits
     */
    private void supprimerTousProduitsAvecMessage() {
        dbHelper.supprimerTousProduitsMagasin(idMag);
        reinitialiserActivity();
        Toast.makeText(MagasinActivity.this,
                getResources().getString(R.string.toast_all_products_successfully_erased),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Réinitialise les views dans l'activity.
     * But: Que la vue corresponde aux données dans la base de données
     */
    private void reinitialiserActivity() {

        listeProduits = dbHelper.getListeProduitsMagasin(magasin.getId());

        arrayAdapterProduits = new ArrayAdapterProduits(this,
                R.layout.relative_layout_liste_produits, listeProduits);

        lvwProduits.setAdapter(arrayAdapterProduits);
    }

}
