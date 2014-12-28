package sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author javinavarroalaman
 */
public class Listin implements Runnable{
    
    protected int serverPort = 8080;
    protected ServerSocket serverSocket = null;
    protected boolean isStoped = false;
    protected Thread runningThread = null;
    
    public Listin(int port){
        this.serverPort = port;
    }
    
    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        
        openServerSocket();
        while(! isStoped()){
            Socket clientSocket = null;
            try{
                clientSocket = this.serverSocket.accept();
            }catch (IOException e){
                if(isStoped()){
                    System.out.println("Server stoped");
                    return;
                }
                throw new RuntimeException("Error accepting client conexion", e);
            }
            new Thread(
                new WorkerRunnable(
                    clientSocket, "Multithreaded server")
            ).start();
        }
        System.out.println("Server stopped");
    }
    
    public synchronized boolean isStoped(){
        return this.isStoped;
    }
    
    public synchronized void stop(){
        this.isStoped = true;
        try{
            this.serverSocket.close();
        }catch (IOException e){
            throw new RuntimeException("Error closing server", e);
        }
    }
    
    private void openServerSocket(){
        try{
            this.serverSocket = new ServerSocket(this.serverPort);
        }catch(IOException e){
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }
    
}
