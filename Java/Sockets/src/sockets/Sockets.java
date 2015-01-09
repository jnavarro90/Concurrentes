package sockets;

import com.sun.security.ntlm.Client;

/**
 *
 * @author javinavarroalaman
 */
public class Sockets {
    public static MonitorListin monitor = new MonitorListin();    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        monitor.start();
        Listin server = new Listin(9000);
    new Thread(server).start();
    try {
        Thread.sleep(10 * 1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    Cliente c6 = new Cliente(900, "Luis");
    Cliente c3 = new Cliente(9000, "Pepe", "626371576");
    Cliente c4 = new Cliente(9000, "Javi", "626371213");
    Cliente c1 = new Cliente(9000, "Javi", "626371213");
    Cliente c2 = new Cliente(9000, "Javi");
    c1.start();
    c2.start();
    try {
        Thread.sleep(10 * 1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    c6.start();
    c4.start();
    try {
        Thread.sleep(10 * 1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    c3.run();

    try {
        Thread.sleep(20 * 1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println("Stopping Server");

    server.stop();
    }
    
}
