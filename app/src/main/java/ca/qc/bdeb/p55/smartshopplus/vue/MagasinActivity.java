package ca.qc.bdeb.p55.smartshopplus.vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

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
        getSupportActionBar().setTitle(magasin.getNom());

        listeProduits = dbHelper.getListeProduitsMagasin(magasin.getId());
        lvwProduits = (ListView) findViewById(R.id.activity_magasin_lvw_produits);

        arrayAdapterProduits = new ArrayAdapterProduits(this, R.layout.relative_layout_liste_produits, listeProduits);

        lvwProduits.setAdapter(arrayAdapterProduits);

    }
}
