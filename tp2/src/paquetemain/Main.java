package paquetemain;

import java.util.Arrays;

import paqueteclases.Buffer;
import paqueteclases.Consumer;
import paqueteclases.GestorDelMonitor;
import paqueteclases.Log;
import paqueteclases.Producer;
import paqueteclases.RdP;

public class Main {

	private final static int MAXProd=5;
	private final static int MAXCons=8;

	public static void main(String[] args) {
		
		RdP rdp = new RdP();
		Buffer storage1 = new Buffer(rdp,10);
		Buffer storage2 = new Buffer(rdp,15);
		GestorDelMonitor monitor = new GestorDelMonitor(rdp);
		
		Producer producer[] = new Producer[MAXProd];
		Consumer consumer[] = new Consumer[MAXCons];
		Thread Tproducers[] = new Thread[MAXProd];
		Thread Tconsumers[] = new Thread[MAXCons] ;
		
		//Log log = new Log(Tconsumers, storage1, storage2, MAXCons);
		//Thread Tlog = new Thread(log);

	for (int i=0; i<MAXProd; i++){
		producer[i] = new Producer(monitor,storage1,storage2);
		Tproducers[i] = new Thread(producer[i], "PRODUCER "+i);	
	}

	for (int j=0; j<MAXCons; j++){	
		consumer[j] = new Consumer(monitor,storage1,storage2);
		Tconsumers[j] = new Thread(consumer[j], "CONSUMER "+j);
	}
	
	long iniciailStart = System.currentTimeMillis();
	
	//	Iniciamos los hilos de los productores, consumidores y del log.

	for ( int i = 0; i<MAXCons; i++)
	{	if(i<MAXProd) {
		Tproducers[i].start();
		}
		Tconsumers[i].start();
	}
	//Tlog.start();
	
	for (Thread t : Tproducers) {
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	for (Thread t : Tconsumers) {
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	System.out.println(System.currentTimeMillis()-iniciailStart);
	}
}
