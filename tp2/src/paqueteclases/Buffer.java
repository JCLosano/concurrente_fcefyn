package paqueteclases;

import java.util.LinkedList;

public class Buffer {
	private LinkedList<Object> storage;	
	private RdP rdp;
	private int tope;
	
	/**
	 * Constructor de la clase
	 * */
	public Buffer(RdP rdp, int tope) {
		this.rdp = rdp;
		storage = new LinkedList<>();
		this.tope = tope;
	}
	
	/**
	 * Metodo que devuelve el tamaño del buffer
	 * @return elementos en el buffer
	 * */
	public int getCantidadDeElementos() {
		//return storage.size();
		return rdp.getCantidadElementos(tope); // de esta forma podemos ver correctamente porque se bloquean los hilos, ahora si podemos comprenderlo y ver que el programa funciona correctamente.
	}
	
	/**
	 * Metodo que coloca un elemento en el buffer. 
	 * Usado por el Productor
	 * */
	synchronized public void set() {
		storage.add(new Object());
	}
	 
	 /**
	  * Metodo que saca un elemento del buffer. 
	  * Usado por el Consumidor
	  * */
	synchronized public void get(){
		storage.poll();
	}
}
