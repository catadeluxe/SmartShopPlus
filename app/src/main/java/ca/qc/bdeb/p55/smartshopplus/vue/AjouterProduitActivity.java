package ca.qc.bdeb.p55.smartshopplus.vue;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ca.qc.bdeb.p55.smartshopplus.R;
import ca.qc.bdeb.p55.smartshopplus.bd.DbHelper;
import ca.qc.bdeb.p55.smartshopplus.modele.Produit;

public class AjouterProduitActivity extends AppCompatActivity {

    public final static int IMAGE_PICK = 11;

    Long idMag;
    Intent intent;

    DbHelper dbHelper;

    EditText edtNom;
    EditText edtQuantite;
    EditText edtTypeQuantite;
    EditText edtPrix;

    RatingBar ratingBar;

    Bitmap imageProduit;
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

        imageProduit = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        ibtnProduit.setImageBitmap(imageProduit);

        getSupportActionBar().setTitle(getResources().getString(R.string.title_add_product)
                + " - " + getResources().getString(R.string.app_name));

        ibtnProduit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent,
                        getResources().getString(R.string.intent_gallery_picker));

                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, IMAGE_PICK);

            }
        });
    }

    /**
     * When a result is returned from another Activity onActivityResult is called.
     * <p>
     * doc https://developer.android.com/reference/android/app/Activity.html#onActivityResult(int,%20int,%20android.content.Intent)
     * <p>
     * Called when an activity you launched exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it. The resultCode will be
     * RESULT_CANCELED if the activity explicitly returned that, didn't return any result, or
     * crashed during its operation.
     * <p>
     * You will receive this call immediately before onResume() when your activity is re-starting.
     * <p>
     * This method is never invoked if your activity sets noHistory to true.
     *
     * @param requestCode int: The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this result came
     *                    from.
     * @param resultCode  int: The integer result code returned by the child activity through its
     *                    setResult().
     * @param data        Intent: An Intent, which can return result data to the caller (various
     *                    data can be attached to Intent "extras").
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // First we need to check if the requestCode matches the one we used.
        if (requestCode == IMAGE_PICK) {

            // The resultCode is set by the DetailActivity
            // By convention RESULT_OK means that what ever
            // DetailActivity did was successful
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    // Get the result from the returned Intent
                    try {
                        InputStream inputStream = this.getContentResolver().openInputStream(data.getData());

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 4;
                        imageProduit = BitmapFactory.decodeStream(inputStream, null, options);

                        this.ibtnProduit.setImageBitmap(imageProduit);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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
                if (verifierTousChampsBienRemplis()) {
                    dbHelper.ajouterProduit(creerProduitAvecDonneesView());
                    Toast.makeText(AjouterProduitActivity.this,
                            getResources().getString(R.string.toast_product_successfully_added),
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
        edtNom = (EditText) findViewById(R.id.activity_aouter_produit_edt_nom_produit);
        edtQuantite = (EditText) findViewById(R.id.activity_aouter_produit_edt_quantite);
        edtTypeQuantite = (EditText) findViewById(R.id.activity_aouter_produit_edt_type_quantite);
        edtPrix = (EditText) findViewById(R.id.activity_aouter_produit_edt_prix);
        ratingBar = (RatingBar) findViewById((R.id.activity_ajouter_produit_rbar_qualite));
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

    /**
     * Prend les données entrés dans la vue, crée et un objet Produit avec.
     * Ne fait pas de validation
     *
     * @return l'objet Produit crée
     */
    private Produit creerProduitAvecDonneesView() {
        Produit produit = new Produit();

        produit.setIdMagasinFk(idMag);
        produit.setNom(edtNom.getText().toString().trim());
        produit.setQuantite(Double.parseDouble(edtQuantite.getText().toString().trim()));
        produit.setTypeQuantite(edtTypeQuantite.getText().toString().trim());
        produit.setPrix(Double.parseDouble(edtPrix.getText().toString().trim()));
        produit.setQualite(ratingBar.getRating());
        produit.setImage(imageProduit);

        return produit;
    }
}
