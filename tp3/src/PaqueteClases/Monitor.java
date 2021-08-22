package PaqueteClases;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    private final int CANT_PLAZAS;
    private final int CANT_TRANSICIONES;
    private static Lock lock = new ReentrantLock();

    public Monitor(int CANT_PLAZAS, int CANT_TRANSICIONES) {
        this.CANT_PLAZAS = CANT_PLAZAS;
        this.CANT_TRANSICIONES = CANT_TRANSICIONES;
        //TODO
    }

    public int disparar() {
        //TODO
        return 0;
    }
}
