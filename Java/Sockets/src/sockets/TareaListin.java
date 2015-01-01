package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**

 */
public class TareaListin implements Runnable{

    final String ALTA = "ALTA";
    final String CONSULTA = "CONSULTA";
    final String ERROR = "ERROR";
    final String OK = "OK";
    protected Socket clientSocket = null;
    protected String serverText   = null;
    PrintWriter salida;	 
    BufferedReader entrada;
    String operacion;
    String nombre;
    String telefono;
    public TareaListin(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
                entrada = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        	 
        	salida = new PrintWriter(clientSocket.getOutputStream(), true);
        	 
        	System.out.println("Confirmando conexion al cliente....");
        	 
        	salida.println("Conexión exitosa...");        	
       	 
        	operacion = entrada.readLine();
                if(operacion.equals(CONSULTA)){         
                    System.out.println("Quiere hacer una consulta\n"
                            + "Introduzca el nombre: \n");
                    nombre = entrada.readLine();
                    System.out.println("Comprobando nombre "+nombre+" en la base de datos...\n");
                    if(MonitorListin.consulta(nombre)){
                        System.out.println("Consulta realizada con exito.");
                        salida.println("noERROR");
                    }else{
                        System.out.println(nombre+" no está en la base de datos.");
                        salida.println("ERROR");
                    }
                }else if(operacion.equals(ALTA)){
                    System.out.println("Quiere dar de alta un contacto\n"
                            + "Introduzca el nombre y un numero de telefono:");
                    nombre = entrada.readLine();
                    telefono = entrada.readLine();
                    System.out.println("Comprobando nombre "+nombre+" en la base de datos...\n");
                    if(MonitorListin.alta(nombre, telefono)){
                        System.out.println("Alta de "+nombre+" realizada con exito.");
                        salida.println("OK");
                    }else{
                        System.out.println(nombre+" ya está en la base de datos.");
                        salida.println("noOK");
                    }
                }
            entrada.close();
            salida.close();
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}
