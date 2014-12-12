package socket;

/**
 *
 * @author javinavarroalaman
 */
public class Sockets {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Servidor serv = new Servidor();
        
        serv.start();
        while(true){
            Cliente client = new Cliente();
            client.start();
        }
    }
    
}
