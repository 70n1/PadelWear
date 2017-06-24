package com.example.comun;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Jesús Tomás on 04/05/2015.
 */
public class DireccionesGestureDetector extends GestureDetector {

    public interface OnDireccionesGestureListener extends OnGestureListener {
        boolean onDerecha(MotionEvent e1, MotionEvent e2, float distX, float distY);
        boolean onIzquierda(MotionEvent e1, MotionEvent e2, float distX, float distY);
        boolean onArriba(MotionEvent e1, MotionEvent e2, float distX, float distY);
        boolean onAbajo(MotionEvent e1, MotionEvent e2, float distX, float distY);
    }

    OnDireccionesGestureListener listener;
    MotionEvent antiguaPulsacion;
    boolean pulsando = false;
    float distMin = 20;

    public DireccionesGestureDetector(Context c, OnDireccionesGestureListener listener) {
        super(c, listener);
        this.listener = listener;
    }

    public boolean onTouchEvent(MotionEvent evento) {
        Log.e("",evento.getAction() +"");
        boolean procesado = false;
        if (evento.getAction() == MotionEvent.ACTION_DOWN) {
            antiguaPulsacion = MotionEvent.obtain(evento);
            pulsando = true;
        } else if (evento.getAction() == MotionEvent.ACTION_UP && pulsando) {
            pulsando = false;
            float distX = antiguaPulsacion.getX() - evento.getX();
            float distY = antiguaPulsacion.getY() - evento.getY();
            if ((Math.abs(distX / distY) > 2) && (distX > distMin)) {
                procesado = listener.onIzquierda(antiguaPulsacion, evento, distX, distY);
            }
            if ((Math.abs(distX / distY) > 2) && (-distX > distMin)) {
                procesado = listener.onDerecha(antiguaPulsacion, evento, distX, distY);
            }
            if ((Math.abs(distY / distX) > 2) && (distY > distMin)) {
                procesado = listener.onArriba(antiguaPulsacion, evento, distX, distY);
            }
            if ((Math.abs(distY / distX) > 2) && (-distY > distMin)) {
                procesado = listener.onAbajo(antiguaPulsacion, evento, distX, distY);
            }
        }
        return procesado || super.onTouchEvent(evento);
    }

/*
    public boolean onDerecha(MotionEvent e1, MotionEvent e2, float distX, float distY) {
        if (listener == null)
            return false;
        else
            return listener.onDerecha(e1, e2, distX, distY);
    }

    public boolean onIzquierda(MotionEvent e1, MotionEvent e2, float distX, float distY) {
        if (listener == null)
            return false;
        else
            return listener.onIzquierda(e1, e2, distX, distY);
    }

    public boolean onArriba(MotionEvent e1, MotionEvent e2, float distX, float distY) {
        if (listener == null)
            return false;
        else
            return listener.onArriba(e1, e2, distX, distY);
    }

    public boolean onAbajo(MotionEvent e1, MotionEvent e2, float distX, float distY) {
        if (listener == null)
            return false;
        else
            return listener.onAbajo(e1, e2, distX, distY);
    }
*/

    public static class SimpleOnDireccionesGestureListener
            extends SimpleOnGestureListener
            implements OnDireccionesGestureListener{

        @Override
        public boolean onDerecha(MotionEvent e1, MotionEvent e2, float distX, float distY) {
            return false;
        }

        @Override
        public boolean onIzquierda(MotionEvent e1, MotionEvent e2, float distX, float distY) {
            return false;
        }

        @Override
        public boolean onArriba(MotionEvent e1, MotionEvent e2, float distX, float distY) {
            return false;
        }

        @Override
        public boolean onAbajo(MotionEvent e1, MotionEvent e2, float distX, float distY) {
            return false;
        }
    }
}