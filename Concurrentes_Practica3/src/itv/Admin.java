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
       try {
                Taller.oficina.acquire();           //¿Esta la oficina libre? 
            } catch (InterruptedException e) {
                System.out.println("Se ha producido la excepcion "+e);
            }
        System.out.println("Los papeles del cliente "+this.numCliente+" se han tramitado puede pasar a un carril...");
    }
    public void run(){ 
        while(true){
            
            try {
               Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Se ha producido la excepcion "+e);
            }
            
            System.out.println("Tramitando papeles del cliente "+this.numCliente+" ...");
            Taller.oficina.release();
     
        }
    }
}