/**
* Clase de modelado de Productores que pueden acceder al buffer.
* 
*/
package PaqueteClases;

public class Producer implements Runnable {
	
	private Buffer storage;
	
/**
* Constructor de la clase Producer.
* @param storage El nombre del buffer de almacenamiento.
*/
	public Producer(Buffer storage){
		this.storage=storage;
	}

/**
* Método run() de la clase Runnable que simula la entrada de
* datos al buffer mediante el método set() 
* */
	@Override
	public void run() {
		for(int i=0; i<50; i++) {
			try {
				storage.set();
				Thread.sleep(5); //usamos este sleep para simular un tiempo de seteo de un elemento
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
		}
	}

}