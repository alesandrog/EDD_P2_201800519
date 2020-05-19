/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

/**
 *
 * @author USUARIO
 */
public class Libro {
    
    private String isbn;
    private String titulo;
    private String autor;
    private String editorial;
    private String anio;
    private String edicion;
    private String categoria;
    private String idioma;
    private int propietario;

    public Libro(String isbn){
    this.isbn = isbn;
    }
    
    public Libro(String isbn, String titulo, String autor, String editorial, String anio, String edicion, String categoria, String idioma, int propietario) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anio = anio;
        this.edicion = edicion;
        this.categoria = categoria;
        this.idioma = idioma;
        this.propietario = propietario;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getPropietario() {
        return propietario;
    }

    public void setPropietario(int propietario) {
        this.propietario = propietario;
    }
    
    
    
}
