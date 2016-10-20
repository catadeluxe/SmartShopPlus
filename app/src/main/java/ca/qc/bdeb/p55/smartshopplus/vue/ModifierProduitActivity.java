package ca.qc.bdeb.p55.smartshopplus.vue;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;

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

        getSupportActionBar().setTitle(produit.getNom() + " - " + getResources().getString(R.string.app_name));
    }

    /**
     * Assigne les Views de la vue (fichier XML) aux variables d'instance
     */
    private void assignerVariablesAuxViews() {
        edtNom = (EditText) findViewById(R.id.activity_modifier_produit_edt_nom_produit);
        edtQuantite = (EditText) findViewById(R.id.activity_modifier_produit_edt_quantite);
        edtTypeQuantite = (EditText) findViewById(R.id.activity_modifier_produit_edt_type_quantite);
        edtPrix = (EditText) findViewById(R.id.activity_modifier_produit_edt_prix);
        ibtnProduit = (ImageButton) findViewById(R.id.activity_ajouter_produit_ibtn_image_produit);
    }

    private void peuplerViewsAvecDonneesProduit() {
        edtNom.setText(produit.getNom());
        edtQuantite.setText(String.valueOf(produit.getQuantite()));
        edtTypeQuantite.setText(produit.getTypeQuantite());
        edtPrix.setText(String.valueOf(produit.getPrix()));
        // TODO Image produit
    }
}
