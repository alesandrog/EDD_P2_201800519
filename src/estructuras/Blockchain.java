/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import java.security.MessageDigest;

/**
 *
 * @author USUARIO
 */
public class Blockchain {

    private int contador_nodos = 1;
    private Bloque inicio;
    int nonce = 0;

    public Blockchain() {
        this.inicio = null;
    }

    public void insertar(Bloque bloque) {
        nonce = 0;
        Bloque aux = inicio;
        while (aux != null) {
            aux = aux.getSiguiente();
        }

        if (aux == inicio) {
            inicio = bloque;
            bloque.setPrevious_hash("0000");
        } else {
            aux.setSiguiente(bloque);
            bloque.setPrevious_hash(aux.getHash());
        }
        bloque.setAnterior(aux);
        bloque.setIndex(contador_nodos);
        String hashtosha = bloque.getIndex() + bloque.getTimestamp() + bloque.getPrevious_hash() + bloque.getData().toString();
        bloque.setHash(calcular_hash(hashtosha));
        bloque.setNonce(nonce);
        contador_nodos++;
    }

    public String calcular_hash(String base) {
        boolean hashValido = false;
        StringBuffer hexString = new StringBuffer();

        while (hashValido == false) {
            hexString = new StringBuffer();
            base = base + nonce;
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(base.getBytes("UTF-8"));

                for (int i = 0; i < hash.length; i++) {
                    String hex = Integer.toHexString(0xff & hash[i]);
                    if (hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }

                System.out.println(hexString);

                if (hexString.charAt(0) == '0' && hexString.charAt(1) == '0' && hexString.charAt(2) == '0' && hexString.charAt(3) == '0') {
                    System.out.println(nonce);
                    hashValido = true;
                } else {
                    nonce++;
                }

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return hexString.toString();
    }

}
