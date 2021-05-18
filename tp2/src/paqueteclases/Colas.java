package paqueteclases;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Esta clase se ocupa de las colas de transiciones con los hilos que se bloquearon.
 * */
public class Colas{
	private ArrayList<Semaphore> colasDeTransiciones;
	private int cantidadT;
	private TiempoActual time;
	
	/**
	  * Constructor de la clase
	  * @param mutex Semaphore
	  * */
	public Colas() {
		colasDeTransiciones = new ArrayList<Semaphore>();
		cantidadT=8;
		crearColas();
		time = new TiempoActual();
	}
	
	/**
	  * Metodo que crea el ArrayList de semáforos
	  * */
	private void crearColas() throws NullPointerException {
		for(int j=0;j<cantidadT;j++)
			colasDeTransiciones.add(new Semaphore(0,true));
	}
	
	/**
	  * Metodo que pregunta que transiciones tienen hilos encolados.
	  * @return vector int[]
	  * */
	public int[] tieneEncolados(){
		int[] vector = new int[] {0,0,0,0,0,0,0,0};
		for(int i=0; i<cantidadT; i++){
			if(colasDeTransiciones.get(i).hasQueuedThreads()) { // hasQueuedThreads() = ¿tiene hilos encolados?
				vector[i] = 1;
			} else {
				vector[i] = 0;
			}	
		}
		return vector;
	}
	
	/**
	  * Metodo que manda a dormir un hilo que no pudo disparar, a la cola de esa transicion
	  * @param transicion int
	  * @throws InterruptedException 
	  * */
	public void wait(int transicion) throws InterruptedException {
		System.out.println("//////////// Se bloquea en la transicion " + transicion + " el thread " + Thread.currentThread().getName() + " [ " + time.getTiempo() + " ] ");
		colasDeTransiciones.get(transicion).acquire();	// SE CLAVAN ACÁ HASTA QUE OTRO HILO LOS LIBERE
		System.out.println("//// Se libera el hilo " + Thread.currentThread().getName() + " de la transicion " + transicion);
	}
	
	/**
	  * Metodo que despierta un hilo que se bloqueó en la cola de esa transicion
	  * @param transicion int
	  * */
	public void liberaHilo(int transicion) {
		System.out.println("//// Se libera un hilo de la transicion " + transicion + ", por el thread " + Thread.currentThread().getName() + " [ " + time.getTiempo() + " ] ");
		colasDeTransiciones.get(transicion).release();
	}
}