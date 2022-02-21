/**
 * 𝑀𝑗+1 = 𝑀𝑗 + 𝐼 ∗ ((𝜎 𝑎𝑛𝑑 𝐸𝑥)#𝐴. -> ecuacion que modela la evolucion del marcado segun los disparos.
 * 𝑀𝑗+1 = 𝑀𝑗 + 𝐼 ∗ (𝜎 𝑎𝑛𝑑 (E and B)) -> asi queda al tener solo arcos especiales del tipo inhibidores.
 */
package PaqueteClases;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * Clase RdP, mantiene el control sobre todos los vectores y matrices
 * que hacen a la red de petri. Se encarga de sensibilizar, cambiar el marcado
 * desensibilizar transiciones, en general controlar que la red de petri avance.
 *
 * @version 1.0
 * @author Losano, Garcia
 */

public class RdP {
    private final int CANT_PLAZAS;
    private final int CANT_TRANSICIONES;
    private final int CANT_EVENTOS;
    private final ArrayList<Integer> transicionConInhibicion;
    private CdT controlDeTiempo;
    private final int[][] matrizH;
    private final int[][] matrizI;
    private final int[][] matrizImenos;
    private int[][] vectorQ;
    private int[][] vectorM;
    private int[][] vectorM0;
    private int[][] vectorB;
    private int[][] vectorE;
    private int[][] vectorSigma;
    private int[][] vectorEx;
    private int[][] vectorAND;
    private int evento;


    /**
     * inicializa la red de petri, haciendo uso de archivos de texto con
     * las matrices y los vectores que describen la red.
     *
     * @param CANT_PLAZAS
     * @param CANT_TRANSICIONES
     * @param CANT_EVENTOS
     */
    public RdP(int CANT_PLAZAS, int CANT_TRANSICIONES, int CANT_EVENTOS) {
        this.CANT_PLAZAS = CANT_PLAZAS;
        this.CANT_TRANSICIONES = CANT_TRANSICIONES;
        this.CANT_EVENTOS = CANT_EVENTOS;

        controlDeTiempo = new CdT();

        //para llevar una cuenta de la cantidad de eventos que arriban
        evento = 0;


        matrizH = crearMatriz("./Hinv.txt", CANT_TRANSICIONES,
                CANT_PLAZAS);
        matrizI = crearMatriz("./I.txt", CANT_PLAZAS,
                CANT_TRANSICIONES);
        matrizImenos = crearMatriz("./I-.txt", CANT_PLAZAS,
                CANT_TRANSICIONES);
        vectorM = crearMatriz("./m0.txt", CANT_PLAZAS, 1);

        vectorM0 = new int[CANT_PLAZAS][1];

        for (int i = 0; i < CANT_PLAZAS; i++)
            vectorM0[i][0] = vectorM[i][0];

        vectorE = crearMatriz("./E.txt", CANT_TRANSICIONES,
                1);
        vectorSigma = crearMatriz("./sigma.txt", CANT_TRANSICIONES
                , 1);

        transicionConInhibicion = new ArrayList<Integer>();
        vectorQ = new int[CANT_PLAZAS][1];
        vectorB = new int[CANT_TRANSICIONES][1];
        vectorEx = new int[CANT_TRANSICIONES][1];
        vectorAND = new int[CANT_TRANSICIONES][1];

        //una vez tiene todas las matrices y vectores, calcula un primer
        //sensibilizado
        calcularTransicionConInhibicion();
        calcularVectorQ();
        calcularVectorB();
        calcularVectorE();
        calcularVectorEx();

        controlDeTiempo.checkSensibilizado(vectorE);
    }

    /**
     * crea las matrices y vectores leyendolos desde archivos de texto en
     * el directorio raiz del proyecto
     *
     * @param pathMatriz
     * @param filas
     * @param columnas
     * @return la matriz o el vector que leyo
     */
    private int[][] crearMatriz(String pathMatriz, int filas, int columnas) {
        int[][] matriz = new int[filas][columnas];

        File f = new File(pathMatriz);

        try (Scanner entrada = new Scanner(f)) {
            while (entrada.hasNextInt()) {
                for (int i = 0; i < filas; i++) {
                    for (int j = 0; j < columnas; j++) {
                        matriz[i][j] = entrada.nextInt();
                        System.out.print(matriz[i][j] + " ");
                    }
                    System.out.println();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("No se encontro el archivo " + pathMatriz);
        }
        System.out.println();
        return matriz;
    }


    /**
     * guarda un arraylist con las transiciones que tienen algun arco inhibidor
     */
    private void calcularTransicionConInhibicion() {
        for (int i = 0; i < CANT_TRANSICIONES; i++) {
            for (int j = 0; j < CANT_PLAZAS; j++) {
                if (matrizH[i][j] > 0) {
                    transicionConInhibicion.add(i);
                    break;
                }
            }
        }
    }

    /**
     * calcula uno de los vectores para calcular el vector de sensibilizado
     */
    private void calcularVectorQ() {
        for (int i = 0; i < CANT_PLAZAS; i++) {
            if (vectorM[i][0] == 0) {
                vectorQ[i][0] = 1;
            } else {
                vectorQ[i][0] = 0;
            }
        }
    }

    /**
     * calcula uno de los vectores para calcular el vector de sensibilizado
     */
    private void calcularVectorB() {
        for (int i = 0; i < CANT_TRANSICIONES; i++) {
            int sumaTemp = 0;
            vectorB[i][0] = 0;
            for (int j = 0; j < CANT_PLAZAS; j++) {
                vectorB[i][0] += matrizH[i][j] * vectorQ[j][0];
                sumaTemp += matrizH[i][j];
            }
            if ((sumaTemp > 0) && (vectorB[i][0] < sumaTemp))
                vectorB[i][0] = 0;
        }
    }

    /**
     * calcula el vector de disparo segun transicion
     * @param transicion
     */
    private void calcularVectorSigma(int transicion) {
        for (int i = 0; i < CANT_TRANSICIONES; i++)
            vectorSigma[i][0] = 0;
        vectorSigma[transicion][0] = 1;
    }

    private void calcularVectorM() {
        int[][] multiplicacion = new int[CANT_PLAZAS][1];
        for (int i = 0; i < CANT_PLAZAS; i++) {
            for (int j = 0; j < CANT_TRANSICIONES; j++) {
                multiplicacion[i][0] += matrizI[i][j] * vectorAND[j][0];
            }
            vectorM[i][0] += multiplicacion[i][0];
        }
    }

    /**
     * calcula uno de los vectores para calcular el vector de sensibilizado
     */
    private void calcularVectorE() {
        for (int i = 0; i < CANT_TRANSICIONES; i++) {
            vectorE[i][0] = 1;
            for (int j = 0; j < CANT_PLAZAS; j++) {
                if (vectorM[j][0] - matrizImenos[j][i] < 0) {
                    vectorE[i][0] = 0;
                    break;
                }
            }
        }
    }

    /**
     * calcula el vector de sensibilizado
     */
    private void calcularVectorEx() {
        for (int i = 0; i < CANT_TRANSICIONES; i++)
            vectorEx[i][0] = vectorE[i][0];

        for (int conInhibicion : transicionConInhibicion)
            vectorEx[conInhibicion][0] = vectorE[conInhibicion][0]
                    & (vectorB[conInhibicion][0] >> 1);
    }

    /**
     * revisa si la cantidad de eventos ya supero los 1000 y si esto sucedio
     * verifica que el marcado vuelva al marcado inicial para terminar la
     * ejecucion, desensibiliza las transiciones que alimentan a los buffers
     * si estos estan llenos
     *
     * @return 1 si no debe terminar la ejecucion, -1 si si debe
     */
    private int checkCantEventos() {

        /*
         * limite de 10 elementos para cada buffer
         */

        if (vectorM[11][0] >= 10) //buffer cpu1
            vectorE[10][0] = 0;
        if (vectorM[6][0] >= 10) //buffer cpu2
            vectorE[11][0] = 0;

        /*
         * Si ya se supera la cantidad de eventos desensibiliza T0
         * y si el vector de marcado es igual al de marcado inicial
         * significa que termino la ejecucion.
         */
        if (evento == CANT_EVENTOS) {
            vectorE[12][0] = 0;
            for (int i = 0; i < CANT_PLAZAS; i++) {
                if (vectorM[i][0] != vectorM0[i][0])
                    break;
                if (i >= CANT_PLAZAS - 1)
                    return -1;
            }
            return 1;
        }
        return 1;
    }

    /**
     * intenta disparar transicion de la red de petri
     *
     * @param transicion
     * @return 0 si no se pudo disparar la transicion, 1 si pudo disparar,
     *  > 1 si la transicion que intenta disparar es con tiempo y se encuentra
     *  antes del alpha
     */
    public int disparar(int transicion) {
        short AND = 0;
        int disparo = 0; // mayor a 1 espera;
        // 1 dispara; 0 no dispara; -1 dispara y termina;

        calcularVectorSigma(transicion);

        for (int i = 0; i < CANT_TRANSICIONES; i++) {
            vectorAND[i][0] = vectorSigma[i][0] & vectorEx[i][0];
            AND |= vectorAND[i][0];
        }
        if (AND > 0) {

            disparo = controlDeTiempo.tryDispararConTiempo(transicion);
            if (disparo > 1) {
                return disparo;
            }

            System.out.println("El " + Thread.currentThread().getName()
                    + " Disparo: " + matchTransicion(transicion));

            //disparo
            calcularVectorM();

            for (int i = 0; i < CANT_PLAZAS; i++) {
                System.out.print(vectorM[i][0] + " ");
            }
            System.out.println();

            //calculo nuevos vectores segun el cambio de marcado
            calcularVectorE();
            controlDeTiempo.checkSensibilizado(vectorE);

            //si ya se generaron la cantidad de eventos
            disparo = checkCantEventos();

            calcularVectorQ();
            calcularVectorB();
            calcularVectorEx();

            if (transicion == 12) {
                evento++;
                System.out.println("Eventos: " + evento);
            }

            if (transicion == 10 || transicion == 8) {
                System.out.println("Elementos en el buffer-1: "
                        + vectorM[11][0]);
                //log.printElementosEnBuffer();
            } else if (transicion == 11 || transicion == 3) {
                System.out.println("Elementos en el buffer-2: "
                        + vectorM[6][0]);
            }


            return disparo;
        }
        return disparo;
    }

    /**
     *
     * @return vector de sensibilizado extendido
     */
    public int[][] getSensibilizadas() {
        return vectorEx;
    }

    /**
     *
     * @return vector de marcado de la red
     */
    public int[][] getVectorM() {
        return vectorM;
    }

    /**
     *
     * matchea el numero de transicion segun la matriz y devuelve el numero
     * de transicion que se ve en el diagrama de la red de petri.
     *
     * @param transicion
     * @return numero de transicion entendible segun RdP
     */
    public int matchTransicion(int transicion) {
        return controlDeTiempo.matchTransicion(transicion);
    }
}
/**
 * 0 t2
 * 1 t3
 * 2 t4
 * 3 t5
 * 4 t7
 * 5 t1
 * 6 t0
 * 7 t6
 */