package museo;

import java.util.concurrent.Semaphore;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author javi
 */
public class Visitante extends Thread{
private int identificador = 0;
/**
* Nos encargamos de proporcionar un tiempo aleatorio, creamos esta
* función por comodidad.
*/
private void Esperar(int min, int max) {
    try {
        sleep(min + (int) (max * Math.random()));
        } catch (Exception e) {
        System.out.println("Error al obtener numero aleatorio.");
    }
}
/**
 * Nos encargamos de aumentar en uno el numero de visitantes en la sala de espera
 * y se hace en exclusion mutua
*/
private synchronized void aumentarSalaEspera(){
    Datos.visitantesSalaEspera++;
    /**
     * Despues de incrementar el numero comprobamos si la sala de espera esta 
     * llena para que el siguiente que entre no pase y se vaya.
    */
    if(Datos.visitantesSalaEspera == Datos.MAX_SALA_ESPERA){
        Datos.esperaLlena = true;               
    }
}
/**
 * Nos encargamos de aumentar en uno el numero de visitantes en la sala de espera
 * y se hace en exclusion mutua
*/
private synchronized void disminuirSalaEspera(){
    Datos.visitantesSalaEspera--;
    /**
     * Despues de incrementar el numero comprobamos si la sala de espera esta 
     * llena para que el siguiente que entre no pase y se vaya.
    */
    if(Datos.visitantesSalaEspera != Datos.MAX_SALA_ESPERA){
        Datos.esperaLlena = false;               
    }
}
    /**
     * Nos encargamos deasignarle al visitante un numero de identificacion
     * y aumentamos la variable en la clase datos en exclusion mutua.
     */
    private synchronized void asignarIdentificador(){
        this.identificador = Datos.identificadorVisitante;
        Datos.identificadorVisitante++;
    }
    /**
     * Nos encargamos de incrementar los visitantes que esperan para entrar
     * a la sala C, y cuando estan todos se llama al guia.
     */
     private synchronized void avisarGuia() {
        Datos.visitantesGuia++;
        if(Datos.visitantesGuia == Datos.MAX_GUIA){
            System.out.println("Hay "+Datos.visitantesGuia+" esperando para entrar a la sala C, hay que avisar al guia.");
            Datos.visitantesGuia = 0;
            Datos.guiaEsperando.release();
        }else{
            System.out.println("Hay "+Datos.visitantesGuia+" esperando para entrar a la sala C.");
        }
    }

    public void run() {

        if(!Datos.esperaLlena){
            aumentarSalaEspera();
            asignarIdentificador();
            
            try {
                Datos.colaEdificio.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, ex);
            }
            Esperar(1000, 1000);
            disminuirSalaEspera();
            /**
             * El visitante visita la sala A
             */
            System.out.println("El visitante con identificador "+identificador+" esta visitando la sala A.");
            Esperar(1000, 1000);
            
            try {
                Datos.cabinaFotos.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, ex);
            }
            /**
             * El visitante se hace una foto
             */
            System.out.println("\n---------------Cabina de fotos---------------\n"
                    + "El visitante con identificador "+identificador+" se está haciendo una foto.\n");
            Esperar(1000, 1000);
            Datos.cabinaFotos.release();
            
            /**
             * El visitante visita la sala B
             */
            System.out.println("El visitante con identificador "+identificador+" esta visitando la sala B.");
            Esperar(1000, 1000);
            
            avisarGuia();
            try {
                Datos.guia.acquire();
                Datos.encuesta.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("El visitante con identificador "+identificador+" esta haciendo la encuesta.");
            Esperar(1000, 1000);
            Datos.encuesta.release();
            
            Datos.colaEdificio.release();
            System.out.println("El visitante con identificador "+identificador+" ha abandonado el edificio.");
        }
    }
}
