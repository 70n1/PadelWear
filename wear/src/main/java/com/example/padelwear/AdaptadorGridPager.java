package com.example.padelwear;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;

/**
 * Created by el70n on 24/06/2017.
 */

public class AdaptadorGridPager extends FragmentGridPagerAdapter {
    private final Context contexto;
    private final String t[][] = {{"21/5/15", "juego 1", "juego 1", "juego 2", "juego 2"}, {"23/5/15", "juego 1", "juego 1"}};
    private final String c[][] = {{"6-2 6-7", "6-2, 65% puntos ganados, 81% al servicio", "22 min. 623 pasos, 33º", "6-7, 48% puntos ganados, 64% al servicio", "36 min. 923 pasos, 32º"}, {"4-6", "4-6, 45% puntos ganados, 53% al servicio", "28 min. 723 pasos, 28º"}};

    public AdaptadorGridPager(Context contexto, FragmentManager fm) {
        super(fm);
        this.contexto = contexto;
    }

    @Override
    public Fragment getFragment(int fila, int col) {
        int icono = R.drawable.pasos;
        if ((col == 0) || ((col - 1) % 2 == 0)) {
            if ((c[fila][col].charAt(0) >= c[fila][col].charAt(2))) {
                icono = R.drawable.cara_alegre;
            } else {
                icono = R.drawable.cara_triste;
            }
        }
        CardFragment fragment = CardFragment.create(t[fila][col], c[fila][col], icono);
        return fragment;
    }

    @Override
    public Drawable getBackgroundForPage(int row, int column) {
        return contexto.getResources().getDrawable(R.drawable.escudo_upv_cuadrado);
    }

    @Override
    public int getRowCount() {
        return t.length;
    }

    @Override
    public int getColumnCount(int fila) {
        return t[fila].length;
    }
}
