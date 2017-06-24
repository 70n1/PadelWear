package com.example.padelwear;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;

/**
 * Created by el70n on 24/06/2017.
 */

public class Pasos extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_tarjeta);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CardFragment cardFragment = CardFragment.create("Pasos:", "911 en 13 minutos", R.drawable.pasos);
        fragmentTransaction.add(R.id.contenedor, cardFragment);
        fragmentTransaction.commit();
    }
}