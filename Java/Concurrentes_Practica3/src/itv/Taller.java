package itv;
/**
 *
 * @author Javi Navarro
 */
import java.util.concurrent.Semaphore;

public class Taller {
    public static final int MAX_CARRILES = 5;
    public static final int MAX_CLIENTES = 100;
    public static final int MAX_OPERARIOS = MAX_CARRILES;
    
    public static Integer carrilesLibres;
    public static Semaphore carrilesOperativos;
    public static Semaphore hayCarriles;
    public static Semaphore mutex;
    public static Semaphore oficina;
    
    public static boolean hayCliente = false;
    public Taller() {
        //variables
        carrilesLibres = new Integer(MAX_CARRILES);
        
        //semaforos
        carrilesOperativos = new Semaphore(0, true); 
        mutex = new Semaphore(1, true);
        hayCarriles = new Semaphore(0, true);
        oficina = new Semaphore(0, true);
        
        //arrays de operarios y clientes
        Operario[] operarios = new Operario[MAX_OPERARIOS];
        Cliente[] clientes = new Cliente[MAX_CLIENTES];
        for(int i=0;i<MAX_OPERARIOS;i++){
            if (i == 0){
                operarios[i] = new Operario(i, true);
            }else{
                operarios[i] = new Operario(i, false);
            }
        }
        Admin a1 = new Admin();
        for(int i=0;i<MAX_CLIENTES;i++){
                clientes[i] = new Cliente(a1, operarios, i);
        }
        
        for(int i=0;i<MAX_OPERARIOS;i++){
            operarios[i].start();
        }
        for(int i=0;i<MAX_CLIENTES;i++){
            clientes[i].start();
        }
        a1.start();
    } 
}
