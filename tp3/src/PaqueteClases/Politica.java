package PaqueteClases;

import java.util.Vector;

/**
 *
 * Clase politica, se encarga de elegir el hilo que se debe senalizar de los
 * que se encuentran esperando en las colas de las transiciones.
 *
 * @version 1.0
 * @author Losano, Garcia
 */

public class Politica {
    private final int CANT_TRANSICIONES;
    Short T1, T8, P2, P9;

    /**
     * inicializa la politica
     *
     * @param CANT_TRANSICIONES
     */
    public Politica(int CANT_TRANSICIONES) {
        this.CANT_TRANSICIONES = CANT_TRANSICIONES;
        T1 = 10;
        T8 = 11;
        P2 = 11;
        P9 = 6;
    }

    /**
     * se fija que hilo debe despertar segun vectorEncolados y vectorEx
     * T1 y T8 tienen prioridad sobre las demas transiciones
     *
     * @param vectorEncolados
     * @param vectorEx
     * @param vectorM
     * @return T1 si buffer 2 es mayor a buffer 1, T8 si buffer 2 es menor o
     * igual a buffer 1
     * cualquier otra transicion encolada y sensibilizada si lo anterior no
     * ocurre
     */
    public int cualDespierto(int[][] vectorEncolados, int[][] vectorEx
                             , int[][] vectorM) {

        if (vectorEncolados[T1][0] == 1 && vectorEncolados[T8][0] == 1
                                        && vectorEx[T1][0] == 1) {
            if (vectorM[P9][0] > vectorM[P2][0])
                return T1;
            else
                return T8;
        }

        for (int i = 0; i < CANT_TRANSICIONES; i++) {
            if (vectorEncolados[i][0] == 1 && vectorEx[i][0] == 1)
                return i;
        }
        return -1;
    }
}
