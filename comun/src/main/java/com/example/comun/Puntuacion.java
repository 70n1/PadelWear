package com.example.comun;

public class Puntuacion {

    public static final String[] puntos = { "0", "15", "30", "40", "-", "AD" };

    private byte miPuntuacion, suPuntuacion;
    private byte misJuegos, susJuegos;
    private byte misSets, susSets;
    //public static Activity a;

    Puntuacion() {
//		miPuntuacion = suPuntuacion = misJuegos = susJuegos = misSets = susSets = 0;
    }

    Puntuacion(byte miPuntuacion, byte suPuntuacion, byte misJuegos,
               byte susJuegos, byte misSets, byte susSets) {
        this.miPuntuacion = miPuntuacion;
        this.suPuntuacion = suPuntuacion;
        this.misJuegos = misJuegos;
        this.susJuegos = susJuegos;
        this.misSets = susSets;
        this.susSets = susSets;
    }

    Puntuacion(Puntuacion puntuacion, boolean miEquipo) {
        this.miPuntuacion = puntuacion.miPuntuacion;
        this.suPuntuacion = puntuacion.suPuntuacion;
        this.misJuegos = puntuacion.misJuegos;
        this.susJuegos = puntuacion.susJuegos;
        this.misSets = puntuacion.misSets;
        this.susSets = puntuacion.susSets;
        aumentarPuntuacion(miEquipo);
    }

    /*public static Vector<Integer> sacarHistorial(Vector<Puntuacion> v) {
        Vector<Integer> tmp = new Vector<Integer>();
        Puntuacion ant = new Puntuacion();
        for (Puntuacion p : v) {
            if (p.misSets > ant.misSets) {
                // He ganado Set
                tmp.add(5);
            } else if (p.susSets > ant.susSets) {
                // Ha ganado Set
                tmp.add(4);
            } else if (p.misJuegos > ant.misJuegos) {
                // He ganado Juego
                tmp.add(3);
            } else if (p.susJuegos > ant.susJuegos) {
                // Ha ganado Juego
                tmp.add(2);
            } else if ((ant.miPuntuacion < 3 && p.miPuntuacion > ant.miPuntuacion)
                    || (ant.miPuntuacion == 4 && p.miPuntuacion == 5)
                    || (ant.miPuntuacion == 4 && p.miPuntuacion == 3)) {
                // He ganado punto
                tmp.add(1);
            } else if ((ant.suPuntuacion < 3 && p.suPuntuacion > ant.suPuntuacion)
                    || (ant.suPuntuacion == 4 && p.suPuntuacion == 5)
                    || (ant.suPuntuacion == 4 && p.suPuntuacion == 3)) {
                // Ha ganado Juego
                tmp.add(0);
            }
            ant = p;
        }
        return tmp;
    }*/

    private void aumentarPuntuacion(boolean miEquipo) {
        if (miEquipo) {
            if (miPuntuacion < 3) {
                miPuntuacion++;
            } else if (miPuntuacion == 3 && suPuntuacion < 3) {
                aumentarJuego(true);
            } else if (miPuntuacion == 3 && suPuntuacion == 3) {
                miPuntuacion = 5;
                suPuntuacion = 4;
            } else if (miPuntuacion == 5) {
                aumentarJuego(true);
            } else if (miPuntuacion == 4) {
                miPuntuacion = 3;
                suPuntuacion = 3;
            }
        } else {
            if (suPuntuacion < 3) {
                suPuntuacion++;
            } else if (suPuntuacion == 3 && miPuntuacion < 3) {
                aumentarJuego(false);
            } else if (suPuntuacion == 3 && miPuntuacion == 3) {
                suPuntuacion = 5;
                miPuntuacion = 4;
            } else if (suPuntuacion == 5) {
                aumentarJuego(false);
            } else if (suPuntuacion == 4) {
                suPuntuacion = 3;
                miPuntuacion = 3;

            }
        }
    }

    private void aumentarJuego(boolean miEquipo) {
        miPuntuacion = suPuntuacion = 0;
        if (miEquipo) {
            if (misJuegos > 4 && misJuegos - susJuegos > 0) {
                aumentarSet(true);
            } else {
                misJuegos++;
            }
        } else {
            if (susJuegos > 4 && susJuegos - misJuegos > 0) {
                aumentarSet(false);
            } else {
                susJuegos++;
            }
        }
    }

    private void aumentarSet(boolean miEquipo) {
        misJuegos = susJuegos = 0;
        //((MainActivity) a).tomarPulsaciones();
        if (miEquipo) {
            misSets++;
        } else {
            susSets++;
        }
    }

    public byte getMiPuntuacion() {
        return miPuntuacion;
    }

    public byte getSuPuntuacion() {
        return suPuntuacion;
    }

    public byte getMisJuegos() {
        return misJuegos;
    }

    public byte getSusJuegos() {
        return susJuegos;
    }

    public byte getMisSets() {
        return misSets;
    }

    public byte getSusSets() {
        return susSets;
    }

}
