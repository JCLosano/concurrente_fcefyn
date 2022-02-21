package PaqueteClases;

import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;


/**
 *
 * Clase monitor, se encarga de controlar los disparos de la
 * red de petri, que estos disparos sean de manera ordenada
 * y proteger la seccion critica haciendo uso de un Semaphore.
 *
 * @version 1.0
 * @author Losano, Garcia
 */
public class Monitor {
    private final int CANT_PLAZAS;
    private final int CANT_TRANSICIONES;
    private final int CANT_EVENTOS;
    private static Semaphore mutex;
    private RdP rdp;
    private Colas colas;
    private Politica politica;
    private Log log;

    private int[][] vectorEncolados;
    private int[][] vectorEx;
    private int[][] vectorM;
    private boolean terminando;


    /**
     * inicializa el monitor.
     *
     * @param CANT_PLAZAS
     * @param CANT_TRANSICIONES
     * @param CANT_EVENTOS
     */
    public Monitor(int CANT_PLAZAS, int CANT_TRANSICIONES, int CANT_EVENTOS) {
        this.CANT_PLAZAS = CANT_PLAZAS;
        this.CANT_TRANSICIONES = CANT_TRANSICIONES;
        this.CANT_EVENTOS = CANT_EVENTOS;

        terminando = false;

        rdp = new RdP(CANT_PLAZAS, CANT_TRANSICIONES, CANT_EVENTOS);
        politica = new Politica(CANT_TRANSICIONES);
        mutex = new Semaphore(1);
        colas = new Colas(CANT_TRANSICIONES);
        log = new Log(rdp);

        vectorEncolados = new int[CANT_TRANSICIONES][1];
        vectorEx = new int[CANT_TRANSICIONES][1];
        vectorM = new int[CANT_PLAZAS][1];

    }

    /**
     *
     * chequea que se cumplan los invariantes de plaza
     *
     * @return true si cumple el chequeo de invariantes, false si no cumple
     */
    private boolean checkInvariantePlaza() {
        vectorM = rdp.getVectorM();
        short invariante = 1;

        if (vectorM[0][0] + vectorM[15][0] != invariante)
            return false;
        if (vectorM[1][0] + vectorM[3][0] + vectorM[4][0] != invariante)
            return false;
        if (vectorM[5][0] + vectorM[7][0] != invariante)
            return false;
        if (vectorM[8][0] + vectorM[10][0] + vectorM[14][0] != invariante)
            return false;
        if (vectorM[12][0] + vectorM[13][0] != invariante)
            return false;

        return true;
    }

    /**
     *
     * trata de disparar transicion de la red de petri, esto lo hace en
     * exclusion mutua haciendo uso de un semaphore(1). Puede disparar
     * transiciones con tiempo tambien. senaliza hilos que se pueden disparar
     * y estan esperando.
     *
     * @param transicion
     * @return -1 si el hilo debe terminar, 0 si el hilo debe disparar otra
     * transicion
     * @throws InterruptedException
     */
    public int disparar(int transicion) throws InterruptedException {
        //inicio de la seccion critica
        mutex.acquire();

        int disparo = 0;
        int senalizado = 0;

        while (true) {

            // para los print, para entender
            int t = matchTransicion(transicion);
            System.out.println("El " + Thread.currentThread().getName() +
                    " intenta disparar transicion " +
                    t);

            disparo = rdp.disparar(transicion);

            if (disparo > 1) {
                mutex.release();
                // El thread se va a dormir lo que le falta para llegar al alpha
                try {
                    System.out.println("El " + Thread.currentThread().getName()
                            + " se va a dormir " + disparo + " mseg" );
                    sleep(disparo);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mutex.acquire();
            }
            else if (disparo == 1) {
                if (!checkInvariantePlaza()) {
                    System.out.println("ErrorPlaceInv");
                    System.exit(1);
                }

                log.disparo(t);

                //senalizar
                vectorEncolados = colas.getEncolados();
                vectorEx = rdp.getSensibilizadas();
                senalizado = politica.cualDespierto(vectorEncolados, vectorEx
                                                    , vectorM);

                System.out.print("Ex: ");
                for (int i = 0; i < CANT_TRANSICIONES; i++) {
                    System.out.print(vectorEx[i][0]+ " ");
                }
                System.out.println();
                System.out.print("En: ");
                for (int i = 0; i < CANT_TRANSICIONES; i++) {
                    System.out.print(vectorEncolados[i][0]+ " ");
                }
                System.out.println();

                if (senalizado != -1) {
                    System.out.println("Despierto a: "
                                       + matchTransicion(senalizado));
                    log.despiertoA(rdp.matchTransicion(senalizado));
                    colas.notify(senalizado);
                }
                mutex.release();
                break;

            } else if (disparo == 0) {
                System.out.println("No se disparo " + t);
                log.noDisparo(t);
                log.seBloquea(t);

                mutex.release(); //sacar este para conditions
                System.out.println("SE ESTA ENCOLANDO " + t);
                colas.acquire(transicion);
                if (terminando == true)
                    return -1;
                mutex.acquire();

            } else if (disparo == -1) {
                log.disparo(t);
                terminando = true;

                colas.allNotify();
                mutex.release();
                return -1;
            }
        }
        return 0;
    }

    /**
     *
     * matchea el numero de transicion segun la matriz y devuelve el numero
     * de transicion que se ve en el diagrama de la red de petri.
     *
     * @param transicion
     * @return numero de transicion entendible segun RdP
     */
    public int matchTransicion (int transicion) {
        return rdp.matchTransicion(transicion);
    }

    /**
     * guarda el log una vez termina la ejecucion
     */
    public void guardarLog() {
        log.archivo();
    }

}
