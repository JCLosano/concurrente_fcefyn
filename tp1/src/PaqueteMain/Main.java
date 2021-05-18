/**
 * Proyecto de modelado de sistema de Buffer con Consumidores
 * y Productores empleando los recursos del buffer. 
 * - TP#1 - Sistema Concurrente.
 *  
 * @authors García, Losano, Oroná and Vignolo.
 * @version 2019.04.23
 */

package PaqueteMain;

import PaqueteClases.Buffer;
import PaqueteClases.Consumer;
import PaqueteClases.Log;
import PaqueteClases.Producer;

public class Main {
	private final static int MAXProd=2;
	private final static int MAXCons=2;

	public static void main(String[] args) {
		
		Buffer storage = new Buffer();
		Producer producer[] = new Producer[MAXProd];
		Consumer consumer[] = new Consumer[MAXCons];
		Thread Tproducers[] = new Thread[MAXProd];
		Thread Tconsumers[] = new Thread[MAXCons] ;
		Log log = new Log(Tconsumers, storage);
		Thread Tlog = new Thread(log);
		
		for (int i=0; i<MAXProd; i++){
			producer[i] = new Producer(storage);
			Tproducers[i] = new Thread(producer[i], "Producer "+i);	
		}
		
		for (int j=0; j<MAXCons; j++){	
			consumer[j] = new Consumer(storage);
			Tconsumers[j] = new Thread(consumer[j], "Consumer "+j);
		}
//iniciamos los hilos de los productores, consumidores y del log.
		for(int i = 0; i<MAXProd; i++)
		{
			Tproducers[i].start();		
		}
		Tconsumers[0].start();
		Tconsumers[1].start();	
		Tlog.start();
		
	}
	}	
	

