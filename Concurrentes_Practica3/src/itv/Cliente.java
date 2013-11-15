package itv;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.Semaphore;
/**
 *
 * @author Javi Navarro
 */
public class Cliente extends Thread{
    Admin a = null;
    Operario[] operarios = null;
    int numCliente;
    public Cliente(Admin a, Operario[] operarios, int numCliente) {
        this.a = a;
        this.operarios = operarios;
        this.numCliente = numCliente;
        
        try {
                Taller.oficina.acquire();           //esperar a que la oficina este vacia  
            } catch (InterruptedException e) {
                System.out.println("Se ha producido la excepcion "+e);
            }
        run();
    }
    
    public void run(){
            
            System.out.println("Cliente"+numCliente+" run...");
            
            a.tramitarPapeles(numCliente);
            System.out.println("Los papeles del cliente "+numCliente+" se han tramitado puede pasar a un carril...");
        
    }
}
