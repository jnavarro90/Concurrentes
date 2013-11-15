package itv;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javi Navarro Alamán
 * @Practica 3 - Programacion de sistemas concurrentes y distribuidos
 */
public class Operario extends Thread {

    int carril;
    int num;
    boolean jefe = false;
    Operario[] operarios = null;
    int numCliente;

    public Operario(int carril, boolean jefe) {
        this.carril = carril;
        this.jefe = jefe;
        num = carril + 1;
    }

    void revisionVehiculo(int carril, int numCliente) {
        this.carril = carril;
        this.numCliente = numCliente;
        Taller.carrilesLibres--;

    }

    public void run() {
        while (true) {
            if (Taller.hayCliente) {
                Taller.hayCliente = false;
                try {
                    Taller.mutex.acquire();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Operario.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Se ha producido la excepcion " + e);
                    }

                    Taller.hayCarriles.release();

                if (Taller.carrilesLibres == 0) {

                    try {
                        Taller.hayCarriles.acquire();               //¿Hay algun operario/carril libre?
                    } catch (InterruptedException e) {
                        System.out.println("Se ha producido la excepcion " + e);
                    }
                    Taller.hayCarriles.release();
                    System.out.println("A un cliente lo esta atendiendo el operario " + num + " en el carril " + num + " ...");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        System.out.println("Se ha producido la excepcion " + e);
                    }

                    Taller.hayCarriles.release();

                } else {

                    Taller.hayCarriles.release();
                    System.out.println("A un cliente lo esta atendiendo el operario " + num + " en el carril " + num + " ...");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        System.out.println("Se ha producido la excepcion " + e);
                    }
                }
                System.out.println("El vehiculo del cliente ha sido revisado por el operario " + num + " en el carril " + num + " y puede marcharse...");
                Taller.mutex.release();
            }
        }
    }
}
