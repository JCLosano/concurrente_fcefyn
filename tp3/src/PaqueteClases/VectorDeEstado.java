package PaqueteClases;

public class VectorDeEstado {
	private Matriz matrizI;
	private SensibilizadoConTiempo sensibilizadoConTiempo;
	private VectorSensibilizadas vectorSensibilizadas;
	private int[] vS;
	
	public VectorDeEstado(){
		
	}
	public void calculoDeVectorEstado(int columna) {
		sensibilizadoConTiempo.resetEsperando();
		matrizI.getColumna();
		//estado=estado+columna;
		int transiciones=15;
		while(columna<transiciones) {
			columna=matrizI.getColumna();
			vS=vectorSensibilizadas.actualizarSensibilizadoT();
			//setear nuevo timestamp
			columna++;
		}
	}
}
