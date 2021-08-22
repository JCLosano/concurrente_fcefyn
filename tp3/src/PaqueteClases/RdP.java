/**
 * 𝑀𝑗+1 = 𝑀𝑗 + 𝐼 ∗ ((𝜎 𝑎𝑛𝑑 𝐸𝑥)#𝐴. -> ecuacion que modela la evolucion del marcado segun los disparo.
 * 𝑀𝑗+1 = 𝑀𝑗 + 𝐼 ∗ (𝜎 𝑎𝑛𝑑 B) -> asi queda al tener solo arcos especiales del tipo inhibidores.
 */
package PaqueteClases;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RdP {
    private final int CANT_PLAZAS;
    private final int CANT_TRANSICIONES;
    private int[][] matrizH;
    private int[][] matrizI;
    private int[][] vectorQ;
    private int[][] vectorM;
    private int[][] vectorB;
    private int[][] vectorSigma;

    public RdP (int CANT_PLAZAS, int CANT_TRANSICIONES) {
        this.CANT_PLAZAS = CANT_PLAZAS;
        this.CANT_TRANSICIONES = CANT_TRANSICIONES;

        matrizH = crearMatriz("./Hinv.txt", CANT_TRANSICIONES,
                              CANT_PLAZAS);
        matrizI = crearMatriz("./I.txt", CANT_PLAZAS,
                              CANT_TRANSICIONES);
        vectorM = crearMatriz("./m0.txt", CANT_PLAZAS, 1);
        vectorSigma = crearMatriz("./sigma.txt", 1,
                                  CANT_TRANSICIONES);
        vectorQ = calcularVectorQ();
    }

    private int[][] crearMatriz(String pathMatriz, int filas, int columnas) {
        int[][] matriz = new int[filas][columnas];

        File f = new File(pathMatriz);

        try (Scanner entrada = new Scanner(f)) {
            while (entrada.hasNextInt()) {
                for(int i = 0; i < filas; i++){
                    for(int j = 0; j < columnas; j++){
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

    private int[][] calcularVectorQ(){
        int[][] vector = new int[CANT_PLAZAS][1];
        for(int i = 0; i < CANT_PLAZAS; i++){
            if(vectorM[i][0] == 0) {
                vector[i][0] = 1;
            } else {
                vector[i][0] = 0;
            }
            System.out.println(vector[i][0]);
        }
        return vector;
    }
}
