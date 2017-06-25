package com.example.padelwear;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.comun.DireccionesGestureDetector;
import com.example.comun.Partida;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by el70n on 24/06/2017.
 */

//public class Contador extends Activity implements GoogleApiClient.ConnectionCallbacks, MessageApi.MessageListener, DataApi.DataListener {
public class Contador extends Activity implements GoogleApiClient.ConnectionCallbacks, DataApi.DataListener {
    private static final String WEAR_PUNTUACION = "/puntuacion";
    private static final String KEY_MIS_PUNTOS = "com.example.padel.key.mis_puntos";
    private static final String KEY_MIS_JUEGOS = "com.example.padel.key.mis_juegos";
    private static final String KEY_MIS_SETS = "com.example.padel.key.mis_sets";
    private static final String KEY_SUS_PUNTOS = "com.example.padel.key.sus_puntos";
    private static final String KEY_SUS_JUEGOS = "com.example.padel.key.sus_juegos";
    private static final String KEY_SUS_SETS = "com.example.padel.key.sus_sets";
    private Partida partida;
    private TextView misPuntos, misJuegos, misSets, susPuntos, susJuegos, susSets;
    private Vibrator vibrador;
    private long[] vibrEntrada = {0l, 500};
    private long[] vibrDeshacer = {0l, 500, 500, 500};

    static DataMap dataMap;

    private GoogleApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contador);
        partida = new Partida();
        vibrador = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        misPuntos = (TextView) findViewById(R.id.misPuntos);
        susPuntos = (TextView) findViewById(R.id.susPuntos);
        misJuegos = (TextView) findViewById(R.id.misJuegos);
        susJuegos = (TextView) findViewById(R.id.susJuegos);
        misSets = (TextView) findViewById(R.id.misSets);
        susSets = (TextView) findViewById(R.id.susSets);
        actualizaNumeros();
        View fondo = findViewById(R.id.fondo);
        fondo.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector detector = new DireccionesGestureDetector(Contador.this, new DireccionesGestureDetector.SimpleOnDireccionesGestureListener() {
                @Override
                public boolean onArriba(MotionEvent e1, MotionEvent e2, float distX, float distY) {
                    partida.rehacerPunto();
                    vibrador.vibrate(vibrDeshacer, -1);
                    actualizaNumeros();
                    return true;
                }

                @Override
                public boolean onAbajo(MotionEvent e1, MotionEvent e2, float distX, float distY) {
                    partida.deshacerPunto();
                    vibrador.vibrate(vibrDeshacer, -1);
                    actualizaNumeros();
                    return true;
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent evento) {
                detector.onTouchEvent(evento);
                return true;
            }
        });
        misPuntos.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector detector = new DireccionesGestureDetector(Contador.this, new DireccionesGestureDetector.SimpleOnDireccionesGestureListener() {
                @Override
                public boolean onDerecha(MotionEvent e1, MotionEvent e2, float distX, float distY) {
                    partida.puntoPara(true);
                    vibrador.vibrate(vibrEntrada, -1);
                    actualizaNumeros();

                    return true;
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent evento) {
                detector.onTouchEvent(evento);
                return true;
            }
        });
        susPuntos.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector detector = new DireccionesGestureDetector(Contador.this, new DireccionesGestureDetector.SimpleOnDireccionesGestureListener() {
                @Override
                public boolean onDerecha(MotionEvent e1, MotionEvent e2, float distX, float distY) {
                    partida.puntoPara(false);
                    vibrador.vibrate(vibrEntrada, -1);
                    actualizaNumeros();
                    return true;
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent evento) {
                detector.onTouchEvent(evento);
                return true;
            }
        });

        apiClient = new GoogleApiClient.Builder(this).addApi(Wearable.API).addConnectionCallbacks(this).build();
    }

    void actualizaNumeros() {
        misPuntos.setText(partida.getMisPuntos());
        susPuntos.setText(partida.getSusPuntos());
        misJuegos.setText(partida.getMisJuegos());
        susJuegos.setText(partida.getSusJuegos());
        misSets.setText(partida.getMisSets());
        susSets.setText(partida.getSusSets());
    }


    @Override
    protected void onStart() {
        super.onStart();
        apiClient.connect();
    }

    @Override
    protected void onStop() {
        Wearable.DataApi.removeListener(apiClient, this);
        //Wearable.MessageApi.removeListener(apiClient, this);
        if (apiClient != null && apiClient.isConnected()) {
            apiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {

        Wearable.DataApi.addListener(apiClient, this);
        //Wearable.MessageApi.addListener(apiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    /*
    @Override
    public void onMessageReceived(final MessageEvent mensaje) {
        / *if (mensaje.getPath().equalsIgnoreCase(WEAR_ARRANCAR_ACTIVIDAD)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, Contador.class));
                }
            });
        }* /
    }
    */

    @Override
    public void onDataChanged(DataEventBuffer eventos) {
        for (DataEvent evento : eventos) {
            if (evento.getType() == DataEvent.TYPE_CHANGED) {
                DataItem item = evento.getDataItem();
                if (item.getUri().getPath().equals(WEAR_PUNTUACION)) {
                    dataMap = DataMapItem.fromDataItem(item).getDataMap();

                    partida.setPuntuacion(dataMap.getByte(KEY_MIS_PUNTOS), dataMap.getByte(KEY_SUS_PUNTOS), dataMap.getByte(KEY_MIS_JUEGOS), dataMap.getByte(KEY_SUS_JUEGOS), dataMap.getByte(KEY_MIS_SETS), dataMap.getByte(KEY_SUS_SETS) );
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            actualizaNumeros();
                            /*misPuntos.setText(String.valueOf(dataMap.getByte(KEY_MIS_PUNTOS)));
                            susPuntos.setText(String.valueOf(dataMap.getByte(KEY_SUS_PUNTOS)));
                            misJuegos.setText(String.valueOf(dataMap.getByte(KEY_MIS_JUEGOS)));
                            susJuegos.setText(String.valueOf(dataMap.getByte(KEY_SUS_JUEGOS)));
                            misSets.setText(String.valueOf(dataMap.getByte(KEY_MIS_SETS)));
                            susSets.setText(String.valueOf(dataMap.getByte(KEY_SUS_SETS)));*/
                        }
                            /*((TextView) findViewById(R.id.textoContador)).setText(Integer.toString(contador));
                        }*/
                    });
                }
            }

        }
    }
}
            /*} else if (evento.getType() == DataEvent.TYPE_DELETED) {

                // Algún ítem ha sido borrado
                }
                */

/*
                actualizaNumeros();

                PutDataMapRequest putDataMapReq = PutDataMapRequest.create(WEAR_PUNTUACION);
                putDataMapReq.getDataMap().putByte(KEY_MIS_PUNTOS, partida.getMisPuntosByte());
                putDataMapReq.getDataMap().putByte(KEY_MIS_JUEGOS, partida.getMisJuegosByte());
                putDataMapReq.getDataMap().putByte(KEY_MIS_SETS, partida.getMisSetsByte());
                putDataMapReq.getDataMap().putByte(KEY_SUS_PUNTOS, partida.getSusPuntosyte());
                putDataMapReq.getDataMap().putByte(KEY_SUS_JUEGOS, partida.getSusJuegosByte());
                putDataMapReq.getDataMap().putByte(KEY_SUS_SETS, partida.getSusSetsByte());

                PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
*/
        /*
        for (DataEvent evento : eventos) {
            if (evento.getType() == DataEvent.TYPE_CHANGED) {
                actualizaNumeros();

            } else if (evento.getType() == DataEvent.TYPE_DELETED) {
                // Algún ítem ha sido borrado
                actualizaNumeros();
            }
        }*/
