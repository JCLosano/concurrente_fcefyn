package PaqueteClases;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Matriz {
	private int[][] incidencia;
	private int[] 	marcadoActual;
	
	public Matriz() {
		matrizIncidencia("Matriz_Incidencia.txt");
		marcadoActual("Matriz_marcado.txt");
	}
	
	public void matrizIncidencia(String file_name) {
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
	public void marcadoActual (String file_name) {
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
        }catch (Exception e){ //Catch de excepciones
            System.err.println("Ocurrio un error: " + e.getMessage());
        }
	}
	public void marcadoInicial () {
		
	}
	
	public int getColumna() {
		return 2;
	}
}
