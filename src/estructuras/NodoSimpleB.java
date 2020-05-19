/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import objetos.Libro;

/**
 *
 * @author USUARIO
 */
public class NodoSimpleB {

    private NodoSimpleB siguiente;
    private Libro valor;
    private int pos;

    public NodoSimpleB(Libro valor) {
        this.siguiente = null;
        this.valor = valor;
    }

    public NodoSimpleB() {
        this.siguiente = null;
    }

    public NodoSimpleB getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoSimpleB siguiente) {
        this.siguiente = siguiente;
    }

    public Libro getValor() {
        return valor;
    }

    public void setValor(Libro valor) {
        this.valor = valor;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
    
    

}
