package concurrentes_p2;

import static java.lang.Math.random;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author javi
 */
public class Concurrentes_P2 {

    /**
     * @param args the command line arguments
     * 
     */
    public static void main(String[] args) {
        FlujoA fA = new FlujoA();
        FlujoB fB = new FlujoB();
        FlujoC fC = new FlujoC();
        
        fA.start();
        fB.start();
        fC.start();
    }
    
    /**
     * Hilo con el flujo A q lo escribira por pantalla 10 veces
     */
    public static class FlujoA extends Thread{
        public void run(){
            for(int i = 0;i<10; i++){
                try {
                    System.out.println("Flujo A");
                    sleep((int)random());
                } catch (InterruptedException ex) {
                    Logger.getLogger(Concurrentes_P2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    /**
     * Hilo con el flujo B q lo escribira por pantalla 15 veces
     */
    public static class FlujoB extends Thread{
        public void run(){
            for(int i = 0;i<15; i++){
                try {
                    System.out.println("Flujo B");
                    sleep((int)random());
                } catch (InterruptedException ex) {
                    Logger.getLogger(Concurrentes_P2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    /**
     * Hilo con el flujo C q lo escribira por pantalla 9 veces
     */
    public static class FlujoC extends Thread{
        public void run(){
            for(int i = 0;i<9; i++){
                try {
                    System.out.println("Flujo C");
                    sleep((int)random());
                } catch (InterruptedException ex) {
                    Logger.getLogger(Concurrentes_P2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}
