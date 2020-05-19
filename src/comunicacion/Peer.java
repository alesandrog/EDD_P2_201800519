/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comunicacion;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author USUARIO
 */
public class Peer extends Thread{
    private String host;
    private int puerto;
    private Object mensaje;
    
    public Peer( String host, int puerto , Object mensaje){
        this.host = host;
        this.puerto = puerto;
        this.mensaje = mensaje;
    }
    
    @Override
    public void run(){
        ObjectOutputStream out;
        try{
            Socket socket = new Socket( host , puerto);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(mensaje);
            socket.close();
        }catch(Exception ex){
        
        }
    }
}
