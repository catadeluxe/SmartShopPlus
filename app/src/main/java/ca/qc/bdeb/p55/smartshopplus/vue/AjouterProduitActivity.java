package ca.qc.bdeb.p55.smartshopplus.vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import ca.qc.bdeb.p55.smartshopplus.R;
import ca.qc.bdeb.p55.smartshopplus.bd.DbHelper;

public class AjouterProduitActivity extends AppCompatActivity {


    Long idMag;
    Intent intent;

    DbHelper dbHelper;

    EditText edtNom;
    EditText edtQuantite;
    EditText edtTypeQuantite;
    EditText edtPrix;

    ImageButton ibtnProduit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_produit);

        // Effectue connexion vers la base de données
        dbHelper = DbHelper.getInstance(getApplicationContext());

        // Récupère l'id du magasin passé par l'activity qui lui envoie
        intent = getIntent();
        idMag = intent.getLongExtra(MagasinActivity.EXTRA_MESSAGE, -1);

        // assigne les variables d'instance aux Views qui se retrouvent dans la vue
        assignerVariablesAuxViews();

        getSupportActionBar().setTitle(getResources().getString(R.string.title_add_product)
                + " - " + getResources().getString(R.string.app_name));
    }

    /**
     * Affiche et gonfle la barre de menu
     *
     * @param menu menu à gonfler
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ajouter_produit_activity, menu);
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
            case R.id.mnu_valider:
// TODO
                break;

        }
        return true;
    }

    /**
     * Assigne les Views de la vue (fichier XML) aux variables d'instance
     */
    private void assignerVariablesAuxViews() {
        edtNom = (EditText) findViewById(R.id.activity_aouter_produit_edt_nom_produit);
        edtQuantite = (EditText) findViewById(R.id.activity_aouter_produit_edt_quantite);
        edtTypeQuantite = (EditText) findViewById(R.id.activity_aouter_produit_edt_type_quantite);
        edtPrix = (EditText) findViewById(R.id.activity_aouter_produit_edt_prix);
        ibtnProduit = (ImageButton) findViewById(R.id.activity_ajouter_produit_ibtn_image_produit);
    }

    /**
     * Vérifie que tous les EditViews ont été remplis adéquatement par l'utilisateur. Indique
     * les endroits où ils n'ont pas été bien remplis, si c'est le cas.
     *
     * @return true si tous les EditViews ont été adéquatement complétés, sinon false.
     */
    private boolean verifierTousChampsBienRemplis() {
        boolean tousChampsValides = true;

        if (edtNom.getText().toString().trim().isEmpty()) {
            edtNom.setError(getResources().getString(R.string.err_field_cannot_be_blank));
            tousChampsValides = false;
        } else {
            tousChampsValides = true;
        }

        if (edtQuantite.getText().toString().trim().isEmpty()) {
            edtQuantite.setError(getResources().getString(R.string.err_field_cannot_be_blank));
            tousChampsValides = false;
        } else {
            tousChampsValides = true;
        }

        if (edtTypeQuantite.getText().toString().trim().isEmpty()) {
            edtTypeQuantite.setError(getResources().getString(R.string.err_field_cannot_be_blank));
            tousChampsValides = false;
        } else {
            tousChampsValides = true;
        }

        if (edtPrix.getText().toString().trim().isEmpty()) {
            edtPrix.setError(getResources().getString(R.string.err_field_cannot_be_blank));
            tousChampsValides = false;
        } else {
            tousChampsValides = true;
        }

        return tousChampsValides;
    }

}
