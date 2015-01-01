package sockets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author javinavarroalaman
 */
public class Listin implements Runnable{
    protected int serverPort = 8080; //numero de puerto por defecto
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
            System.out.println("Esperando una conexión:");
            try{
                clientSocket = this.serverSocket.accept();
                System.out.println("Un cliente se ha conectado.");
            }catch (IOException e){
                if(isStoped()){
                    System.out.println("El servidor se ha parado");
                    return;
                }
                throw new RuntimeException("Error aceptando la conexion con el cliente", e);
            }
            new Thread(
                   //creamos un nuevo hilo de ejecucion de la clase TareaListin que controlara lo que quiere el usuario
                new TareaListin(
                    clientSocket)
            ).start();
        }
        System.out.println("Server stopped");
    }
    
    //estos dos metodos son synchronized por que no se mezclen si cualquier hilo los cambia
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
