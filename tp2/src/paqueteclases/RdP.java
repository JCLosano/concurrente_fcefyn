package paqueteclases;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Esta clase se ocupa de la Red de Petri que se modeló, y se almacenan
 * en arreglos/matrices los documentos de texto que se crearon previamente
 * en base a dicha red.
 * */
public class RdP {
	private int[][] incidencia;
	private int[] 	marcadoActual;
	private int 	Produc, Produce_afuera1, Produce_afuera2, 
					B2_Espacios, B2_Elementos, B1_Espacios,
					B1_Elementos, Consum, Consume_afuera1,
					Consume_afuera2;
	
	/**
	  * Constructor de la clase
	  * @param storage1 Buffer
	  * @param storage2 Buffer
	  * */
	public RdP() {
		cargarMatrizIncidencia("Matriz_Incidencia.txt");
		cargarMarcadoActual("Matriz_marcado.txt");
		inicializacion();
	}
	
	/**
	  * Método que pone los valores que tenga el marcado actual
	  * en cada plaza de la Red de Petri.
	  * @param marcadoActual int[]
	  * */
	private void inicializacion() {
		Produc 			= marcadoActual[0];
		Produce_afuera1 = marcadoActual[1];
		Produce_afuera2 = marcadoActual[2];
		B2_Espacios 	= marcadoActual[3];
		B2_Elementos    = marcadoActual[4];
		B1_Espacios 	= marcadoActual[5];
		B1_Elementos 	= marcadoActual[6];
		Consum			= marcadoActual[7];
		Consume_afuera1 = marcadoActual[8];
		Consume_afuera2 = marcadoActual[9];
	}
	
	/**
	  * Método que dispara las transiciones que estés disponibles
	  * @param transicion int
	  * */
	public Boolean disparar(int transicion){
		
		int[] vectorS = sensibilizadas();
		if(vectorS[transicion] == 0) return false; // NO PUEDE DISPARAR PORQUE NO ESTÁ SENSIBILIZADA
		
		for(int i=0 ; i<getCantidadPlazas() ; i++){
				marcadoActual[i] = marcadoActual[i] + incidencia[i][transicion];
		}
		inicializacion();

		return true; // PUEDE DISPARAR PORQUE SI ESTÁ SENSIBILIZADA
	}
	
	/**
	  * Método que analiza las plazas que tengan tokens y que coloca un 1
	  * en la transicion que corresponda, representada por el vectorS
	  * @return int[]
	  * */	
	public int[] sensibilizadas(){
		
		/*int[] vectorS = new int[] {1,1,1,1,1,1,1,1};
		for(int j=0 ; j<getCantidadTransiciones() ; j++) {
			for(int i=0 ; i<getCantidadPlazas() ; i++) {
				// Vemos cuales plazas van a una transicion en particular, y si esa plazas tienen 0 token.
				if ( (( marcadoActual[i] - 1) < 0 ) && ( incidencia[i][j] < 0 ) ) {
					vectorS[j]=0; // NO ESTÁ SENSIBILIZADA
				}
			}
		}*/
		
		int[] vectorS = new int[] {0,0,0,0,0,0,0,0};
		inicializacion();
		if(Produc>0 && B1_Espacios>0)
			vectorS[0]=1; //P_sacaEspacioB1	sensibilizada
		if(Produc>0 && B2_Espacios>0)
			vectorS[1]=1; //P_sacaEspacioB2 sensibilizada
		if(Produce_afuera1>0)
			vectorS[2]=1; //P_poneElemB1 sensibilizada
		if(Produce_afuera2>0)
			vectorS[3]=1; //P_poneElemB2 sensibilizada
		if(B2_Elementos>0 && Consum>0)
			vectorS[4]=1; //C_sacaElemB2 sensibilizada
		if(B1_Elementos>0 && Consum>0)
			vectorS[5]=1; //C_sacaElemB1 sensibilizada
		if(Consume_afuera1>0)
			vectorS[6]=1; //C_poneEspacioB1 sensibilizada
		if(Consume_afuera2>0)
			vectorS[7]=1; //C_poneEspacioB2 sensibilizada

		return vectorS;
	}
	
	/**
	  * Método lector del archivo de texto de la Matriz de Incidencia
	  * @param file_name String Nombre del archivo
	  * */	
	private void cargarMatrizIncidencia(String file_name) {
		int cant_trans = 8;
		int cant_plazas = 10;
		incidencia = new int[cant_plazas][cant_trans];
        try{            
            FileInputStream fstream = new FileInputStream(file_name);	// Abrimos el archivo            
            DataInputStream entrada = new DataInputStream(fstream);		// Creamos el objeto de entrada 
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));		// Creamos el Buffer de Lectura
            String strLinea;
            int j=0, pos;
            // Leer el archivo linea por linea	
            while ((strLinea = buffer.readLine()) != null) {
            	String [] linea = strLinea.split(" ");
            	pos = 0;
            	for(int i=0;i<cant_trans;i++){
            		incidencia[j][pos] = Integer.parseInt(linea[pos]);
            		pos++;
            	}
            	j++;	
            }
            entrada.close();	// Cerramos el archivo
        }catch (Exception e){ //Catch de excepciones
            System.err.println("Ocurrio un error: " + e.getMessage());
        }
    }
	
	/**
	  * Método lector del archivo de texto del Vector de Marcado
	  * @param file_name String Nombre del archivo
	  * */
	private void cargarMarcadoActual(String file_name) {
		int cant_plazas = 10;
		marcadoActual = new int[cant_plazas];
		try{            
            FileInputStream fstream = new FileInputStream(file_name);	// Abrimos el archivo            
            DataInputStream entrada = new DataInputStream(fstream);		// Creamos el objeto de entrada 
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));		// Creamos el Buffer de Lectura
            String strLinea;
            int j=0;
            // Leer el archivo linea por linea	
            while ((strLinea = buffer.readLine()) != null)   {
            	String [] linea = strLinea.split(" ");  //Separamos la linea por cada espacio y lo guardamos en un arreglo
            	for(j=0;j<cant_plazas;j++){
            		marcadoActual[j] = Integer.parseInt(linea[j]);
            	}
            }	
            entrada.close();	// Cerramos el archivo
        } catch (Exception e) { //Catch de excepciones
            System.err.println("Ocurrio un error: " + e.getMessage());
        }
	}
	
	/**
	  * Método que devuelve la cantidad de plazas que tiene la Red de Petri
	  * @return int
	  * */
	public int getCantidadPlazas(){
		return incidencia.length;
	}
	 
	/**
	  * Método que devuelve la cantidad de transiciones que tiene la Red de Petri
	  * @return int
	  * */
	public int getCantidadTransiciones() {
		return incidencia[0].length;
	}
	
	public int getCantidadElementos(int buffer) {
		if(buffer == 15) {
			return B2_Elementos;
		}
		if(buffer == 10) {
			return B1_Elementos;
		}
		return 0;
	}
}
