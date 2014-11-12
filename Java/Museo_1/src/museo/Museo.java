/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package museo;

/**
 *
 * @author javi
 */
public class Museo {
    public static Datos datos = new Datos();
    public static Guia guia = new Guia();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        datos.start();
        guia.start();
        while(true){
            Visitante v = new Visitante();
            v.start();
        }
    }
    
}
