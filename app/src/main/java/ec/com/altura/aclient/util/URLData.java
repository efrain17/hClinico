/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.altura.aclient.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author caelvaso
 */
public class URLData {

    public String action;
    public Map<String, String> data;

    public URLData() {
        data = new LinkedHashMap();
    }

    public URLData(String action) {
        this.action = action;
        data = new LinkedHashMap();
    }

    public URLData(String action, Map data) {
        this.action = action;
        this.data = data;
    }

    public void put(String key, String value) {
        data.put(key, value);
    }
    public String get(String key) {
        return data.get(key);
    }

    public static URLData decompile(String query) throws Exception {
        query = Encriptador.desencriptar(query);
        String[] decodes = query.split("&");
        URLData urlData = new URLData();
        for (String decode : decodes) {
            int i = decode.indexOf("=");
            String key = decode.substring(0, i);
            String value = decode.substring(i + 1);
            value = URLDecoder.decode(value, "UTF-8");
            if ("action".equalsIgnoreCase(key)) {
                urlData.action = value;
            } else {
                urlData.put(key, value);
            }
        }
        return urlData;
    }

    @Override
    public String toString() {
        String salida = "action=" + action;
        try {
            for (String key : data.keySet()) {
                salida += "&" + key + "=" + URLEncoder.encode(data.get(key), "UTF-8");
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(URLData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return salida;
    }
    public String encode() throws NoSuchAlgorithmException {
        String salida = toString();
        salida = Encriptador.encriptar(salida);
        return salida;
    }
    public String encodeURI() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return URLEncoder.encode(encode(), "UTF-8");
    }
}
