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
public class ArbolBPrueba {

    private NodoB raiz;

    public ArbolBPrueba() {

        raiz = new NodoB();

    }

    public void buscar(Object valor) {
        buscar_nodo(raiz, valor);
    }

    public void buscar_nodo(NodoB nodo, Object valor) {

    }

    public void insertar(NodoB padre, Object valor) {

        if (!tieneHijos(padre)) {
            for (int i = 0; i < 5; i++) {
                if (padre.getClaves()[i] == null) {
                    padre.getClaves()[i] = valor;
                    padre.setClavesUsadas(padre.getClavesUsadas() + 1);
                    if (i == 4) 
                    {
                        //split
                        break;
                    }
                }
            }
        } else {
            for (int i = 0; i <= padre.getClavesUsadas(); i++) {
                if ((int) valor < (int) padre.getClaves()[i]) {
                    insertar(padre.getHijos()[i], valor);
                    break;
                }
            }
        }
    }

    
    public void insertarPadre(NodoB padre, Object valor) {
        for (int i = 0; i < 5; i++) {
            if (padre.getClaves()[i] == null) {
                padre.getClaves()[i] = valor;
                padre.setClavesUsadas(padre.getClavesUsadas() + 1);
                if (i == 4) //split
                {
                    break;
                }
            }
        }
    }

    public void split(NodoB nodo) {
        if (nodo == raiz) {
            NodoB nRaiz = new NodoB();
            insertarPadre(nRaiz, nodo.getClaves()[2]);
            nodo.setPadre(nRaiz);
            NodoB hijo2 = new NodoB();
            insertar(hijo2, nodo.getClaves()[3]);
            insertar(hijo2, nodo.getClaves()[4]);
            hijo2.setPadre(nRaiz);
            for (int i = 2; i < 5; i++) {
                nodo.getClaves()[i] = null;
            }
            insertarHijo(nRaiz, nodo);
            insertarHijo(nRaiz, hijo2);

        } else {

            NodoB der = new NodoB();

            insertarPadre(nodo.getPadre(), nodo.getClaves()[2]);
            insertar(der , nodo.getClaves()[3]);
            insertar(der , nodo.getClaves()[4]);
            insertarHijo(nodo.getPadre() , der );

        }
    }

    public boolean tieneHijos(NodoB nodo) {
        return nodo.getHijos()[0] == null;
    }

    public void insertarHijo(NodoB nodo, NodoB nuevo) {
        for (int i = 0; i < 5; i++) {
            if (nodo.getHijos()[i] == null) {
                nodo.getHijos()[i] = nuevo;
                break;
            }
        }
    }

    public static void intercambiar(Object[] a, int i, int j) {
        Object aux = a[i];
        a[i] = a[j];
        a[j] = aux;
    }

    public static void quicksort(Object a[]) {
        quicksort(a, 0, a.length - 1);
    }

    private static void quicksort(Object a[], int primero, int ultimo) {
        int i, j, central;
        double pivote;
        central = (primero + ultimo) / 2;
        pivote = (int) a[central];
        i = primero;
        j = ultimo;
        do {
            while ((int) a[i] < pivote) {
                i++;
            }
            while ((int) a[j] > pivote) {
                j--;
            }
            if (i <= j) {
                intercambiar(a, i, j);
                i++;
                j--;
            }
        } while (i <= j);
        if (primero < j) {
            quicksort(a, primero, j); // mismo proceso con sublista izqda
        }
        if (i < ultimo) {
            quicksort(a, i, ultimo); // mismo proceso con sublista drcha
        }
    }

}
