package PaqueteClases;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;


/**
 *
 * Clase colas, implementa 1 cola por cada transicion, asi si una transicion
 * no se puede disparar, el hilo que haya tratado de disparar esta transicion
 * se encolara en la cola de esta. Para esto hace uso de semaphore(0) con 0
 * permits.
 *
 * @version 1.0
 * @author Losano, Garcia
 */

public class Colas {
    private final int CANT_TRANSICIONES;
    private ArrayList<Semaphore> colasTransiciones;
    private int[][] vectorEncolados;

    /**
     * inicializa las colas segun CANT_TRANSICIONES tenga poniendo una cola
     * por transicion
     *
     * @param CANT_TRANSICIONES
     */
    public Colas (int CANT_TRANSICIONES) {
        this.CANT_TRANSICIONES = CANT_TRANSICIONES;
        colasTransiciones = new ArrayList<Semaphore>();
        vectorEncolados = new int[CANT_TRANSICIONES][1];

        try {
            for (int i = 0; i < CANT_TRANSICIONES; i++) {
                colasTransiciones.add(new Semaphore(0));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @return transiciones que tengan un hilo encolado
     */
    public int[][] getEncolados() {
        return vectorEncolados;
    }

    /**
     * se bloqua en la cola de transicion
     * @param transicion
     */
    public void acquire(int transicion) {
        try {
            vectorEncolados[transicion][0] = 1;
            colasTransiciones.get(transicion).acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * senaliza al hilo que esta en la cola de transicion
     * @param transicion
     */
    public void notify(int transicion) {
        vectorEncolados[transicion][0] = 0;
        colasTransiciones.get(transicion).release();
    }

    /**
     * senaliza a todos los hilos encolados en todas las transiciones
     */
    public void allNotify() {
        for (int i = 0; i < CANT_TRANSICIONES; i++) {
            vectorEncolados[i][0] = 0;
            colasTransiciones.get(i).release();
        }
    }
}
