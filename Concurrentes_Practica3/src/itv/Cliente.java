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
    boolean salir = false;
    int numCliente;
    public Cliente(Admin a, Operario[] operarios, int numCliente) {
        this.a = a;
        this.operarios = operarios;
        this.numCliente = numCliente+1;
    }
    
    public void run(){
        while(true){
            
            a.tramitarPapeles(this.numCliente);          //Ve a la oficina y que te tramite los papeles
            
            Taller.hayCliente = true;
            for (int i = 0; i < operarios.length;i++){
                
                    operarios[i].revisionVehiculo(i, this.numCliente); //Busca cual esta libre y que revise el vehiculo.
                                                                //Se pasa la lista de operarios y el operario que va a atender al cliente.
            }
    }
    }
}
