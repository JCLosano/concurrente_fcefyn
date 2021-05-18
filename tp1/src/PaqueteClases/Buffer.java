/**
* Clase de modelado de un Buffer cuya capacidad son 10 lugares.
* 
*/

package PaqueteClases;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ThreadLocalRandom;

public class Buffer {
	private final static int maxSize = 10;
	private LinkedList<Object> storage;
	private final ReentrantLock lock=new ReentrantLock();
	
/**
* Constructor de la clase Buffer.
*/
	public Buffer(){
		storage=new LinkedList<>();
	}
	
/**
 * M�todo dedicado para el productor, cuya funci�n es de sacar
 * elementos del Buffer.
 * Este buffer tiene pura p�rdida, es decir que si est� lleno, el 
 * productor descarta los datos.
 * 
 */
	public void set() { // Productor
		System.out.println(Thread.currentThread().getName() + ": Acquiring the lock");
		lock.lock();
		System.out.println(Thread.currentThread().getName() + ": lock acquired");
		if (!(storage.size() == maxSize)) //si no esta lleno
			{
		
			storage.add(new Object()); //agrego un objeto al buffer.
			System.out.println(Thread.currentThread().getName() + ": Putting the object " + storage.get(storage.size()-1));
			System.out.println("Space:" + (maxSize - storage.size())); //imprimo el espacio disponible en el buffer.
		} 
		else {
			System.out.println("LLENO");
		}
		System.out.println(Thread.currentThread().getName() + ": Releasing the lock");	
		lock.unlock();
		System.out.println(Thread.currentThread().getName() + ": lock released");
	}
	
	public void get() { // Consumidor
		System.out.println(Thread.currentThread().getName() + ": Acquiring the lock");
		lock.lock();
		System.out.println(Thread.currentThread().getName() + ": lock acquired");
		if (!(storage.isEmpty())) //si no esta vacio
		{			
			try {
				int randomInt = ThreadLocalRandom.current().nextInt(1, 10)*100; //Thread que busca un n�mero random entre 1 y 10
				System.out.printf(Thread.currentThread().getName() + ": Processing Object during %d miliseconds\n",(randomInt));
				Thread.sleep(randomInt); //duermo el hilo del consumidor un tiempo random entre alfa y beta.
				System.out.println(Thread.currentThread().getName() + ": Consuming object " + storage.get(0));
				storage.poll(); //quito el primer elemento (cabeza) del buffer haciendolo una lista de tipo cola.
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("Space:" + (maxSize-storage.size())); //imprimo el espacio disponible en el buffer
				}	
			}
			else {
				System.out.println("VACIO");
			}
		System.out.println(Thread.currentThread().getName() + ": Releasing the lock");
		lock.unlock();
		System.out.println(Thread.currentThread().getName() + ": lock released");
	}
	
	public int getSize()
	{
		return storage.size();	
	}
	}
	

