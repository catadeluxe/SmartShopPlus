package ca.qc.bdeb.p55.smartshopplus.vue;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import ca.qc.bdeb.p55.smartshopplus.R;
import ca.qc.bdeb.p55.smartshopplus.bd.DbHelper;
import ca.qc.bdeb.p55.smartshopplus.modele.Produit;

public class ModifierProduitActivity extends AppCompatActivity {

    Long idProduit;
    Produit produit;

    Intent intent;
    DbHelper dbHelper;

    EditText edtNom;
    EditText edtQuantite;
    EditText edtTypeQuantite;
    EditText edtPrix;

    Bitmap imageProduit;
    ImageButton ibtnProduit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_produit);

        dbHelper = DbHelper.getInstance(getApplicationContext());
        intent = getIntent();
        idProduit = intent.getLongExtra(MagasinActivity.EXTRA_MESSAGE, -1);

        produit = dbHelper.getProduit(idProduit);

        // assigne les variables d'instance aux Views qui se retrouvent dans la vue
        assignerVariablesAuxViews();

        peuplerViewsAvecDonneesProduit();
        getSupportActionBar().setTitle(produit.getNom() + " - " + getResources().getString(R.string.app_name));
    }

    /**
     * Affiche et gonfle la barre de menu
     *
     * @param menu menu à gonfler
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_modifier_produit_activity, menu);
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
                if (verifierTousChampsBienRemplis()) {
                    boolean isUpdatedSuccessfully = dbHelper.updateProduit(creerProduitAvecDonneesView());
                    if (isUpdatedSuccessfully) {
                        Toast.makeText(ModifierProduitActivity.this,
                                getResources().getString(R.string.toast_changes_saved),
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                break;
        }
        return true;
    }

    /**
     * Assigne les Views de la vue (fichier XML) aux variables d'instance
     */
    private void assignerVariablesAuxViews() {
        edtNom = (EditText) findViewById(R.id.activity_modifier_produit_edt_nom_produit);
        edtQuantite = (EditText) findViewById(R.id.activity_modifier_produit_edt_quantite);
        edtTypeQuantite = (EditText) findViewById(R.id.activity_modifier_produit_edt_type_quantite);
        edtPrix = (EditText) findViewById(R.id.activity_modifier_produit_edt_prix);
        ibtnProduit = (ImageButton) findViewById(R.id.activity_modifier_produit_ibtn_image_produit);
    }

    /**
     * Remplit les Views de l'Activity avec les données du produit en cours à modifier
     */
    private void peuplerViewsAvecDonneesProduit() {
        edtNom.setText(produit.getNom());
        edtQuantite.setText(String.valueOf(produit.getQuantite()));
        edtTypeQuantite.setText(produit.getTypeQuantite());
        edtPrix.setText(String.valueOf(produit.getPrix()));
        ibtnProduit.setImageBitmap(produit.getImage());
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

    /**
     * Prend les données entrés dans la vue, crée et un objet Produit avec.
     * Ne fait pas de validation
     *
     * @return l'objet Produit crée
     */
    private Produit creerProduitAvecDonneesView() {
        Produit produitCree = new Produit();

        produitCree.setId(this.produit.getId());
        produitCree.setIdMagasinFk(this.produit.getIdMagasinFk());
        produitCree.setNom(edtNom.getText().toString().trim());
        produitCree.setQuantite(Double.parseDouble(edtQuantite.getText().toString().trim()));
        produitCree.setTypeQuantite(edtTypeQuantite.getText().toString().trim());
        produitCree.setPrix(Double.parseDouble(edtPrix.getText().toString().trim()));

        // TODO imageProduit
        produitCree.setImage(this.produit.getImage());


        return produitCree;
    }
}
