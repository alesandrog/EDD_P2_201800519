/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejadores;

import estructuras.ArbolAvl;
import estructuras.NodoAVL;
import estructuras.NodoSimpleB;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import objetos.Libro;
import objetos.SesionActiva;
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
public class manejadorLibros {

    public void registrar_categoria(String categoria) {
        SesionActiva.libros.insertar(categoria);
    }

    public boolean editar_categoria(String categoria, String nueva_categoria) {
        NodoAVL aux = SesionActiva.libros.buscar_categoria(categoria);
        if (aux != null) {
            aux.setCategoria(nueva_categoria);
            return true;
        }
        return false;
    }

    public void insertar_libro(String isbn, String titulo, String autor, String editorial, String anio, String edicion, String categoria, String idioma, int propietario) {
        NodoAVL aux = SesionActiva.libros.buscar_categoria(categoria);
        if (aux == null) {
            SesionActiva.libros.insertar(categoria);
            aux = SesionActiva.libros.buscar_categoria(categoria);
        }
        System.out.println(aux.getCategoria());

        Libro libro = new Libro(isbn, titulo, autor, editorial, anio, edicion, categoria, idioma, propietario);
        SesionActiva.libros.insertarLibro(categoria, libro);
        System.out.println(libro.getTitulo());
        System.out.println("*************************************");
        jsonLibros(isbn, titulo, autor, editorial, anio, edicion, categoria, idioma, propietario, "CREAR_LIBRO");
    }

    public void libro_isbn(String isbn) {
        SesionActiva.libros.buscar_isbn(isbn);
    }

    public void libro_nombre(String nombre) {
        SesionActiva.libros.buscar_nombre(nombre);
    }


    
    public boolean carga_masiva() {
        String first = "C:\\Cargas\\Libros.json";
        //String contents = new String(Files.readAllBytes(Paths.get(first)));
//        String contents = "";
/*        try {
            String str;
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(first), "UTF-8"));
            while ((str = bufReader.readLine()) != null) {
                contents += str;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el archivo");
        }*/
        try {
           // JSONParser parser = new JSONParser();
            
            String contents = new String(Files.readAllBytes(Paths.get(first)), StandardCharsets.UTF_8);
            JSONObject o = new JSONObject(contents);
            JSONArray libros = o.getJSONArray("libros");
            for (int i = 0; i < libros.length(); i++) {
                JSONObject js = (JSONObject) libros.getJSONObject(i);
                String isbn = "" + js.getNumber("ISBN");
                String idioma = js.getString("Idioma");
                String year = "" + js.getNumber("Año");
                String titulo = js.getString("Titulo");
                String editorial = js.getString("Editorial");
                String autor = js.getString("Autor");
                String edicion = "" + js.getNumber("Edicion");
                String categoria = js.getString("Categoria");

                insertar_libro(isbn, titulo, autor, editorial, year, edicion, categoria, idioma, SesionActiva.usuario_sesion.getCarnet());
            }
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return false;
    }

    public void busqueda_categoria(String categoria) {
        try{
        SesionActiva.libros.libros_categoria(categoria).mostrar();
        
        }catch(Exception ex){
        JOptionPane.showMessageDialog(null,"Error en la busqueda");
        }
    }

    private void jsonLibros(String isbn, String titulo, String autor, String editorial, String anio, String edicion, String categoria, String idioma, int propietario, String accion) {
        JSONObject creacion = new JSONObject();
        JSONArray crearLibro = new JSONArray();
        JSONObject cdata = new JSONObject();
        cdata.put("ISBN", isbn);
        cdata.put("Año", anio);
        cdata.put("Idioma", idioma);
        cdata.put("Titulo", titulo);
        cdata.put("Editorial", editorial);
        cdata.put("Autor", autor);
        cdata.put("Edicion", edicion);
        cdata.put("Categoria", categoria);
        cdata.put("Propietario", propietario);
        crearLibro.put(cdata);
        creacion.put(accion, crearLibro);
        SesionActiva.data.put(creacion);
    }

    public boolean eliminar_categoria(String categoria) {
        NodoAVL aux = SesionActiva.libros.buscar_categoria(categoria);
        if (aux != null) {
            if (aux.propietario == SesionActiva.usuario_sesion.getCarnet()) {
                SesionActiva.libros.eliminarCategoria(categoria);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No posee permisos para eliminar");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro la categoria");
            return false;
        }
    }

    public void eliminar_libro(String isbn, String categoria) {
        NodoAVL aux = SesionActiva.libros.buscar_categoria(categoria);
        aux.getLibros().eliminar(isbn);

    }

    public boolean eliminar_libro(String isbn) {
        SesionActiva.libros.buscar_isbn(isbn);
        NodoSimpleB aux = ArbolAvl.resultado_busqueda.inicio.getSiguiente();
        NodoAVL aux2 = SesionActiva.libros.buscar_categoria(aux.getValor().getCategoria());
        jsonLibros(isbn, aux.getValor().getTitulo(), aux.getValor().getAutor(), aux.getValor().getEditorial(), aux.getValor().getAnio(), aux.getValor().getEdicion(), aux.getValor().getCategoria(), aux.getValor().getIdioma(), aux.getValor().getPropietario(), "ELIMINAR_LIBRO");
        if (aux != null) {
            if (aux.getValor().getPropietario() == SesionActiva.usuario_sesion.getCarnet()) {
                aux2.getLibros().eliminar(isbn);
                aux2.totalLibros--;
                if (aux2.totalLibros <= 0) {
                    eliminar_categoria(aux2.getCategoria());
                }
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No posee permisos para eliminar");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro la categoria");
            return false;
        }

    }

    public void graficalAVL() {
        String ruta = "avl.dot";
        String rutapng = "avl.png";
        SesionActiva.libros.generarDot();
        try {
            File file = new File(ruta);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(SesionActiva.libros.arbol);
            bw.close();
            ProcessBuilder pbuilder;
            pbuilder = new ProcessBuilder("dot", "-Tpng", "-o", rutapng, ruta);
            pbuilder.redirectErrorStream(true);
            pbuilder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
