/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comunicacion;

/**
 *
 * @author USUARIO
 */
import estructuras.Bloque;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import objetos.SesionActiva;
import org.json.JSONArray;
import org.json.JSONObject;
import ui.configuracionSocket;

public class Client extends Thread {

    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private String identificador;
    private boolean escuchando;
    private String host;
    private final int puerto;

    public Client(String host, Integer puerto, String nombre) {

        this.host = host;
        this.puerto = puerto;
        this.identificador = nombre;
        escuchando = true;
        this.start();
    }

    public void run() {
        try {
            socket = new Socket(host, puerto);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            configuracionSocket.jTextArea1.append("***ESTADO CONEXION: CONECTADO***\n");
            System.out.println("Conexion exitosa!!!!");
            this.enviarSolicitudConexion(identificador, this.host, SesionActiva.servidor.puerto + "");
            this.escuchar();
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "Conexión rehusada, servidor desconocido,\n"
                    + "puede que haya ingresado una ip incorrecta\n"
                    + "o que el servidor no este corriendo.\n"
                    + "Esta aplicación se cerrará.");

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Conexión rehusada, error de Entrada/Salida,\n"
                    + "puede que haya ingresado una ip o un puerto\n"
                    + "incorrecto, o que el servidor no este corriendo.\n"
                    + "Esta aplicación se cerrará.");

        }

    }

    public void desconectar() {
        try {
            objectOutputStream.close();
            objectInputStream.close();
            socket.close();
            escuchando = false;
        } catch (Exception e) {
            System.err.println("Error al cerrar los elementos de comunicación del cliente.");
        }
    }

    public void leer_json(String b) {
        try {
            JSONObject json = new JSONObject(b);
            JSONArray data = json.getJSONArray("DATA");
            //JSONObject d = data.getJSONObject(0);
            for (int i = 0; i < data.length(); i++) {
                configuracionSocket.jTextArea1.append(procesar_json(data.getJSONObject(i)) + "\n");
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public String procesar_json(JSONObject operacion) {

        try {
            String op = operacion.getJSONArray("CREAR_USUARIO").toString();
            return "CREAR_USUARIO";
        } catch (Exception e) {
        }

        try {
            String op = operacion.getJSONArray("EDITAR_USUARIO").toString();
            return "EDITAR_USUARIO";
        } catch (Exception e) {
        }

        try {
            String op = operacion.getJSONArray("CREAR_LIBRO").toString();
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

    /**
     * Método que envia un determinado mensaje hacia el servidor.
     *
     * @param cliente_receptor
     * @param mensaje
     */
    public void enviarMensaje(String mensaje) {
        LinkedList<String> lista = new LinkedList<>();
        //tipo
        lista.add("MENSAJE");
        //cliente emisor
        lista.add(identificador);
        //mensaje que se desea transmitir
        lista.add(mensaje);
        try {
            objectOutputStream.writeObject(lista);
        } catch (IOException ex) {
            System.out.println("Error de lectura y escritura al enviar mensaje al servidor.");
        }
    }

    public void enviarjson(String json) {
        try {
            objectOutputStream.writeObject(json);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public void noti(LinkedList<String> lista){
    try {
            objectOutputStream.writeObject(lista);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void escuchar() {
        try {
            while (escuchando) {
                Object aux = objectInputStream.readObject();
                if (aux != null) {
                    if (aux instanceof LinkedList) {
                        //Si se recibe una LinkedList entonces se procesa
                        ejecutar((LinkedList<String>) aux);
                    } else if (aux instanceof String) {
                        leer_json((String) aux);
                    } else {
                        configuracionSocket.jTextArea1.append("ninguno");
                    }
                } else {
                    System.err.println("Se recibió un null a través del socket");
                }
            }
        } catch (Exception e) {
            /* JOptionPane.showMessageDialog(ventana, "La comunicación con el servidor se ha\n"
             + "perdido, este chat tendrá que finalizar.\n"
             + "Esta aplicación se cerrará.");
             */

        }
    }

    /**
     * Método que ejecuta una serie de instruccines dependiendo del mensaje que
     * el cliente reciba del servidor.
     *
     * @param lista
     */
    public void ejecutar(LinkedList<String> lista) {
        // 0 - El primer elemento de la lista es siempre el tipo
        String tipo = lista.get(0);
        switch (tipo) {
            case "CONEXION_ACEPTADA":
                // 1      - Identificador propio del nuevo usuario
                // 2 .. n - Identificadores de los clientes conectados actualmente
                identificador = lista.get(1);
                //ventana.sesionIniciada(identificador);
                configuracionSocket.jTextArea1.append("Usuarios Conectados:\n");
                for (int i = 2; i < lista.size(); i++) {
                    configuracionSocket.jTextArea1.append(lista.get(i));
                    //  ventana.addContacto(lista.get(i));
                }
                break;
            case "NUEVO_USUARIO_CONECTADO":
                // 1      - Identificador propio del cliente que se acaba de conectar
                //ventana.addContacto(lista.get(1));
                configuracionSocket.jTextArea1.append("-----USUARIO NUEVO" + lista.get(1) + "------\n");
                break;
            case "USUARIO_DESCONECTADO":
                // 1      - Identificador propio del cliente que se acaba de conectar
                //ventana.eliminarContacto(lista.get(1));
                break;
            case "MENSAJE":
                // 1      - Cliente emisor
                // 2      - Cliente receptor
                // 3      - Mensaje
                //ventana.addMensaje(lista.get(1), lista.get(3));
                System.out.println(lista.get(1) + " --> " + lista.get(2));
                configuracionSocket.jTextArea1.append("Recibido");
                configuracionSocket.jTextArea1.append(lista.get(1) + " --> " + lista.get(2));
                break;
            default:
                break;
        }
    }

    /**
     * Al conectarse el cliente debe solicitar al servidor que lo agregue a la
     * lista de clientes, para ello se ejecuta este método.
     *
     * @param identificador
     */
    private void enviarSolicitudConexion(String identificador, String ip, String puerto) {
        LinkedList<String> lista = new LinkedList<>();
        //tipo
        lista.add("SOLICITUD_CONEXION");
        //cliente solicitante
        lista.add(identificador);
        lista.add(ip);
        lista.add(puerto);
        try {
            objectOutputStream.writeObject(lista);
        } catch (IOException ex) {
            System.out.println("Error de lectura y escritura al enviar mensaje al servidor.");
        }
    }

    /**
     * Cuando se cierra una ventana cliente, se debe notificar al servidor que
     * el cliente se ha desconectado para que lo elimine de la lista de clientes
     * y todos los clientes lo eliminen de su lista de contactos.
     */
    void confirmarDesconexion() {
        LinkedList<String> lista = new LinkedList<>();
        //tipo
        lista.add("SOLICITUD_DESCONEXION");
        //cliente solicitante
        lista.add(identificador);
        try {
            objectOutputStream.writeObject(lista);
        } catch (IOException ex) {
            System.out.println("Error de lectura y escritura al enviar mensaje al servidor.");
        }
    }

    /**
     * Método que retorna el identificador del cliente que es único dentro del
     * chat.
     *
     * @return
     */
    String getIdentificador() {
        return identificador;
    }

}
