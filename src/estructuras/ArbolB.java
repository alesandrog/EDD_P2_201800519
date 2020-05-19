/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import objetos.Libro;
import objetos.SesionActiva;

/**
 *
 * @author USUARIO
 */
public class ArbolB {

    public static int grado;
    public Nodo primerNodo;
    public static boolean esRaiz;
    public static int nivel = 1;
    public static int imprimir = 1;
    public String arbol = "";
    public static String val_internos = "";
    public int coincidencias = 0;
    public int contadorNodos = 0;
    public  ArrayList<Libro> ingresados = new ArrayList<Libro>();

    public ArbolB(int grado) {
        this.grado = grado;
        primerNodo = new Nodo();
        //   Lista llevarIngresos = new Lista();
        esRaiz = true;
    }

    public void insertar(Libro valor) {
        if (primerNodo.tengoHijos == false) {
            int j = 0;
            for (int i = 0; i < primerNodo.valores.length; i++) {
                if (primerNodo.valores[i] == null) {
                    primerNodo.valores[i] = valor;
                    //             Lista.ingresados.add(valor);
                    j = i;
                    ordenar(primerNodo.valores);
                    ingresados.add(valor);
                    break;
                }
            }
            if (j == 2 * grado) {
                split(primerNodo);
            }
        } else {
            setTengoHijos(primerNodo);
            ingresarEnHijos(primerNodo, valor);

        }
    }

    public void ordenar(Libro arr[]) {
        int longitud = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                longitud++;
            } else {
                break;
            }
        }
        for (int ord = 0; ord < longitud; ord++) {
            for (int ord1 = 0; ord1 < longitud - 1; ord1++) {
                if (arr[ord1].getIsbn().compareToIgnoreCase(arr[ord1 + 1].getIsbn()) > 0) {
                    Libro tmp = arr[ord1];
                    arr[ord1] = arr[ord1 + 1];
                    arr[ord1 + 1] = tmp;

                }
            }
        }
    }

    public void setTengoHijos(Nodo nodo) {
        if (nodo == primerNodo) {
            if (primerNodo.nodo[0] != null) {
                primerNodo.tengoHijos = true;
            }
        }
        for (int i = 0; i < nodo.nodo.length; i++) {
            if (nodo.nodo[i] != null) {
                nodo.tengoHijos = true;
                setTengoHijos(nodo.nodo[i]);
            }
        }
    }

    public void ingresarEnHijos(Nodo conHijos, Libro valor) {
        boolean entro = false;
        if (conHijos != null && !conHijos.tengoHijos) {
            ubicarValorEnArreglo(conHijos, valor);
            entro = true;
        }
        for (int i = 0; conHijos != null && i < 2 * grado + 1 && !entro; i++) {
            if (conHijos.valores[i] == null || valor.getIsbn().compareToIgnoreCase(conHijos.valores[i].getIsbn()) < 0) {
                entro = true;
                ingresarEnHijos(conHijos.nodo[i], valor);
                i = 2 * grado;
            }
        }
    }

    public void ubicarValorEnArreglo(Nodo nodoA, Libro valor) {
        int cont = 0;
        while (cont <= 2 * grado) {
            if (nodoA.valores[cont] == null) {
                nodoA.valores[cont] = valor;
                ordenar(nodoA.valores);
                //       Lista.ingresados.add(valor);
                    ingresados.add(valor);
                if (cont == 2 * grado) {
                    split(nodoA);
                }
                break;
            }
            cont++;
        }
    }

    public void ordenarNodos(Nodo aOrdenar) {
        int i, j;
        i = 0;
        Nodo tmp;

        while (i < 2 * grado + 3 && aOrdenar.nodo[i] != null) {
            j = 0;
            while (j < 2 * grado + 2 && aOrdenar.nodo[j] != null && aOrdenar.nodo[j + 1] != null) {
                if (aOrdenar.nodo[j].valores[0].getIsbn().compareToIgnoreCase(aOrdenar.nodo[j + 1].valores[0].getIsbn()) > 0) {
                    tmp = aOrdenar.nodo[j];
                    aOrdenar.nodo[j] = aOrdenar.nodo[j + 1];
                    aOrdenar.nodo[j + 1] = tmp;
                }
                j++;
            }
            i++;
        }
    }

    public void split(Nodo nodo) {

        Nodo hijoIzq = new Nodo();
        Nodo hijoDer = new Nodo();

        //split general 
        if (nodo.nodo[0] != null) { //si tiene hijos antes de hacer el split entonces
            for (int i = 0; i < grado + 1; i++) { // los separa los hijos del nodo en hijoIzq e hijoDer
                hijoIzq.nodo[i] = nodo.nodo[i];
                hijoIzq.nodo[i].padre = hijoIzq;
                nodo.nodo[i] = null;
                hijoDer.nodo[i] = nodo.nodo[grado + 1 + i];
                hijoDer.nodo[i].padre = hijoDer;
                nodo.nodo[grado + 1 + i] = null;
            }
        }

        for (int i = 0; i < grado; i++) { //guarda los valores en hijoIzq e hijoDer
            hijoIzq.valores[i] = nodo.valores[i];
            nodo.valores[i] = null;
            hijoDer.valores[i] = nodo.valores[grado + 1 + i];
            nodo.valores[grado + 1 + i] = null;
        }
        nodo.valores[0] = nodo.valores[grado];
        nodo.valores[grado] = null; //queda en nodo solo el valor que "subio"

        nodo.nodo[0] = hijoIzq; //asigna a nodo el nuevo hijo izquierdo (hijoIzq)
        nodo.nodo[0].padre = nodo; // se hizo en primer ciclo
        nodo.nodo[1] = hijoDer; // asigna a nodo el nuevo hijo derecho (hijoDer)
        nodo.nodo[1].padre = nodo; // se hizo en el primer ciclo        
        setTengoHijos(primerNodo);
        ordenarNodos(nodo);

        if (nodo.padre != null) { // luego del split y asignar los hijos (hijoIzq, hijoDer), subir el valor al padre
            boolean subido = false;
            for (int i = 0; i < nodo.padre.valores.length && subido == false; i++) {
                if (nodo.padre.valores[i] == null) {
                    nodo.padre.valores[i] = nodo.valores[0];
                    subido = true;
                    nodo.valores[0] = null;
                    ordenar(nodo.padre.valores);
                }
            }
            int posHijos = 0;
            for (int i = 0; i < 2 * grado + 3; i++) {
                if (nodo.padre.nodo[i] != null) {
                    posHijos++;
                } else {
                    break;
                }
            }
            nodo.padre.nodo[posHijos] = nodo.nodo[0];
            nodo.padre.nodo[posHijos + 1] = nodo.nodo[1];
            nodo.padre.nodo[posHijos].padre = nodo.padre;
            nodo.padre.nodo[posHijos + 1].padre = nodo.padre;
            int aqui = 0;
            for (int i = 0; i < 2 * grado + 3 && nodo.padre.nodo[i] != null; i++) {
                if (nodo.padre.nodo[i].valores[0] == nodo.valores[0]) {
                    aqui = i;
                    break;
                }
            }
            Nodo papa = nodo.padre;
            nodo = null;
            int j = aqui;
            while (j < 2 * grado + 2 && papa.nodo[j] != null && papa.nodo[j + 1] != null) {
                papa.nodo[j] = papa.nodo[j + 1];
                j++;
            }
            papa.nodo[j] = null;
            ordenar(papa.valores);
            ordenarNodos(papa);
            if (papa.valores[2 * grado] != null) {
                split(papa);
            }
        }
    }

    public void graficar() {
        
        String direccionPng = "";
        String direccionDot = "";
        
        try {
            ProcessBuilder pbuilder;
            pbuilder = new ProcessBuilder("dot", "-Tpng", "-o", direccionPng, direccionDot);
            pbuilder.redirectErrorStream(true);
            //Ejecuta el proceso
            pbuilder.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar(String valor) { //elimina de la lista el valor y vuelve a crear el arbol
        boolean encontrado = false;
        int j = 0;
        for (int i = 0; i < ingresados.size() && !encontrado; i++) {
            if (ingresados.get(i).getIsbn().compareToIgnoreCase(valor) == 0) {
                encontrado = true;
                j = i;
            }
        }
        if (encontrado == true) {
            ingresados.remove(j);
        } else {
            System.out.println("El valor a eliminar no se encuentra en el arbol B");
        }
        ArrayList<Libro> auxiliar = ingresados;
        ingresados = new ArrayList<Libro>();
        primerNodo = new Nodo();
        primerNodo.tengoHijos = false;
        for (int k = 0; k < auxiliar.size(); k++) {
            Libro y = auxiliar.get(k);
            insertar(y);
        }
    }
    /*
     public boolean buscar(int valor){
     boolean esta = false;
     for(int i = 0; i < Lista.ingresados.size() && !esta; i++){
     if(Lista.ingresados.get(i) == valor){
     esta = true;
     System.out.println("El elemento buscado si se encuentra en el arbol B");
     return esta;
     }
     }
     System.out.println("El elemento buscado no se encuentra en el arbol B");
     return false;
     }
     */

    public Libro busqueda_isbn(Nodo nodo, String isbn) {

        if (nodo != null) {
            for (int i = 0; i < 5; i++) {
                if (nodo.valores[i] != null) {
                    if (nodo.valores[i].getIsbn().compareToIgnoreCase(isbn) == 0) {
                        return nodo.valores[i];
                    }
                }
            }
            if (nodo.valores[0] != null && nodo.valores[1] == null) {
                if (isbn.compareToIgnoreCase(nodo.valores[0].getIsbn()) < 0) {
                    return busqueda_isbn(nodo.nodo[0], isbn);
                } else if (isbn.compareToIgnoreCase(nodo.valores[0].getIsbn()) > 0) {
                    return busqueda_isbn(nodo.nodo[1], isbn);
                }
            }

            if (nodo.valores[0] != null && nodo.valores[1] != null) {
                if (isbn.compareToIgnoreCase(nodo.valores[0].getIsbn()) < 0) {
                    return busqueda_isbn(nodo.nodo[0], isbn);
                } else if (isbn.compareToIgnoreCase(nodo.valores[0].getIsbn()) > 0 && isbn.compareToIgnoreCase(nodo.valores[1].getIsbn()) < 0) {
                    return busqueda_isbn(nodo.nodo[1], isbn);
                } else if (isbn.compareToIgnoreCase(nodo.valores[1].getIsbn()) > 0 && nodo.valores[2] == null) {
                    return busqueda_isbn(nodo.nodo[2], isbn);
                }
            }

            if (nodo.valores[1] != null && nodo.valores[2] != null) {
                if (isbn.compareToIgnoreCase(nodo.valores[1].getIsbn()) > 0 && isbn.compareToIgnoreCase(nodo.valores[2].getIsbn()) < 0) {
                    return busqueda_isbn(nodo.nodo[2], isbn);
                } else if (isbn.compareToIgnoreCase(nodo.valores[2].getIsbn()) > 0 && nodo.valores[3] == null) {
                    return busqueda_isbn(nodo.nodo[3], isbn);
                }
            }

            if (nodo.valores[2] != null && nodo.valores[3] != null) {
                if (isbn.compareToIgnoreCase(nodo.valores[2].getIsbn()) > 0 && isbn.compareToIgnoreCase(nodo.valores[3].getIsbn()) < 0) {
                    return busqueda_isbn(nodo.nodo[3], isbn);
                } else if (isbn.compareToIgnoreCase(nodo.valores[3].getIsbn()) > 0) {
                    return busqueda_isbn(nodo.nodo[4], isbn);
                }
            }
        }
        return null;
    }

    public boolean pertenece_categoria(Nodo nodo, String isbn) {

        if (nodo != null) {
            for (int i = 0; i < 5; i++) {
                if (nodo.valores[i] != null) {
                    if (nodo.valores[i].getIsbn().compareToIgnoreCase(isbn) == 0) {
                        return true;
                    }
                }
            }
            if (nodo.valores[0] != null && nodo.valores[1] == null) {
                if (isbn.compareToIgnoreCase(nodo.valores[0].getIsbn()) < 0) {
                    return pertenece_categoria(nodo.nodo[0], isbn);
                } else if (isbn.compareToIgnoreCase(nodo.valores[0].getIsbn()) > 0) {
                    return pertenece_categoria(nodo.nodo[1], isbn);
                }
            }

            if (nodo.valores[0] != null && nodo.valores[1] != null) {
                if (isbn.compareToIgnoreCase(nodo.valores[0].getIsbn()) < 0) {
                    return pertenece_categoria(nodo.nodo[0], isbn);
                } else if (isbn.compareToIgnoreCase(nodo.valores[0].getIsbn()) > 0 && isbn.compareToIgnoreCase(nodo.valores[1].getIsbn()) < 0) {
                    return pertenece_categoria(nodo.nodo[1], isbn);
                } else if (isbn.compareToIgnoreCase(nodo.valores[1].getIsbn()) > 0 && nodo.valores[2] == null) {
                    return pertenece_categoria(nodo.nodo[2], isbn);
                }
            }

            if (nodo.valores[1] != null && nodo.valores[2] != null) {
                if (isbn.compareToIgnoreCase(nodo.valores[1].getIsbn()) > 0 && isbn.compareToIgnoreCase(nodo.valores[2].getIsbn()) < 0) {
                    return pertenece_categoria(nodo.nodo[2], isbn);
                } else if (isbn.compareToIgnoreCase(nodo.valores[2].getIsbn()) > 0 && nodo.valores[3] == null) {
                    return pertenece_categoria(nodo.nodo[3], isbn);
                }
            }

            if (nodo.valores[2] != null && nodo.valores[3] != null) {
                if (isbn.compareToIgnoreCase(nodo.valores[2].getIsbn()) > 0 && isbn.compareToIgnoreCase(nodo.valores[3].getIsbn()) < 0) {
                    return pertenece_categoria(nodo.nodo[3], isbn);
                } else if (isbn.compareToIgnoreCase(nodo.valores[3].getIsbn()) > 0) {
                    return pertenece_categoria(nodo.nodo[4], isbn);
                }
            }
        }
        return false;
    }

    public void pertenece_categoriaN(Nodo nodo, String nombre) {

        if (nodo != null) {
            for (int i = 0; i < 5; i++) {
                if (nodo.valores[i] != null) {
                    if (nodo.valores[i].getTitulo().compareToIgnoreCase(nombre) == 0 || nodo.valores[i].getTitulo().contains(nombre)) {
                        coincidencias++;
                    }
                }
            }
            for (int i = 0; i < 5; i++) {
                if (nodo.nodo[i] != null) {
                    pertenece_categoriaN(nodo.nodo[i], nombre);
                }
            }
        }
    }

    public void busqueda_nombre(Nodo nodo, String nombre) {

        if (nodo != null) {
            for (int i = 0; i < 5; i++) {
                if (nodo.valores[i] != null) {
                    if (nodo.valores[i].getIsbn().compareToIgnoreCase(nombre) == 0 || nodo.valores[i].getTitulo().contains(nombre)) {
                        ArbolAvl.resultado_busqueda.insertar(new NodoSimpleB(nodo.valores[i]));
                    }
                }
            }
            for (int i = 0; i < 5; i++) {
                if (nodo.nodo[i] != null) {
                    busqueda_nombre(nodo.nodo[i], nombre);
                }
            }
        }
    }

    public void mostrar_todos(Nodo nodo) {

        if (nodo != null) {
            for (int i = 0; i < 5; i++) {
                if (nodo.valores[i] != null) {
                    ArbolAvl.resultado_busqueda.insertar(new NodoSimpleB(nodo.valores[i]));
                }
            }
            for (int i = 0; i < 5; i++) {
                if (nodo.nodo[i] != null) {
                    mostrar_todos(nodo.nodo[i]);
                }
            }
        }
    }

    public void mostrar() {
        mostrar_todos(primerNodo);
    }

    public Libro buscar(String isbn) {
        return busqueda_isbn(primerNodo, isbn);
    }

    public boolean perteneceCategoria(String isbn) {
        return pertenece_categoria(primerNodo, isbn);
    }

    public void buscar_nombre(String nombre) {
        busqueda_nombre(primerNodo, nombre);
    }

    public void perteneceCategoriaN(String nombre) {
        coincidencias = 0;
        pertenece_categoriaN(primerNodo, nombre);
    }

    public String recorrer(Nodo nodo) {

        arbol += "\n";
        for (int i = 0; i < 2 * grado + 1; i++) {
            if (nodo.nodo[i] != null) {
                if (i == 0) {
                    nivel++;
                    imprimir = 1;
                } else {
                    imprimir++;
                }
                recorrer(nodo.nodo[i]);
            }

            boolean inicio = true;
            boolean fin = false;

            for (int j = 0; nodo.nodo[i] != null && j < nodo.nodo[i].valores.length; j++) {
                if (nodo.nodo[i].valores[j] != null) {
                    if (inicio) {
                        arbol += "[ ";
                        inicio = false;
                        fin = true;
                    }
                    arbol += nodo.nodo[i].valores[j] + ", ";
                }
            }

            if (fin) {
                arbol += " ]";
                fin = false;
            }

        }
        if (arbol.length() > (2 * grado + 3) * 4) {
            System.out.println(arbol);
            return arbol;
        }
        return arbol;
    }

    public void generarDot() {

        arbol += "digraph g {\n"
                + "node [shape = record,height=.1];";
        graficar(primerNodo);
        indexar(primerNodo);
        arbol += "}\n";

        String ruta = "b.dot";
        String rutapng = "b.png";
        try {
            File file = new File(ruta);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(arbol);
            bw.close();
            ProcessBuilder pbuilder;
            pbuilder = new ProcessBuilder("dot" , "-Tpng", "-o", rutapng , ruta );
            pbuilder.redirectErrorStream(true);
            pbuilder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println(arbol);
    }
    
    
    public String graficar(Nodo actual) {

        arbol += "node" + contadorNodos + "[label=\"";
        actual.indice = "node" + contadorNodos;
        for (int i = 0; i < 5; i++) {
            if (actual.valores[i] != null) {
                arbol += "<f" + i + ">|" + actual.valores[i].getIsbn() + " : " + actual.valores[i].getTitulo() + "| ";
            }
        }
        arbol += "\"];\n";
        contadorNodos++;
        for (int i = 0; i < 5; i++) {
            if (actual.nodo[i] != null) {
                graficar(actual.nodo[i]);
            }
        }
        
        
        return arbol;
    }

    public void indexar(Nodo actual) {
        for (int i = 0; i < 5; i++) {
            if (actual.nodo[i] != null) {
                arbol += actual.indice + "->" + actual.nodo[i].indice + ";\n";
                indexar(actual.nodo[i]);
            }
        }

    }

    public String imprimir_valores(Nodo actual) {

        val_internos += "[";
        for (int i = 0; i < 5; i++) {
            if (actual.valores[i] != null) {
                val_internos += actual.valores[i] + ",";
            }
        }
        val_internos += "]  ";
        return "";
    }

    public String llamarRecorrer() {
        String mostrar = recorrer(primerNodo);
        nivel = 1;
        imprimir = 1;
        return mostrar;
    }

    public boolean esNumero(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
