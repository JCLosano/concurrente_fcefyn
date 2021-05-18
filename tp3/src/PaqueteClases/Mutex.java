package PaqueteClases;

import java.util.concurrent.Semaphore;

public class Mutex {
	private Semaphore mutex;
	
	public Mutex(){
		mutex = new Semaphore(1,true);
	}
	public void release() {
		mutex.release();
	}
	public void acquire() {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
