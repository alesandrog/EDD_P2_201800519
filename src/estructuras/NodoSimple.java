/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import objetos.Usuario;

/**
 *
 * @author USUARIO
 */
public class NodoSimple {

    private NodoSimple siguiente;
    private Usuario valor;
    public String identificador = "";
    public String nodos = "";
    
    public NodoSimple(Usuario valor) {
        siguiente = null;
        this.valor = valor;
    }

    public NodoSimple() {
        siguiente = null;
        valor = new Usuario();
    }

    public NodoSimple getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoSimple siguiente) {
        this.siguiente = siguiente;
    }

    public Usuario getValor() {
        return valor;
    }

    public void setValor(Usuario valor) {
        this.valor = valor;
    }

}
