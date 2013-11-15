package itv;
/**
 *
 * @author Javi Navarro
 */
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Taller {
    public static final int MAX_CARRILES = 5;
    public static final int MAX_CLIENTES = 100;
    public static final int MAX_OPERARIOS = MAX_CARRILES;
    
    public static Integer carrilesLibres;
    public static boolean carrilesOperativos;
    public static Semaphore hayCarriles;
    public static Semaphore mutex;
    public static Semaphore oficina;
    
    public Taller() {
        //variables
        carrilesLibres = new Integer(MAX_CARRILES);
        carrilesOperativos = false;
        
        //semaforos
        mutex = new Semaphore(1, true);
        hayCarriles = new Semaphore(0, true);
        oficina = new Semaphore(1, true);
        
        //arrays de operarios y clientes
        Operario[] operarios = new Operario[MAX_OPERARIOS];
       
        for(int i=0;i<MAX_OPERARIOS;i++){
            if (i == 0){
                operarios[i] = new Operario(i, true);
            }else{
                operarios[i] = new Operario(i, false);
            }
        }
        Admin a1 = new Admin();
        
        Cliente c1 = new Cliente(a1, operarios, 1);
        Cliente c2 = new Cliente(a1, operarios, 2);
        
        a1.start();
        c1.start();
        c2.start();
    } 
}
