/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import java.util.ArrayList;
import objetos.Libro;

/**
 *
 * @author USUARIO
 */
public class Lista {
       // instance variables - replace the example below with your own
    public static ArrayList<Libro> ingresados;


    public Lista()
    {
       ingresados = new ArrayList<Libro>();
    }
    public boolean buscar(String valor){
        boolean esta = false;
        for (int i = 0; i < ingresados.size() && !esta; i++){
            if (ingresados.get(i).getIsbn() == valor){
                 esta = true;                
            }
        }
        return esta;
        
    }
}
