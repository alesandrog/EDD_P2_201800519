/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comunicacion;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import objetos.NodoRed;
import objetos.SesionActiva;
import ui.configuracionSocket;

/**
 *
 * @author USUARIO
 */
public class Server extends Thread {

    private ServerSocket serverSocket;
    LinkedList<ClientHandler> clientes;
    public String puerto;
    static int correlativo;
    public String ip;
    
    public Server(String puerto ,String ip) {
        correlativo = 0;
        this.puerto = puerto;
        this.ip = ip;
        clientes = new LinkedList<>();
        SesionActiva.nodosRed.add(ip + ":" + puerto);
        this.start();
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(Integer.valueOf(puerto));
            System.out.println("Servidor escuchando en puerto: " + puerto + "\n");
            configuracionSocket.jTextArea1.append("Servidor escuchando en puerto: " + puerto + "\n");
            while (true) {
                ClientHandler h;
                Socket socket;
                socket = serverSocket.accept();
                System.out.println("Nueva conexion entrante: " + socket);                
                configuracionSocket.jTextArea1.append("Cliente Nuevo: " + socket.getPort() + "\n");
     //           registrarNodo(socket.getLocalAddress().toString(), socket.getPort() + "");
                h = new ClientHandler(socket, this);
                h.start();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El servidor no se ha podido iniciar,\n"
                    + "puede que haya ingresado un puerto incorrecto.\n"
                    + "Esta aplicación se cerrará.");
            System.exit(0);
        }
    }
    
    LinkedList<String> getUsuariosConectados() {
        LinkedList<String> usuariosConectados = new LinkedList<>();
        clientes.stream().forEach(c -> usuariosConectados.add(c.getIdentificador()));
        return usuariosConectados;
    }
}
