package PaqueteClases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * Clase log, se encarga de mantener un log.txt y un t_invariantes.txt donde
 * guardara cada disparo exitoso de la red de petri.
 *
 * @version 1.0
 * @author Losano, Garcia
 */

public class Log {

    private String t;
    private String disparos;
    private RdP rdp;
    private File file1 = new File("./log.txt");
    private File file2 = new File("./t_invariante.txt");

    private FileWriter fw1;
    private FileWriter fw2;

    private BufferedWriter bw1;
    private BufferedWriter bw2;

    public Log (RdP rdp) {
        //TODO
        this.rdp = rdp;
        try {
            if (!file1.exists())
                file1.createNewFile();
            if (!file2.exists())
                file2.createNewFile();

            fw1 = new FileWriter(file1);
            fw2 = new FileWriter(file2);

            bw1 = new BufferedWriter(fw1);
            bw2 = new BufferedWriter(fw2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void intentaDisparar(int transicion) {
        disparos = "El Thread: " + Thread.currentThread().getName()
                + " intenta disparar T" + transicion + "\n";
        disparos = disparos.replace("null", "");
        try {
            bw1.write(disparos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disparo(int transicion) {
        disparos = "Se disparo T" + transicion + "\n";

        if (transicion != 1)
            t = "T" + transicion;
        else
            t = "Tu";


        disparos = disparos.replace("null","");
        t = t.replace("null","");

        try {
            bw1.write(disparos);
            bw2.write(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void noDisparo(int transicion) {
        disparos = "No se disparo T" + transicion + "\n";
        disparos = disparos.replace("null","");
        try {
            bw1.write(disparos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void despiertoA(int transicion) {
        disparos = "El thread: " + Thread.currentThread().getName()
                + " despierta al thread en T" + transicion + "\n";
        try {
            bw1.write(disparos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void seBloquea(int transicion) {
        disparos = "El thread: " + Thread.currentThread().getName()
                + " se bloquea en T" + transicion +"\n";
        try {
            bw1.write(disparos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void archivo() {
        try {
            bw1.close();
            bw2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
