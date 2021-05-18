package PaqueteClases;

public class RdP {
	private boolean k;
	private VectorSensibilizadas vectorSensibilizadas;
	private VectorDeEstado vectorDeEstado;
	private Matriz marcadoActual;
	private Matriz incidencia;
	//private Matriz vectorSensibilizado;
	//private Matriz contadorDisparo;
	private Matriz inhibidores;
	private Matriz timer;
	private Matriz inhibidosPorTiempo;
	private Matriz trans_inhibidas;
	private Matriz sensibilizadoExtendido;
	private Matriz prioridades;
	private Matriz Q;
	
	public boolean disparar(int transicion) {
		this.k = vectorSensibilizadas.estaSensibilizado(transicion);
		return k;
	}
	
	public void calculoDeVectorEstado() {	// falta ver como obtener el valor de k
		if(k) {
			vectorDeEstado.calculoDeVectorEstado(0);
		} else {
			k=false;
		}
	}

	
}
