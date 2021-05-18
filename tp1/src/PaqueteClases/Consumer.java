/**
* Clase de modelado de Consumidores que pueden acceder al buffer.
* Cada consumidor puede extraer s�lo de a un dato.
* 
*/

package PaqueteClases;

public class Consumer implements Runnable {
	
	private Buffer storage;
	
/**
* Constructor de la clase Producer.
* @param storage El nombre del buffer de almacenamiento.
*/
	public Consumer(Buffer storage){
		this.storage=storage;
	}
	
/**
 * M�todo run() de la clase Runnable que simula la extracci�n de
 * datos del buffer mediante el m�todo get() 
 * 
 * */
	@Override
	public void run() {
		for(int i=0; i<50; i++){
			try {
				storage.get(); 
				Thread.sleep(5); //usamos este sleep para simular un tiempo de get de un elemento
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}


}
