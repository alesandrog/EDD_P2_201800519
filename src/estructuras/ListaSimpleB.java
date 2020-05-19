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
public class ListaSimpleB {

    public NodoSimpleB inicio;
    
    //almacena el numero de elementos insertados
    private int elementos = 0;

    public ListaSimpleB() {
        this.inicio = new NodoSimpleB();
    }

    public void insertar(NodoSimpleB nuevo) {
        NodoSimpleB aux = inicio;
        NodoSimpleB aux2 = new NodoSimpleB();
        int i = 0;

        while (aux != null) {
            aux2 = aux;
            aux = aux.getSiguiente();
            i++;
        }
        nuevo.setPos(i - 1);

        aux2.setSiguiente(nuevo);
        nuevo.setSiguiente(aux);

    }

}
