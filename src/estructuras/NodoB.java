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
public class NodoB {

    private Object[] claves;
    private NodoB padre;
    private NodoB[] hijos;
    private int clavesUsadas = 0;
    
    public NodoB() {
        padre = null;
        claves = new Object[5];
        hijos = new NodoB[5];
        for (int i = 0; i < 4; i++) {
            claves[i] = null;
         }
    }

    public NodoB getPadre() {
        return padre;
    }

    public void setPadre(NodoB padre) {
        this.padre = padre;
    }

    public int getClavesUsadas() {
        return clavesUsadas;
    }

    public void setClavesUsadas(int clavesUsadas) {
        this.clavesUsadas = clavesUsadas;
    }

    public Object[] getClaves() {
        return claves;
    }

    public void setClaves(Object[] claves) {
        this.claves = claves;
    }

    

    public NodoB[] getHijos() {
        return hijos;
    }

    public void setHijos(NodoB[] hijos) {
        this.hijos = hijos;
    }
    
    
    
    
}
