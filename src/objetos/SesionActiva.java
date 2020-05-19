/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import comunicacion.Client;
import comunicacion.Cliente;
import comunicacion.Server;
import comunicacion.Servidor;
import estructuras.ArbolAvl;
import estructuras.TablaDispersa;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author USUARIO
 */
public class SesionActiva {

    public static TablaDispersa usuarios = new TablaDispersa();
    public static Usuario usuario_sesion;
    public static ArbolAvl libros = new ArbolAvl();
    public static Server servidor;
    public static Client cliente;
    public static JSONArray data = new JSONArray();
    public static JSONObject blockchain = new JSONObject();
    public static int contadorBloques = -1;
    public static String lastHash = "0000";
    public static LinkedList<String> nodosRed = new LinkedList<String>();
}
