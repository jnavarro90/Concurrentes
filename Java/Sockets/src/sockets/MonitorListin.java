package sockets;

import java.util.ArrayList;

/**
 *
 * @author javinavarroalaman
 */ 
public class MonitorListin extends Thread{
    
    private static ArrayList<Contacto> listaContactos = new ArrayList<Contacto>();
    private static boolean exito = false;
    private static int indice = 0;
    
    public static synchronized boolean alta(String n, String t){
        Contacto c = new Contacto(n, t);
        if(!listaContactos.contains(c)){
            listaContactos.add(new Contacto(n, t));
            if(!listaContactos.contains(n)){
               exito = false; 
            }
            exito = true;
        }else{
            exito = false;
        }
        return exito;
    }
    public static synchronized boolean consulta(String n){
        Contacto c = new Contacto(n, "0");
         if(listaContactos.contains(c)){
            indice = listaContactos.indexOf(n);
            System.out.println(listaContactos.get(indice+1).toString());
            exito = true;
        }else{
            exito = false;
        }
        return exito;
    }
}
