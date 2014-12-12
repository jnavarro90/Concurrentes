package socket;

import java.net.*;
import java.io.*;
/**
 *
 * @author Javi
 */

public class Servidor extends Thread{

final int PUERTO=1088;

ServerSocket sc;
Socket so;
DataOutputStream salida;
String mensajeRecibido;
//SERVIDOR

    public void run(){

    BufferedReader entrada;
        while(true){
            try{

                sc = new ServerSocket(PUERTO );/* crea socket servidor que escuchara en puerto 1088*/
                so=new Socket();

                System.out.println("Esperando una conexión:");

                //Inicia el socket, ahora esta esperando una conexión por parte del cliente
                so = sc.accept();
                System.out.println("Un cliente se ha conectado.");

                //Canales de entrada y salida de datos

                entrada = new BufferedReader(new InputStreamReader(so.getInputStream()));
                salida = new DataOutputStream(so.getOutputStream());
                System.out.println("Confirmando conexion al cliente....");
                salida.writeUTF("Conexión exitosa..\n envia un mensaje :D");

                //Recepcion de mensaje

                mensajeRecibido = entrada.readLine();
                System.out.println("Servidor: "+mensajeRecibido);
                salida.writeUTF("Se recibio tu mensaje\n Terminando conexion...");
                salida.writeUTF("Gracias por conectarte, adios!");
                System.out.println("Cerrando conexión...");

                salida.close();
                sc.close();//Aqui se cierra la conexión con el cliente

            }catch(Exception e ){

                System.out.println("Error: "+e.getMessage());

            }
        }
    }
}