package ca.qc.bdeb.p55.smartshopplus.vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import ca.qc.bdeb.p55.smartshopplus.R;
import ca.qc.bdeb.p55.smartshopplus.bd.DbHelper;
import ca.qc.bdeb.p55.smartshopplus.modele.ArrayAdapterMagasins;
import ca.qc.bdeb.p55.smartshopplus.modele.Magasin;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "1";


    private DbHelper dbHelper;

    ArrayAdapterMagasins arrayAdapterMagasins;
    ListView lvwMagasins;
    List<Magasin> listeMagasins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(getResources().getString(R.string.title_product_list) + " - " + getResources().getString(R.string.app_name));

        dbHelper = DbHelper.getInstance(getApplicationContext());

        listeMagasins = dbHelper.getListeMagasins();

        lvwMagasins = (ListView) findViewById(R.id.activity_main_lvw_magasins);

        arrayAdapterMagasins = new ArrayAdapterMagasins(this, R.layout.relative_layout_liste_magasins, listeMagasins);

        lvwMagasins.setAdapter(arrayAdapterMagasins);

        /**
         * Évènement qui se déclenche lorsque l'utilisateur appuie sur une ligne de
         * la liste de magasins
         *
         */
        lvwMagasins.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * Déclenche l'activité ModificationMagasinActivity lorsqu'un magasin a été appuyé
             *
             * doc https://developer.android.com/reference/android/widget/AdapterView.OnItemClickListener.html
             * @param parent AdapterView: The AdapterView where the click happened.
             * @param view View: The view within the AdapterView that was clicked (this will be a view provided by the arrayAdapterProduits)
             * @param position int: The position of the view in the arrayAdapterProduits.
             * @param id long: The row id of the item that was clicked.
             */
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Magasin magClique = (Magasin) parent.getItemAtPosition(position);

                Intent intent = new Intent(getBaseContext(), MagasinActivity.class);
                intent.putExtra(EXTRA_MESSAGE, magClique.getId());
                startActivity(intent);
            }
        });
    }

    /**
     * Actions à faire lorsqu'on revient à l'activité
     */
    @Override
    protected void onStart() {
        super.onStart();

        listeMagasins = dbHelper.getListeMagasins();

        arrayAdapterMagasins = new ArrayAdapterMagasins(this, R.layout.relative_layout_liste_magasins, listeMagasins);

        lvwMagasins.setAdapter(arrayAdapterMagasins);

    }

    /**
     * Affiche et gonfle la barre de menu
     *
     * @param menu menu à gonfler
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
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
                Intent intent = new Intent(this, AjouterMagasinActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
