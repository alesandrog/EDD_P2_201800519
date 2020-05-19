/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejadores;

import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import objetos.SesionActiva;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author USUARIO
 */
public class manejadorBloques {

    int nonce = 0;
    String timestamp = "";

    public String timestamp() {
        Timestamp ts = new Timestamp(new Date().getTime());
        SimpleDateFormat format = new SimpleDateFormat("DD-MM-YY-::HH:MM:SS");
        timestamp = format.format(ts);
        return format.format(ts);
    }

    public String hash() {

        StringBuffer hexString = new StringBuffer();
        hexString = new StringBuffer();
        String bloque = SesionActiva.contadorBloques + timestamp() + SesionActiva.lastHash + SesionActiva.data.toString() + nonce;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(bloque.getBytes("UTF-8"));

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return hexString.toString();
    }

    public String minar() {
        nonce = 0;
        while (true) {
            String tryNonce = hash();
            System.out.println(tryNonce);
            if (tryNonce.substring(0, 4).equals("0000")) {
                return tryNonce;
            }
            nonce++;
        }
    }

    public JSONObject generarBloque() {
        String hash = minar();
        JSONObject f = new JSONObject();
        SesionActiva.contadorBloques++;
        String cont = SesionActiva.contadorBloques + "";
        f.put("INDEX", cont);
        f.put("TIMESTAMP", timestamp);
        f.put("NONCE", nonce);
        f.put("DATA", SesionActiva.data);
        f.put("PREVIOUSHASH", SesionActiva.lastHash);
        f.put("HASH", hash);

        System.out.println("Bloque");
        System.out.println(f.toString());
        SesionActiva.lastHash = hash;
        SesionActiva.data = new JSONArray();
        return f;
    }
}
