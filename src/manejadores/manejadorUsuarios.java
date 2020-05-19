/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejadores;

import estructuras.NodoAVL;
import estructuras.NodoSimple;
import estructuras.TablaDispersa;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import objetos.SesionActiva;
import objetos.Usuario;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objetos.Libro;
import objetos.SesionActiva;
import org.json.JSONObject;
import org.json.*;

/**
 *
 * @author USUARIO
 */
public class manejadorUsuarios {

    public boolean login(int carnet, String contra) {
        NodoSimple aux = SesionActiva.usuarios.buscar(carnet);
        if (aux != null) {
            if (aux.getValor().getPassword().compareTo(contra) == 0) {
                SesionActiva.usuario_sesion = aux.getValor();
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean registrar(int carnet, String nombre, String apellido, String carrera, String pass) {
        NodoSimple aux = SesionActiva.usuarios.buscar(carnet);
        if (aux == null) {
            Usuario nuevo = new Usuario(carnet, nombre, apellido, carrera, pass);
            SesionActiva.usuarios.insertar(nuevo);
            jsonUsuarios(carnet, nombre, apellido, carrera, pass, "CREAR_USUARIO");
            return true;
        }
        return false;
    }

    public boolean editar(int carnet, String nombre, String apellido, String carrera, String pass) {
        if (carnet == SesionActiva.usuario_sesion.getCarnet()) {
            jsonUsuarios(carnet, nombre, apellido, carrera, pass, "EDITAR_USUARIO");
            return SesionActiva.usuarios.actualizar(carnet, nombre, apellido, carrera, pass);
        }
        return false;
    }

    public boolean eliminar(int carnet) {
        if (carnet == SesionActiva.usuario_sesion.getCarnet()) {
            SesionActiva.libros.eliminarPropietario(carnet);
            for (int i = 0; i < SesionActiva.libros.categorias_usuario.size(); i++) {
                NodoAVL aux = SesionActiva.libros.categorias_usuario.get(i);
                SesionActiva.libros.eliminarCategoria(aux.getCategoria());
                System.out.println(aux.getCategoria());
            }
            return SesionActiva.usuarios.eliminar(carnet);
        }
        return false;
    }

    public boolean carga_masiva() {
        String first = "C:\\Cargas\\usuarios.json";
        try {
            String contents = new String(Files.readAllBytes(Paths.get(first)), StandardCharsets.UTF_8);
            JSONObject o = new JSONObject(contents);
            JSONArray usuarios = o.getJSONArray("Usuarios");
            for (int i = 0; i < usuarios.length(); i++) {
                JSONObject js = (JSONObject) usuarios.getJSONObject(i);
//                int carnet = Integer.parseInt(js.getString("Carnet"));
                int carnet = js.getInt("Carnet");
                String nombre = js.getString("Nombre");
                String apellido = js.getString("Apellido");
                String carrera = js.getString("Carrera");
                String pass = js.getString("Password");
                System.out.println(js.toString());
                registrar(carnet, nombre, apellido, carrera, pass);
            }
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return false;
    }

    private void jsonUsuarios(int carnet, String nombre, String apellido, String carrera, String pass, String accion) {
        JSONObject creacion = new JSONObject();
        JSONArray crearUsu = new JSONArray();
        JSONObject cdata = new JSONObject();
        cdata.put("Carnet", carnet);
        cdata.put("Nombre", nombre);
        cdata.put("Apellido", apellido);
        cdata.put("Carrera", carrera);
        cdata.put("Password", pass);
        crearUsu.put(cdata);
        creacion.put(accion, crearUsu);
        SesionActiva.data.put(creacion);
    }

    public void graficar() {
        SesionActiva.usuarios.generarDot();
    }

}
