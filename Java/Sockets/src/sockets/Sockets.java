package sockets;

/**
 *
 * @author javinavarroalaman
 */
public class Sockets {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Listin server = new Listin(9000);
    new Thread(server).start();

    try {
        Thread.sleep(5);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println("Stopping Server");
    server.stop();
    }
    
}
