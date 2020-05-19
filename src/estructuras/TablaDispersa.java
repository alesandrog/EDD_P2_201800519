/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import objetos.Usuario;
import estructuras.Casilla;
import estructuras.NodoSimple;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import objetos.SesionActiva;

/**
 *
 * @author USUARIO
 */
public class TablaDispersa {

    private static final int casillas = 45;
    public static Casilla[] tabla_hash = new Casilla[casillas];

    public TablaDispersa() {
        for (int i = 0; i < casillas; i++) {
            Casilla casilla = new Casilla();
            tabla_hash[i] = casilla;
        }

    }

    public int direccion(int carnet) {
        int a = carnet % casillas;
        System.out.println(a);
        return carnet % casillas;
    }

    public void insertar(Usuario nuevo) {
        tabla_hash[direccion(nuevo.getCarnet())].getLista_colision().insertar(new NodoSimple(nuevo));
    }

    public boolean actualizar(int carnet, String nombre, String apellido, String carrera, String password) {
        NodoSimple aux;
        aux = tabla_hash[direccion(carnet)].getLista_colision().buscar(carnet);
        if (aux != null) {
            aux.getValor().setNombre(nombre);
            aux.getValor().setApellido(apellido);
            aux.getValor().setCarrera(carrera);
            aux.getValor().setPassword(password);
            return true;
        }

        return false;
    }

    public boolean eliminar(int carnet) {
        return tabla_hash[direccion(carnet)].getLista_colision().eliminar(carnet);
    }

    public NodoSimple buscar(int carnet) {
        NodoSimple aux;
        aux = tabla_hash[direccion(carnet)].getLista_colision().buscar(carnet);
        return aux;
    }

    public void generarDot() {
        String dot = "";
        int contador = 1;
        dot += "digraph G {\n";
        dot += "nodesep = .05;\n";
        dot += "rankdir=LR;\n";
        dot += "node [shape=record,width=.1,height=.1];\n";
        dot += "node0 [label = \"";
        for (int i = 0; i < 45; i++) {
            tabla_hash[i].getLista_colision().getInicio().identificador = "f" + i;
            dot += "<f" + i + "> |";

        }
        dot += "\", height=2.5];\n";
        dot += "node[width = 1.5];\n";

        for (int i = 0; i < 45; i++) {
            if (tabla_hash[i].getLista_colision().getInicio().getValor().getCarnet() != 0) {
                NodoSimple aux = tabla_hash[i].getLista_colision().getInicio();
                dot += "node" + contador + "[label = \"{<n>";
                while (aux != null) {
                    dot += aux.getValor().getCarnet() + "|";
                    aux = aux.getSiguiente();
                }
                dot += "<p> }\"];\n";
                tabla_hash[i].getLista_colision().getInicio().nodos = "node" + contador;
                contador++;
            }
        }

        for (int i = 0; i < 45; i++) {
            if (tabla_hash[i].getLista_colision().getInicio().getValor().getCarnet() != 0) {
                NodoSimple aux = tabla_hash[i].getLista_colision().getInicio();
                dot += "node0:" + aux.identificador + "->" + aux.nodos + ":n;\n";
            }
        }
        
        dot += "}";
        String ruta = "hash.dot";
        try {
            File file = new File(ruta);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(dot);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(dot);
    }

}
