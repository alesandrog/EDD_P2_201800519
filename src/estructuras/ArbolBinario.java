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
public class ArbolBinario {

    protected NodoArbol raiz;

    public ArbolBinario() {
        raiz = null;
    }

    public ArbolBinario(NodoArbol raiz) {
        this.raiz = raiz;
    }

    public NodoArbol raizArbol() {
        return raiz;
    }
// Comprueba el estatus del árbol

    boolean esVacio() {
        return raiz == null;
    }

    public static NodoArbol nuevoArbol(
            NodoArbol ramaIzqda, Object dato, NodoArbol ramaDrcha) {
        return new NodoArbol(ramaIzqda, dato, ramaDrcha);
    }  
}
