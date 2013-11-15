package itv;

import java.util.logging.Level;
import java.util.logging.Logger;
import itv.*;

/**
 *
 * @author Javi Navarro
 */
public class Admin extends Thread{
    int numCliente;
    public void tramitarPapeles(int numCliente){
       this.numCliente = numCliente;
       run();

    }
    public void run(){           
            try {
               Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Se ha producido la excepcion "+e);
            }
            System.out.println("Tramitando papeles del cliente "+numCliente+" ...");
            Taller.oficina.release();
         
    }
}
