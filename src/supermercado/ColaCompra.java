package supermercado;

import java.time.LocalTime;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ColaCompra {
    private static final int NO_CAJA = -1;
    private final Semaphore semaphore;
    private final Lock reentrantLock = new ReentrantLock(true);
    private final int[] cajas;
    private final boolean[] cajaDisposible;

    public ColaCompra(int numeroDeCajas) {
        semaphore = new Semaphore(numeroDeCajas, true);
        cajas = new int[numeroDeCajas];
        cajaDisposible = new boolean[numeroDeCajas];
        for (int i = 0; i < numeroDeCajas; i++){
            cajas[i] = i;
            cajaDisposible[i] = true;
        }
    }
    public void comprar(int tiempoCompra) {
        try {
            semaphore.acquire();
            System.out.println(LocalTime.now().getHour()+":"+LocalTime.now().getMinute()+":"+LocalTime.now().getSecond()+" -> el cliente: "+Thread.currentThread().getName()+" ha entrado a la cola.");
            int numeroCompra = seleccionarCaja();
            if(numeroCompra != NO_CAJA){
                TimeUnit.SECONDS.sleep(tiempoCompra);
                System.out.println(LocalTime.now().getHour()+":"+LocalTime.now().getMinute()+":"+LocalTime.now().getSecond()+" -> el cliente: "+Thread.currentThread().getName()+" ha realizado su compra.");
            }
            cajaDisposible[numeroCompra] = true;
        } catch (InterruptedException e) {
            return;
        } finally {
            semaphore.release();
        }
    }

    private int seleccionarCaja() {
        reentrantLock.lock();
        System.out.println(LocalTime.now().getHour()+":"+LocalTime.now().getMinute()+":"+LocalTime.now().getSecond()+" -> el cliente: "+Thread.currentThread().getName()+" va a elegir una caja.");
        try {
            for (int i = 0; i < cajas.length; i++){
                if(cajaDisposible[i]){
                    int numCaja = i+1;
                    cajaDisposible[i] = false;
                    System.out.println(LocalTime.now().getHour()+":"+LocalTime.now().getMinute()+":"+LocalTime.now().getSecond()+" -> el cliente: "+Thread.currentThread().getName()+ " ha cogido la caja "+numCaja+".");
                    return i;
                }
            }
        } finally {
            reentrantLock.unlock();
        }
        return NO_CAJA;
    }
}
