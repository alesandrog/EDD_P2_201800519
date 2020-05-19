/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

/**
 *
 * @author USUARIO
 */
public class Casilla {
 
    private ListaSimple lista_colision;
    
    public Casilla(){
    lista_colision = new ListaSimple();
    }

    public ListaSimple getLista_colision() {
        return lista_colision;
    }
    
    
}
