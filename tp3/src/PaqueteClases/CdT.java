package PaqueteClases;

import java.time.Duration;
import java.time.Instant;

/**
 *
 * Clase Control de tiempo, se encarga de controlar el tiempo de las
 * transiciones que se sensibilizan en una ventana de tiempo.
 *
 * @version 1.0
 * @author Losano, Garcia
 */

public class CdT {

    private final int[][] transicionesConTiempo;
    private final int CANT_TRANSICIONES_CON_TIEMPO;
    private Instant[][] instanteSensibilizado;
    private int[][] flagSensibilizado;
    private final int ALPHA_ARRIBO = 20;
    private final int ALPHA_SERVICIO1 = 60;
    private final int ALPHA_SERVICIO2 = 60 * 1;
    private final int BETA_ARRIBO = ALPHA_ARRIBO * 100;
    private final int BETA_SERVICIO1 = ALPHA_SERVICIO1 * 100;
    private final int BETA_SERVICIO2 = ALPHA_SERVICIO2 * 100;

    /**
     * inicializa el control de tiempo de las transiciones con tiempo
     */
    public CdT() {

        CANT_TRANSICIONES_CON_TIEMPO = 3;
        transicionesConTiempo = new int[CANT_TRANSICIONES_CON_TIEMPO][1];
        instanteSensibilizado = new Instant[CANT_TRANSICIONES_CON_TIEMPO][1];

        flagSensibilizado = new int[CANT_TRANSICIONES_CON_TIEMPO][1];

        transicionesConTiempo[0][0] = 12;
        transicionesConTiempo[1][0] = 13;
        transicionesConTiempo[2][0] = 14;

        for (int i = 0; i < CANT_TRANSICIONES_CON_TIEMPO; i++) {
            instanteSensibilizado[i][0] = Instant.now();
        }
       // flagSensibilizado[0][0] = 1;
    }

    /**
     * verifica si vectorE contiene una transicion con tiempo y toma el
     * instante de tiempo en el cual se sensibilizo por token
     *
     * @param vectorE
     */
    public void checkSensibilizado(int[][] vectorE) {
        for (int i = 0; i < CANT_TRANSICIONES_CON_TIEMPO; i++) {
            if (vectorE[transicionesConTiempo[i][0]][0] == 1
                    && flagSensibilizado[i][0] == 0) {
                instanteSensibilizado[i][0] = Instant.now();
                flagSensibilizado[i][0] = 1;
            }
        }
    }

    /**
     * intenta disparar transicion con tiempo, si es con tiempo calcula si el
     * instante de disparo esta antes, dentro o despues de la ventana.
     * Produce un system.exit() si se encuentra despues.
     *
     * @param transicion
     * @return 0 si transicion es sin tiempo, 1 si es con tiempo y puede
     * dispararla, > 1 si es con tiempo y se encuentra antes del alpha
     */
    public int tryDispararConTiempo(int transicion){

        Instant instanteDisparo = Instant.now();
        Instant instante1 = Instant.now();
        Instant instante2 = Instant.now();
        int auxFlagSensibilizado = -1;

        if (transicion == transicionesConTiempo[0][0]) {
            instante1 = instanteSensibilizado
                    [0][0].plusMillis
                    (ALPHA_ARRIBO);
            instante2 = instanteSensibilizado
                    [0][0].plusMillis
                    (BETA_ARRIBO);
            auxFlagSensibilizado = 0;
        } else if (transicion == transicionesConTiempo[1][0]) {
            instante1 = instanteSensibilizado
                    [1][0].plusMillis
                    (ALPHA_SERVICIO1);
            instante2 = instanteSensibilizado
                    [1][0].plusMillis
                    (BETA_SERVICIO1);
            auxFlagSensibilizado = 1;
        } else if (transicion == transicionesConTiempo[2][0]) {
            instante1 = instanteSensibilizado
                    [2][0].plusMillis
                    (ALPHA_SERVICIO2);
            instante2 = instanteSensibilizado
                    [2][0].plusMillis
                    (BETA_SERVICIO2);
            auxFlagSensibilizado = 2;
        }
        else {
            return 0;
        }

        if (instanteDisparo.isBefore(instante1)) {
            Duration cuantoFalta = Duration.between(instanteDisparo, instante1);
            System.out.println("Falta: " + (int) cuantoFalta.toMillis()
                    + " para alpha");
            return (int) cuantoFalta.toMillis();
        } else if (instanteDisparo.isAfter(instante2)) {
            System.out.println("Mucho tiempo de espera "
                               + matchTransicion(transicion));
            System.exit(1);
        }
        flagSensibilizado[auxFlagSensibilizado][0] = 0;
        return 1;
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
        switch (transicion) {
            case 0:
                return 9;
            case 1:
                return 11;
            case 2:
                return 10;
            case 3:
                return 12;
            case 4:
                return 14;
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 4;
            case 8:
                return 5;
            case 9:
                return 7;
            case 10:
                return 1;
            case 11:
                return 8;
            case 12:
                return 0;
            case 13:
                return 6;
            case 14:
                return 13;
            default:
                return -1; //ERROR
        }
    }
}
