package PaqueteMain;

import PaqueteClases.Hilo;
import PaqueteClases.RdP;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		final int CANT_HILOS = 7;
		final int CANT_PLAZAS = 16;
		final int CANT_TRANSICIONES = 15;

		ArrayList<Hilo> hilos = new ArrayList<>();

		for (int i = 0; i < CANT_HILOS; i++) {
			hilos.add(new Hilo());
		}
		// inicializacion de hilos
/*		for (Hilo hilo : hilos) {
			hilo.start();
		}
		try {
			for (Hilo hilo : hilos) {
				hilo.join();
			}
		} catch (Exception e) {
			System.out.println(e);
		}*/
		RdP rdp = new RdP(CANT_PLAZAS, CANT_TRANSICIONES);
	}

}