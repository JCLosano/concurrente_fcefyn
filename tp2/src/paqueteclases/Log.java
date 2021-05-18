package paqueteclases;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import paqueteclases.Buffer;

public class Log implements Runnable{

	private Thread Tconsumers[];
	private Buffer storage1,storage2;
	private final int MAXCons;
	private TiempoActual time;

	public Log(Thread Tconsumers[], Buffer storage1,Buffer storage2,int MAXCons) {
		this.Tconsumers= Tconsumers;
		this.storage1=storage1;
		this.storage2=storage2;
		this.MAXCons=MAXCons;		
		time = new TiempoActual();
	}
	
	@Override
	public void run() {
		//creamos el log.txt y escribimos en él cada 2 segundos
		try (FileWriter file = new FileWriter("C:\\Users\\bruno\\Desktop\\Programación Concurrente\\TP2\\log.txt"); PrintWriter pw = new PrintWriter(file);) {
			while(true) {
				for (int i = 0; i < MAXCons; i++) {
					writeThreadInfo(pw, Tconsumers[i]); //escribe en el archivo log.txt
				}
				Thread.sleep(2000);
				pw.println("");
				pw.println("");
				pw.flush();//lo usamos por el sleep
			}
		} catch (IOException |InterruptedException e ) {
			e.printStackTrace();
		}
	}
	
	private void writeThreadInfo(PrintWriter pw, Thread thread) {
		pw.println(" [ " + time.getTiempo() + " ] ");
		pw.println("  Id: " + thread.getName());
		pw.println("  State: " + thread.getState());
		pw.println("  Storage1 size: " + storage1.getCantidadDeElementos());
		pw.println("  Storage2 size: " + storage2.getCantidadDeElementos());
		pw.println("  ************************************\n");
	}
}

