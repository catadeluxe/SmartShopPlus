package ca.qc.bdeb.p55.smartshopplus.vue;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

        arrayAdapterProduits = new ArrayAdapterProduits(this, R.layout.relative_layout_liste_produits, listeProduits);

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
            case R.id.mnu_supprimer:
                affichierFenetreConfirmationSuppression();
                break;
        }
        return true;
    }

    /**
     * Affiche fenêtre confirmatiom suppression magasin
     */
    void affichierFenetreConfirmationSuppression() {
        new AlertDialog.Builder(this)
                .setTitle("Title")
                .setMessage("Do you really want to whatever?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(MagasinActivity.this, "Yaay", Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(MagasinActivity.this, "Nope", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
    }


}
