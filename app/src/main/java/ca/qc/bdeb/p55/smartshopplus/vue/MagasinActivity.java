package ca.qc.bdeb.p55.smartshopplus.vue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ca.qc.bdeb.p55.smartshopplus.R;

public class MagasinActivity extends AppCompatActivity {

    Long idMag;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magasin);

        intent = getIntent();
        idMag = intent.getLongExtra(MainActivity.EXTRA_MESSAGE, -1);




    }
}
