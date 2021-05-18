package PaqueteClases;

import java.util.concurrent.Semaphore;

import PaqueteClases.Colas;
import PaqueteClases.RdP;

public class GestorDelMonitor {

	private RdP rdp;
	private Colas bloqueados;
	private Semaphore mutex;
	private int[] vS,vC,m;
	private int cantTrans;

	/**
	  * Constructor de la clase
	  * @param storage1 Buffer
	  * @param storage2 Buffer
	  * */
	public GestorDelMonitor(RdP rdp) {
		this.rdp = rdp;
		mutex 	= new Semaphore(1, true);
		bloqueados	= new Colas();
//		cantTrans	= rdp.getCantidadTransiciones();
		m  = new int[] {0,0,0,0,0,0,0,0}; 
		vS = new int[] {0,0,0,0,0,0,0,0};
		vC = new int[] {0,0,0,0,0,0,0,0};
	}
	
	/** 
	  * Método que dispara la transicion que reciba
	  * @param transicion int
	 * @throws InterruptedException 
	  * */
	public void dispararTransicion(int transicion) throws InterruptedException {
		mutex.acquire();	
		
		while ( !rdp.disparar(transicion) ) {
				mutex.release();
				bloqueados.wait(transicion);	// LOS HILOS QUE NO PUDIERON SER DISPARADOS SE CLAVAN ACÁ
				mutex.acquire();		
		}
		
		System.out.println("Yo el thread " + Thread.currentThread().getName() + " disparé la transicion " + transicion);
		
//		vS = rdp.sensibilizadas();
		vC = bloqueados.tieneEncolados();
		AND();
		
		for(int i=0 ;i<cantTrans;i++) {
			if(m[i]==1) {
				bloqueados.liberaHilo(i);
				break;	 
			}
		}
		mutex.release();
	}
	 
	 /**
	  * Método que hace un AND entre los elementos de la cola de transiciones
	  * sensibilizadas: vS, y los hilos que estan bloqueados esperando en la cola: vC
	  * @param vS int[]
	  * @param vC int[]
	  * @return m int[]
	  * */
	 private void AND(){
		 for(int i=0;i<cantTrans;i++) {m[i] = 0;}
		 for(int i=0; i<cantTrans; i++){	
			 m[i] = vS[i] & vC[i];
		 }
}
}
