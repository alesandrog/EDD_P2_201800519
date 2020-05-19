/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author USUARIO
 */
public class Bloque {

    private int index;
    private String timestamp;
    private int nonce;
    private JSONObject data;
    private String previous_hash;
    private String hash;
    private Bloque siguiente;
    private Bloque anterior;

    public Bloque() {
        this.siguiente = this.anterior = null;
    }

    public Bloque(JSONObject data) {
        this.data = data;
        Timestamp ts = new Timestamp(new Date().getTime());
        SimpleDateFormat format = new SimpleDateFormat("DD-MM-YY-::HH:MM:SS");
        this.timestamp = format.format(ts);
    }

    public Bloque(int index, String timestamp, int nonce, String previous_hash, String hash) {
        this.index = index;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.previous_hash = previous_hash;
        this.hash = hash;
        this.siguiente = this.anterior = null;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public String getPrevious_hash() {
        return previous_hash;
    }

    public void setPrevious_hash(String previous_hash) {
        this.previous_hash = previous_hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Bloque getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Bloque siguiente) {
        this.siguiente = siguiente;
    }

    public Bloque getAnterior() {
        return anterior;
    }

    public void setAnterior(Bloque anterior) {
        this.anterior = anterior;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

}
