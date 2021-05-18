package PaqueteClases;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Log implements Runnable{
	
	private Thread Tconsumers[];
	private Buffer	storage;

	public Log(Thread Tconsumers[], Buffer storage) {
		this.Tconsumers= Tconsumers;
		this.storage=storage;
			
	}
	
	@Override
	public void run() {
		//creamos el log.txt y escribimos en él cada 2 segundos
		try (FileWriter file = new FileWriter("C:\\Users\\Daniela\\Desktop\\log.txt"); PrintWriter pw = new PrintWriter(file);) {
			for(int j=0;j<50;j++) {
			for (int i = 0; i < 2; i++) {
				writeThreadInfo(pw, Tconsumers[i], storage); //escribe en el archivo log.txt
			}
			System.out.println("ESCRIBE");
			Thread.sleep(2000);
			pw.println("");
			pw.println("");
			pw.flush();//lo usamos por el sleep
			}
		} catch (IOException |InterruptedException e ) {
			e.printStackTrace();
		}
		}
	
	private void writeThreadInfo(PrintWriter pw, Thread thread,Buffer storage) {
		pw.println("  Id: " + thread.getName());
		pw.println("  State: " + thread.getState());
		pw.println("  Storage size: " + storage.getSize());
		pw.println("  ************************************\n");
	}
}
