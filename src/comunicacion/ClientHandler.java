/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comunicacion;

import estructuras.Bloque;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import objetos.NodoRed;
import objetos.SesionActiva;
import ui.configuracionSocket;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author USUARIO
 */
public class ClientHandler extends Thread {

    private final Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private final Server server;
    private String identificador;
    private boolean escuchando;

    public ClientHandler(Socket socket, Server server) {
        this.server = server;
        this.socket = socket;
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.err.println("Error en la inicialización del ObjectOutputStream y el ObjectInputStream");
        }
    }

    public void desconnectar() {
        try {
            socket.close();
            escuchando = false;
            System.out.println("Ya desconecte");
        } catch (IOException ex) {
            System.err.println("Error al cerrar el socket de comunicación con el cliente.");
        }
    }

    public void run() {
        try {
            escuchar();
        } catch (Exception ex) {
            System.err.println("Error al llamar al método readLine del hilo del cliente.");
        }
        desconnectar();
    }

    public void escuchar() {
        escuchando = true;
        while (escuchando) {
            try {
                Object aux = objectInputStream.readObject();
                if (aux instanceof LinkedList) {
                    ejecutar((LinkedList<String>) aux);
                } else if (aux instanceof String) {
                    configuracionSocket.jTextArea1.append("\n*****************BLOQUE ENTRANTE*****************\n");
                    leer_json((String) aux);
                } else {
                    configuracionSocket.jTextArea1.append("ninguno");
                }
            } catch (Exception e) {
                System.err.println("Error al leer lo enviado por el cliente.");
            }
        }
    }

    public void leer_json(String b) {
        try {
            JSONObject json = new JSONObject(b);
            configuracionSocket.jTextArea1.append("Leyendo JSON\n");
            JSONArray data = json.getJSONArray("DATA");
            //JSONObject d = data.getJSONObject(0);
            for (int i = 0; i < data.length(); i++) {
                configuracionSocket.jTextArea1.append(procesar_json(data.getJSONObject(i)) + "\n");
            }
            confirmarDesConexion();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public String procesar_json(JSONObject operacion) {

        try {
            JSONArray accion = operacion.getJSONArray("CREAR_USUARIO");
            String op = accion.toString();

            return "CREAR_USUARIO";
        } catch (Exception e) {
        }

        try {
            String op = operacion.getJSONArray("EDITAR_USUARIO").toString();
            return "EDITAR_USUARIO";
        } catch (Exception e) {
        }

        try {
            JSONArray accion = operacion.getJSONArray("CREAR_LIBRO");
            String op = accion.toString();
            JSONObject libro = (JSONObject) accion.get(0);
            libro.get("Idioma");
            configuracionSocket.jTextArea1.append(libro.get("Idioma") + "\n");
            return "CREAR_LIBRO";
        } catch (Exception e) {
        }

        try {
            String op = operacion.getJSONArray("CREAR_CATEGORIA").toString();
            return "CREAR_CATEGORIA";
        } catch (Exception e) {
        }

        try {
            String op = operacion.getJSONArray("ELIMINAR_USUARIO").toString();
            return "ELIMINAR_USUARIO";
        } catch (Exception e) {
        }

        try {
            String op = operacion.getJSONArray("ELIMINAR_LIBRO").toString();
            return "ELIMINAR_LIBRO";
        } catch (Exception e) {
        }

        try {
            String op = operacion.getJSONArray("ELIMINAR_CATEGORIA").toString();
            return "ELIMINAR_CATEGORIA";
        } catch (Exception e) {
        }

        return "No se pudo procesar";
    }

    public void ejecutar(LinkedList<String> lista) {
        // 0 - El primer elemento de la lista es siempre el tipo
        String tipo = lista.get(0);
        switch (tipo) {
            case "SOLICITUD_CONEXION":
                // 1 - Identificador propio del nuevo usuario
                confirmarConexion(lista.get(1), lista.get(2), lista.get(3));
                break;
            case "SOLICITUD_DESCONEXION":
                // 1 - Identificador propio del nuevo usuario
                confirmarDesConexion();
                break;
            case "MENSAJE":
                // 1      - Cliente emisor
                // 2      - Cliente receptor
                // 3      - Mensaje
                configuracionSocket.jTextArea1.append(lista.get(1) + "-->" + lista.get(2));
                LinkedList<String> listaaux = new LinkedList<String>();
                listaaux.add("MENSAJE");
                listaaux.add(this.identificador);
                listaaux.add("Respuesta de servidor");
                enviarMensaje(listaaux);
                break;

            case "ACTUALIZAR_NODOS":
                System.out.println("Anda piola el peer");
                confirmarDesConexion();
                break;
            case "PEER":
                System.out.println("Anda piola el peer");
                this.desconnectar();
                break;
            default:
                break;
        }
    }

    private void enviarMensaje(LinkedList<String> lista) {
        try {
            objectOutputStream.writeObject(lista);
        } catch (Exception e) {
            System.err.println("Error al enviar el objeto al cliente.");
        }
    }

    /**
     * Una vez conectado un nuevo cliente, este método notifica a todos los
     * clientes conectados que hay un nuevo cliente para que lo agreguen a sus
     * contactos.
     *
     * @param identificador
     */
    private void confirmarConexion(String identificador, String ip, String puerto) {
        Servidor.correlativo++;
        this.identificador = Servidor.correlativo + " - " + identificador;
        LinkedList<String> lista = new LinkedList<>();
        lista.add("CONEXION_ACEPTADA");
        lista.add(this.identificador);
        lista.addAll(SesionActiva.servidor.getUsuariosConectados());
        enviarMensaje(lista);
        System.out.println("\nNuevo cliente: " + this.identificador);
        configuracionSocket.jTextArea1.append("\nNuevo cliente: " + this.identificador + "\n");
//server.agregarLog("\nNuevo cliente: " + this.identificador);
        //enviar a todos los clientes el nombre del nuevo usuario conectado excepto a él mismo
        LinkedList<String> auxLista = new LinkedList<>();
        auxLista.add("NUEVO_USUARIO_CONECTADO");
        auxLista.add(this.identificador);
        registrarNodo(ip, puerto + "");

        SesionActiva.servidor.clientes
                .stream()
                .forEach(cliente -> cliente.enviarMensaje(auxLista));
        SesionActiva.servidor.clientes.add(this);
    }

    public void registrarNodo(String ip, String puerto) {
        System.out.println("Entre a registrar");
        if (SesionActiva.nodosRed.contains(ip + ":" + puerto) == false) {
            System.out.println("No lo tenia xd");
            SesionActiva.nodosRed.add(ip + ":" + puerto);
            System.out.println("info recibida");
            System.out.println(ip);
            System.out.println(puerto);
            //notificarNodo();
        }
    }

    public void notificarNodo() {

    }

    public void recibirNodos(LinkedList<String> lista) {
        System.out.println("Entre a recibir");
        for (int i = 1; i < lista.size(); i++) {
            if (SesionActiva.nodosRed.contains(lista.get(i)) == false) {
                System.out.println("No lo tenia xd");
                SesionActiva.nodosRed.add(lista.get(i));
                System.out.println(lista.get(i));
            } else {
                System.out.println("Ya lo tengo papu -----> " + lista.get(i));
            }
        }

        System.out.println("info recibida");
    }

    /**
     * Método que retorna el identificador único del cliente dentro del chat.
     *
     * @return
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Método que se invoca cuando el usuarioi al que atienede este hilo decide
     * desconectarse, si eso pasa, se tiene que informar al resto de los
     * usuarios que ya no pueden enviarle mensajes y que deben quitarlo de su
     * lista de contactos.
     */
    private void confirmarDesConexion() {
        LinkedList<String> auxLista = new LinkedList<>();
        auxLista.add("USUARIO_DESCONECTADO");
        auxLista.add(this.identificador);
        System.out.println("\nEl cliente \"" + this.identificador + "\" se ha desconectado.");
        configuracionSocket.jTextArea1.append("\nEl cliente \"" + this.identificador + "\" se ha desconectado.");
        // server.agregarLog("\nEl cliente \"" + this.identificador + "\" se ha desconectado.");
        this.desconnectar();
        for (int i = 0; i < server.clientes.size(); i++) {
            if (server.clientes.get(i).equals(this)) {
                server.clientes.remove(i);
                break;
            }
        }
        SesionActiva.servidor.clientes
                .stream()
                .forEach(h -> h.enviarMensaje(auxLista));
    }
    
    private void dsc(){
    }

}
