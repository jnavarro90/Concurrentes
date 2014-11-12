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
    public static final int MAX_SALA_ESPERA = 50;
    public static final int MAX_EDIFICIO = 12;
    public static final int MAX_GUIA = 6;
    public static final int MAX_ENCUESTA = 5;
    public static Semaphore colaSalaEspera;
    public static Semaphore colaEdificio;
    public static Semaphore cabinaFotos;
    public static Semaphore guia;
    public static Semaphore guiaEsperando;
    public static Semaphore encuesta;
    public static Semaphore mutex;
    public static int visitantesSalaEspera = 0;
    public static int visitantesEdificio = 0;
    public static int visitantesGuia = 0;
    public static int visitantesEncuesta = 0;
    public static boolean esperaLlena = false;
    public static int identificadorVisitante = 0;

    public Visitante() {
        colaSalaEspera = new Semaphore(MAX_SALA_ESPERA, true);
        colaEdificio = new Semaphore(MAX_EDIFICIO, true);
        cabinaFotos = new Semaphore(1, true);
        guia = new Semaphore(0, true);
        guiaEsperando = new Semaphore(1, esperaLlena);
        encuesta = new Semaphore(MAX_ENCUESTA, true);
        mutex = new Semaphore(1, true);
    }
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
    visitantesSalaEspera++;
    /**
     * Despues de incrementar el numero comprobamos si la sala de espera esta 
     * llena para que el siguiente que entre no pase y se vaya.
    */
    if(visitantesSalaEspera == MAX_SALA_ESPERA){
        esperaLlena = true;               
    }
}
    /**
     * Nos encargamos deasignarle al visitante un numero de identificacion
     * y aumentamos la variable en la clase datos en exclusion mutua.
     */
    private synchronized void asignarIdentificador(){
        this.identificador = identificadorVisitante;
        identificadorVisitante++;
    }
    /**
     * Nos encargamos de incrementar los visitantes que esperan para entrar
     * a la sala C, y cuando estan todos se llama al guia.
     */
     private synchronized void avisarGuia() {
        visitantesGuia++;
        if(visitantesGuia == MAX_GUIA){
            System.out.println("Hay "+visitantesGuia+" esperando para entrar a la sala C, hay que avisar al guia.");
            visitantesGuia = 0;
            guiaEsperando.release();
        }else{
            System.out.println("Hay "+visitantesGuia+" esperando para entrar a la sala C.");
        }
    }

    @Override
    public void run() {
        if(!esperaLlena){
            aumentarSalaEspera();
            asignarIdentificador();
            
            try {
                colaEdificio.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, ex);
            }
            /**
             * El visitante visita la sala A
             */
            System.out.println("El visitante con identificador "+identificador+" esta visitando la sala A.");
            Esperar(100, 100);
            
            try {
                cabinaFotos.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, ex);
            }
            /**
             * El visitante se hace una foto
             */
            System.out.println("El visitante con identificador "+identificador+" se está haciendo una foto.");
            Esperar(100, 100);
            cabinaFotos.release();
            
            /**
             * El visitante visita la sala B
             */
            System.out.println("El visitante con identificador "+identificador+" esta visitando la sala B.");
            Esperar(100, 100);
            
            avisarGuia();
            try {
                guia.acquire();
                encuesta.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("El visitante con identificador "+identificador+" esta haciendo la encuesta.");
            Esperar(100, 100);
            encuesta.release();
            
            System.out.println("El visitante con identificador "+identificador+" ha abandonado el edificio.");
        }
    }
}
