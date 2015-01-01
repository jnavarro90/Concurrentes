package sockets;

import java.net.*;
import java.io.*;

/**
*
* @author Javi
*/

public class Cliente extends Thread{

final String HOST = "localhost";
final String OK = "OK";
final String ERROR = "ERROR";
final String ALTA = "ALTA";
final String CONSULTA = "CONSULTA";

int serverPort = 8080; //numero de puerto por defecto
String operacion;
String nombre;
String telefono;
String mServidor;

PrintWriter salida;	 
BufferedReader entrada;
Socket sc;

//Cliente
    public Cliente(int port, String n, String t){
        this.serverPort = port;
        this.operacion = ALTA;
        this.nombre = n;
        this.telefono = t; 
    }
    public Cliente(int port, String n){
        this.serverPort = port;
        this.operacion = CONSULTA;
        this.nombre = n;
    }
    
    
public void run(){

        try{

            sc = new Socket( HOST , serverPort ); //conectar a un servidor en localhost con puerto indicado

            entrada = new BufferedReader(new InputStreamReader(sc.getInputStream()));
            salida = new PrintWriter(sc.getOutputStream(), true);

            //enviamos el mensaje
            if(operacion.equals(CONSULTA)){
                salida.println(operacion);
                salida.println(nombre);
               
            }else if(operacion.equals(ALTA)){
                salida.println(operacion);
                salida.println(nombre);
                salida.println(telefono);
            }else{
                System.out.println("Se ha indicado mal la operacion ha realizar.");
            }

            //cerramos la conexión
            sc.close();

        }catch(Exception e ){

            System.out.println("Error en el cliente: "+e.getMessage());

        }

    }

}
