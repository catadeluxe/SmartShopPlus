package ca.qc.bdeb.p55.smartshopplus.vue;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import ca.qc.bdeb.p55.smartshopplus.R;
import ca.qc.bdeb.p55.smartshopplus.bd.DbHelper;
import ca.qc.bdeb.p55.smartshopplus.modele.Magasin;

public class AjouterMagasinActivity extends AppCompatActivity {

    DbHelper dbHelper;

    EditText edtNomMagasin;
    ImageButton iBtnImageMagasin;

    Bitmap imageMagasin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_magasin);

        dbHelper = DbHelper.getInstance(getApplicationContext());

        getSupportActionBar().setTitle(getResources().getString(R.string.title_add_store)
                + " - " + getResources().getString(R.string.app_name));

        // assigne les variables d'instance aux Views qui se retrouvent dans la vue
        assignerVariablesAuxViews();

        imageMagasin = BitmapFactory.decodeResource(getResources(), R.drawable.ic_store_black_48dp);
        iBtnImageMagasin.setImageBitmap(imageMagasin);
    }

    /**
     * Affiche et gonfle la barre de menu
     *
     * @param menu menu à gonfler
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ajouter_magasin_activity, menu);
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
                    dbHelper.ajouterMagasin(creerMagasinAvecDonneesView());
                    Toast.makeText(AjouterMagasinActivity.this,
                            getResources().getString(R.string.toast_store_successfully_added),
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
        return true;
    }

    /**
     * Assigne les Views de la vue (fichier XML) aux variables d'instance
     */
    private void assignerVariablesAuxViews() {
        edtNomMagasin = (EditText) findViewById(R.id.activity_ajouter_magasin_edt_nom_magasin);
        iBtnImageMagasin = (ImageButton) findViewById(R.id.activity_ajouter_magasin_ibtn_image_magasin);
    }

    /**
     * Vérifie que tous les EditViews ont été remplis adéquatement par l'utilisateur. Indique
     * les endroits où ils n'ont pas été bien remplis, si c'est le cas.
     *
     * @return true si tous les EditViews ont été adéquatement complétés, sinon false.
     */
    private boolean verifierTousChampsBienRemplis() {
        boolean tousChampsValides = true;

        if (edtNomMagasin.getText().toString().trim().isEmpty()) {
            edtNomMagasin.setError(getResources().getString(R.string.err_field_cannot_be_blank));
            tousChampsValides = false;
        } else {
            tousChampsValides = true;
        }

        return tousChampsValides;
    }

    /**
     * Prend les données dans la vue et retourne un Magasin avec ces données. Méthode doit être
     * appellée après les validations.
     *
     * @return Le Magasin crée
     */
    private Magasin creerMagasinAvecDonneesView() {
        Magasin magasinCree = new Magasin();

        magasinCree.setNom(edtNomMagasin.getText().toString().trim());
        magasinCree.setImage(imageMagasin);

        return magasinCree;
    }

}
