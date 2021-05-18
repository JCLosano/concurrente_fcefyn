package paqueteclases;

/**
 * Esta clase realiza una ejecución con todos los consumidores trabajando
 * concurrentemente, donde cada consumidor quita 6250 productos de los buffers.
 * */
public class Consumer implements Runnable {
	
	private final int MAXConsumidos=6250;
	private GestorDelMonitor monitor;
	private Buffer storage1, storage2;
	private int i=0;
	private int transicion;
	private TiempoActual time;
	
	/**
	 * Constructor de la clase
	 * @param monitor GestorDelMonitor
	 * @param storage1 Buffer
	 * @param storage2 Buffer
	 * 
	 * */
	public Consumer(GestorDelMonitor monitor, Buffer storage1, Buffer storage2) {
		this.monitor=monitor;
		this.storage1=storage1;
		this.storage2=storage2;
		time = new TiempoActual();
	}

	@Override
	public void run () {
		while(i<MAXConsumidos) {
			
			try {
				if (storage1.getCantidadDeElementos() >= storage2.getCantidadDeElementos()) {
					monitor.dispararTransicion(5);
					transicion=5;
				} else {
					monitor.dispararTransicion(4);
					transicion=4;
				}
				//System.out.println("El consumidor: " + Thread.currentThread().getName()+" quiere consumir.");
				Thread.sleep(50);
				
				if(transicion == 4) {
					monitor.dispararTransicion(7);
					storage2.get();
					System.out.println("El "+Thread.currentThread().getName()+" acaba de SACAR un elemento del BUFFER_2 ------ Elementos en B2: " + storage2.getCantidadDeElementos() + " [ " + time.getTiempo() + " ] ");

				} else {
					monitor.dispararTransicion(6);
					storage1.get();
					System.out.println("El "+Thread.currentThread().getName()+" acaba de SACAR un elemento del BUFFER_1 ------ Elementos en B1: "+ storage1.getCantidadDeElementos() + " [ " + time.getTiempo() + " ] ");

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			i++;
		}
		System.out.println("Termino Ejecucion; Buffer 1: " + storage1.getCantidadDeElementos() + "  Buffer 2: " + storage2.getCantidadDeElementos());
	}
}
