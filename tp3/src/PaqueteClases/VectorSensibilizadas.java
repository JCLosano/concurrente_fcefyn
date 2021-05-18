package PaqueteClases;

import java.util.concurrent.Semaphore;

public class VectorSensibilizadas {
	private SensibilizadoConTiempo sensibilizadoConTiempo;
	private boolean ventana;
	private boolean esperando;
	private boolean k;
	private boolean antes;
	private Mutex mutex;
	
	public VectorSensibilizadas() {
	sensibilizadoConTiempo = new SensibilizadoConTiempo();
	ventana = true;
	esperando = true;
	k = true;
	antes = true;
	}
	
	public boolean estaSensibilizado(int transicion) {
		ventana = sensibilizadoConTiempo.testVentanaTiempo();
		if(ventana==true) {
			if(esperando==false) {
				sensibilizadoConTiempo.setNuevoTimeStamp();
				k = true;
				return k;
			}
			else {
				k=false;
				return k;
			}
		}
		else {
			//sensibilizadoConTiempo.antesDeLaVentana();
			mutex.release();
			if((sensibilizadoConTiempo.antesDeLaVentana())) {
				sensibilizadoConTiempo.setEsperando();
				//sleep(timeStamp+alfa-ahora);
			}
			else {
				k=false;
				return k;
			}
		}
		return k;
	}
	
	public int[] actualizarSensibilizadoT() {
		sensibilizadoConTiempo.setNuevoTimeStamp();
		return null;
		
	}

	public int[] getVectorSensibilizado() {
		return null;
	}
	
}
