package paqueteclases;

/**
 * Esta clase realiza una ejecución con todos los productores trabajando
 * concurrentemente, donde cada productor inserta 10.000 productos.
 * */
public class Producer implements Runnable {
	
	private GestorDelMonitor monitor;
	private Buffer storage1, storage2;
	private int i=0;
	private int	transicion;
	private final int MAXProduccion=10000;
	private TiempoActual time;

	/**
	 * Constructor de la clase
	 * @param monitor GestorDelMonitor
	 * @param storage1 Buffer
	 * @param storage2 Buffer
	 * */
	public Producer(GestorDelMonitor monitor, Buffer storage1, Buffer storage2) {
		this.monitor=monitor;
		this.storage1=storage1;
		this.storage2=storage2;
		time = new TiempoActual();
	}

	@Override
	public void run () {
		 while(i<MAXProduccion) {
			 
			try {
				if (storage1.getCantidadDeElementos() >= storage2.getCantidadDeElementos()) {
					monitor.dispararTransicion(1);
					transicion=1;
				} else {
					monitor.dispararTransicion(0);
					transicion=0;
				}
				//System.out.println("El productor: " + Thread.currentThread().getName()+" quiere producir.");
				Thread.sleep(50); 
				
				if(transicion == 0) {
					monitor.dispararTransicion(2);
					storage1.set();
					System.out.println("El "+Thread.currentThread().getName()+" acaba de PONER un elemento del BUFFER_1 ++++++ Elementos en B1: " + storage1.getCantidadDeElementos() + " [ " + time.getTiempo() + " ] ");		

				} else {
					monitor.dispararTransicion(3);
					storage2.set();
					System.out.println("El "+Thread.currentThread().getName()+" acaba de PONER un elemento del BUFFER_2 ++++++ Elementos en B2: " + storage2.getCantidadDeElementos() + " [ " + time.getTiempo() + " ] ");
				
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			
			//System.out.println("Productos: " + i);
			i++;
		}
		System.out.println("/////////////// Terminamos de producir ///////////////");
	}
}