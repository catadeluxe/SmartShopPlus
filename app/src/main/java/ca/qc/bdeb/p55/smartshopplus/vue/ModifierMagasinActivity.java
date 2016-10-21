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
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ca.qc.bdeb.p55.smartshopplus.R;
import ca.qc.bdeb.p55.smartshopplus.bd.DbBitmapUtility;
import ca.qc.bdeb.p55.smartshopplus.bd.DbHelper;
import ca.qc.bdeb.p55.smartshopplus.modele.Magasin;

public class ModifierMagasinActivity extends AppCompatActivity {

    public final static int IMAGE_PICK = 7;
    private final static String IMAGE_IBTN = "ImageIbtn";

    Magasin magasin;
    Long idMag;
    Intent intent;

    DbHelper dbHelper;

    EditText edtNomMagasin;
    ImageButton iBtnImageMagasin;

    Bitmap imageMagasin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_magasin);

        dbHelper = DbHelper.getInstance(getApplicationContext());
        intent = getIntent();
        idMag = intent.getLongExtra(MagasinActivity.EXTRA_MESSAGE, -1);

        magasin = dbHelper.getMagasin(idMag);

        getSupportActionBar().setTitle(magasin.getNom() + " - " + getResources().getString(R.string.app_name));


        // assigne les variables d'instance aux Views qui se retrouvent dans la vue
        assignerVariablesAuxViews();

        imageMagasin = magasin.getImage();
        peuplerViewsAvecDonneesProduit();

        iBtnImageMagasin.setOnClickListener(new View.OnClickListener() {

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
                        imageMagasin = BitmapFactory.decodeStream(inputStream, null, options);

                        this.magasin.setImage(imageMagasin);
                        this.iBtnImageMagasin.setImageBitmap(imageMagasin);

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
        getMenuInflater().inflate(R.menu.menu_modifier_magasin_activity, menu);
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
                    dbHelper.updateMagasin(creerMagasinAvecDonneesView());
                    Toast.makeText(ModifierMagasinActivity.this,
                            getResources().getString(R.string.toast_changes_applied),
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
        return true;
    }


    /**
     * Enregistre l'état de l'activity avat la rotation de l'écran.
     *
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putByteArray(IMAGE_IBTN, DbBitmapUtility.getBytes(imageMagasin));

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Rétablit l'état de l'activity après la rotation de l'écran pour qu'il corresponde à l'état
     * que l'activity avait avant la rotation
     *
     * @param savedInstanceState
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        imageMagasin = DbBitmapUtility.getImage(savedInstanceState.getByteArray(IMAGE_IBTN));
        iBtnImageMagasin.setImageBitmap(imageMagasin);
    }


    /**
     * Assigne les Views de la vue (fichier XML) aux variables d'instance
     */

    private void assignerVariablesAuxViews() {
        edtNomMagasin = (EditText) findViewById(R.id.activity_modifier_magasin_edt_nom_magasin);
        iBtnImageMagasin = (ImageButton)
                findViewById(R.id.activity_modifier_magasin_ibtn_image_magasin);
    }

    /**
     * Remplit les Views de l'Activity avec les données du produit en cours à modifier
     */
    private void peuplerViewsAvecDonneesProduit() {
        edtNomMagasin.setText(magasin.getNom());
        iBtnImageMagasin.setImageBitmap(imageMagasin);
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

        magasinCree.setId(this.idMag);

        magasinCree.setNom(edtNomMagasin.getText().toString().trim());

        magasinCree.setImage(imageMagasin);

        return magasinCree;
    }
}
