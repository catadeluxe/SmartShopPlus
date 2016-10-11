package ca.qc.bdeb.p55.smartshopplus.vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        dbHelper = DbHelper.getInstance(getApplicationContext());

        listeMagasins = dbHelper.getListeClient();

        lvwMagasins = (ListView) findViewById(R.id.activity_main_lvw_magasins);

        arrayAdapterMagasins = new ArrayAdapterMagasins(this, R.layout.relative_layout, listeMagasins);

        lvwMagasins.setAdapter(arrayAdapterMagasins);


        /**
         * Évènement qui se déclenche lorsque l'utilisateur appuie sur une ligne de
         * la liste de magasins
         *
         */
        lvwMagasins.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * Déclenche l'activité MagasinActivity lorsqu'un magasin a été appuyé
             *
             * doc https://developer.android.com/reference/android/widget/AdapterView.OnItemClickListener.html
             * @param parent AdapterView: The AdapterView where the click happened.
             * @param view View: The view within the AdapterView that was clicked (this will be a view provided by the adapter)
             * @param position int: The position of the view in the adapter.
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
}