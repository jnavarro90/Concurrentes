/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package museo;

import java.util.ArrayList;

/**
 *
 * @author javi
 */
public class Museo {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Datos datos = new Datos();
        
        ArrayList listaVisitantes = new ArrayList();
        int n = 0;
      
        while(true){
            Visitante v = new Visitante();
            v.start();
        }
    }
    
}
