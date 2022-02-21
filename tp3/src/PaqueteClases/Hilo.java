package PaqueteClases;

import java.util.ArrayList;

/**
 * Clase Hilo, extiende de Thread e implementa el metodo run()
 * Recibe las transiciones que va a disparar.
 * Recibe el monitor para ejecutar el metodo disparar.
 *
 * @version 1.0
 * @authors Losano, Garcia
 */
public class Hilo extends Thread {

    Monitor monitor;
    ArrayList<Integer> transiciones;

    public Hilo(Monitor monitor, ArrayList<Integer> transiciones) {
        this.monitor = monitor;
        this.transiciones = transiciones;
    }

    /**
     * trata de disparar sus transiciones.
     */
    public void run() {

        int disparo = 0;

        String nombre = this.getName();

        while (true) {
            for (int transicion : transiciones) {

                try {
                    disparo = monitor.disparar(transicion);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (disparo == -1) {
                    System.out.println("Hilo: " + this.getName() + " termino");
                    return;
                }
            }
        }

    }
}
