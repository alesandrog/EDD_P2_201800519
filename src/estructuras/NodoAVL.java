/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import objetos.SesionActiva;

/**
 *
 * @author USUARIO
 */
public class NodoAVL {

    int fe;
    ArbolB libros;
    String categoria;
    NodoAVL izquierda;
    NodoAVL derecha;
    public int propietario; 
    public String indice = "";
    public int totalLibros = 0;
    private int altura = 1;

    public NodoAVL(String categoria) {
        libros = new ArbolB(2);
        this.categoria = categoria;
        fe = 0;
        this.propietario = SesionActiva.usuario_sesion.getCarnet();
    }

 
    public int altura(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.altura;
    }


    public int getFe() {
        return fe;
    }

    public void setFe(int fe) {
        this.fe = fe;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public NodoAVL getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodoAVL izquierda) {
        this.izquierda = izquierda;
    }

    public NodoAVL getDerecha() {
        return derecha;
    }

    public void setDerecha(NodoAVL derecha) {
        this.derecha = derecha;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public ArbolB getLibros() {
        return libros;
    }

    public void setLibros(ArbolB libros) {
        this.libros = libros;
    }
    
    

    public interface Comparador {

        boolean igualQue(Object q);

        boolean menorQue(Object q);

        boolean menorIgualQue(Object q);

        boolean mayorQue(Object q);

        boolean mayorIgualQue(Object q);
    }

}
