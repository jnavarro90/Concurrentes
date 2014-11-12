package museo;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author javi
 */
public class Guia extends Thread{
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
    * Nos encargamos de avisar a los visitantes quie pueden pasar a la sala C
    * lo hacemos en exclusion mutua para que hasta que no salga el ultimo
    * no pueda entrar otro grupo.
    */
    private synchronized void avisarVisitantes(){
        for(int i = 0; i <= Datos.MAX_GUIA; i++){
            Datos.guia.release();
        }
    }
    
    public void run() {
        while(true){
            try {
                Datos.guiaEsperando.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(Guia.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("El guia esta visitando la sala C con los "+Datos.MAX_GUIA+" visitantes.");
            Esperar(1000, 1000);
            
            avisarVisitantes();
        }
    }
    
}
