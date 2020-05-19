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
public class NodoArbol {

    private Object valor;
    private NodoArbol izquierda;
    private NodoArbol derecha;

    public NodoArbol(){
    }
    
    public NodoArbol(Object valor) {
        this.valor = valor;
        this.izquierda = this.derecha = null;
    }

    public NodoArbol(NodoArbol ramaIzdo, Object valor, NodoArbol ramaDcho) {
        this.valor = valor;
        this.izquierda = ramaIzdo;
        this.derecha = ramaDcho;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public NodoArbol getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodoArbol izquierda) {
        this.izquierda = izquierda;
    }

    public NodoArbol getDerecha() {
        return derecha;
    }

    public void setDerecha(NodoArbol derecha) {
        this.derecha = derecha;
    }
    
    

}
