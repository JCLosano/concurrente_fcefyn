package PaqueteMain;

import PaqueteClases.Hilo;
import PaqueteClases.Monitor;

import java.time.Instant;
import java.util.ArrayList;

/**
 *
 * Clase main del proyecto, crea los hilos, el monitor y las constantes
 * de cantidad de plazas, transiciones, eventos.
 *
 * @version 1.0
 * @author Losano, Garcia
 */

public class Main {

    public static void main(String[] args) {

        final int CANT_PLAZAS = 16;
        final int CANT_TRANSICIONES = 15;
        final int CANT_EVENTOS = 1000;

        Instant inicio = Instant.now();
        Instant fin;

        Monitor monitor = new Monitor(CANT_PLAZAS, CANT_TRANSICIONES,
                CANT_EVENTOS);

        ArrayList<Hilo> hilos = new ArrayList<Hilo>();

        //Thread-0
        ArrayList<Integer> hilo0 = new ArrayList<>(); //t0
        hilo0.add(12);//t0
        //Thread-1
        ArrayList<Integer> hilo1 = new ArrayList<>(); //t1, t8
        hilo1.add(10); //t1
        //Thread-2
        ArrayList<Integer> hilo2 = new ArrayList<>(); //t1, t8
        hilo2.add(11); //t8
        //Thread-3
        ArrayList<Integer> hilo3 = new ArrayList<>(); //t2, t3, t7
        hilo3.add(5); //t2
        hilo3.add(6); //t3
        hilo3.add(9); //t7
        //Thread-4
        ArrayList<Integer> hilo4 = new ArrayList<>(); //t4
        hilo4.add(7); //t4
        //Thread-5
        ArrayList<Integer> hilo5 = new ArrayList<>(); //t5, t6
        hilo5.add(8); //t5
        hilo5.add(13); //t6
        //Th5read-6
        ArrayList<Integer> hilo6 = new ArrayList<>(); //t9, t10, t14
        hilo6.add(0);//t9
        hilo6.add(2);//t10
        hilo6.add(4);//t14
        //Thread-7
        ArrayList<Integer> hilo7 = new ArrayList<>(); //t11
        hilo7.add(1);//t11
        //Thread-8
        ArrayList<Integer> hilo8 = new ArrayList<>(); //t12, t13
        hilo8.add(3);//t12
        hilo8.add(14);//t13

        hilos.add(new Hilo(monitor, hilo0));
        hilos.add(new Hilo(monitor, hilo1));
        hilos.add(new Hilo(monitor, hilo2));
        hilos.add(new Hilo(monitor, hilo3));
        hilos.add(new Hilo(monitor, hilo4));
        hilos.add(new Hilo(monitor, hilo5));
        hilos.add(new Hilo(monitor, hilo6));
        hilos.add(new Hilo(monitor, hilo7));
        hilos.add(new Hilo(monitor, hilo8));

        // inicializacion de hilos
        for (Hilo hilo : hilos) {
            hilo.start();
        }
        try {
            for (Hilo hilo : hilos) {
                hilo.join();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        fin = Instant.now();
        System.out.println("Tiempo de ejecucion: "
                + (fin.toEpochMilli() - inicio.toEpochMilli()) + " mseg");
        monitor.guardarLog();
    }

}