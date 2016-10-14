package ca.qc.bdeb.p55.smartshopplus.vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ca.qc.bdeb.p55.smartshopplus.R;
import ca.qc.bdeb.p55.smartshopplus.bd.DbHelper;
import ca.qc.bdeb.p55.smartshopplus.modele.Magasin;

public class AjouterMagasinActivity extends AppCompatActivity {

    Magasin magasin;
    Long idMag;
    Intent intent;

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_magasin);

        dbHelper = DbHelper.getInstance(getApplicationContext());
        intent = getIntent();
        idMag = intent.getLongExtra(MainActivity.EXTRA_MESSAGE, -1);

        magasin = dbHelper.getMagasin(idMag);

        getSupportActionBar().setTitle(magasin.getNom() + " - " + getResources().getString(R.string.app_name));
    }
}
