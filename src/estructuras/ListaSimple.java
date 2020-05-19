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
public class ListaSimple {

    private NodoSimple inicio;

    public ListaSimple() {
        inicio = new NodoSimple();
    }

    public NodoSimple getInicio() {
        return inicio;
    }

    public void setInicio(NodoSimple inicio) {
        this.inicio = inicio;
    }

    public void insertar(NodoSimple nuevo) {

        NodoSimple aux = inicio;
        NodoSimple aux2 = new NodoSimple();

        while (aux != null) {
            aux2 = aux;
            aux = aux.getSiguiente();
        }

        if (aux2 == inicio) {
            inicio = nuevo;
        } else {
            aux2.setSiguiente(nuevo);
        }

        nuevo.setSiguiente(aux);
    }

    public NodoSimple buscar(int carnet) {
        NodoSimple aux = inicio;

        while (aux != null) {
            if (aux.getValor().getCarnet() == carnet) {
                return aux;
            }
            aux = aux.getSiguiente();
        }

        return aux;
    }

    public boolean eliminar(int carnet) {
        NodoSimple aux = inicio;
        NodoSimple aux2 = new NodoSimple();

        while (aux != null && aux.getValor().getCarnet() != carnet) {
            aux2 = aux;
            aux = aux.getSiguiente();
        }

        if (aux != null) {

            if (aux == inicio) {
                inicio = new NodoSimple();
            } else {
                aux2.setSiguiente(aux.getSiguiente());
                aux.setValor(null);
            }
            return true;
        }

        return false;
    }

}
