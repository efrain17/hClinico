package ec.com.altura.aclient.util;


import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 *
 * @author ELIAS VARGAS
 */
    public class Encriptador {
        Cipher ecipher;
        Cipher dcipher;
        // 8-byte Salt
        byte[] salt = {
            (byte)0xA9, (byte)0x9B, (byte)0xC8, (byte)0x32,
            (byte)0x56, (byte)0x35, (byte)0xE3, (byte)0x03
        };

        // Iteration count
        int iterationCount = 19;

        public Encriptador(SecretKey key) {
            try {
                ecipher = Cipher.getInstance("DES");
                dcipher = Cipher.getInstance("DES");
                ecipher.init(Cipher.ENCRYPT_MODE, key);
                dcipher.init(Cipher.DECRYPT_MODE, key);

            } catch (javax.crypto.NoSuchPaddingException e) {
            } catch (java.security.NoSuchAlgorithmException e) {
            } catch (java.security.InvalidKeyException e) {
            }
        }
        Encriptador(String passPhrase) {
            try {
                // Create the key
                KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
                SecretKey key = SecretKeyFactory.getInstance(
                        "PBEWithMD5AndDES").generateSecret(keySpec);
                ecipher = Cipher.getInstance(key.getAlgorithm());
                dcipher = Cipher.getInstance(key.getAlgorithm());

                // Prepare the parameter to the ciphers
                AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

                // Create the ciphers
                ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
                dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
            } catch (java.security.InvalidAlgorithmParameterException e) {
            } catch (java.security.spec.InvalidKeySpecException e) {
            } catch (javax.crypto.NoSuchPaddingException e) {
            } catch (java.security.NoSuchAlgorithmException e) {
            } catch (java.security.InvalidKeyException e) {
            }
        }
        public String encrypt(String str) {
            try {
                // Encode the string into bytes using utf-8
                byte[] utf8 = str.getBytes("UTF8");

                // Encrypt
                byte[] enc = ecipher.doFinal(utf8);
                // Encode bytes to base64 to get a string
                return new String(Base64.encode(enc, Base64.DEFAULT));
            } catch (javax.crypto.BadPaddingException e) {
            } catch (IllegalBlockSizeException e) {
            } catch (UnsupportedEncodingException e) {}
            return null;
        }

        public String decrypt(String str) {
            try {
                byte[] dec = Base64.decode(str, Base64.DEFAULT);
                byte[] utf8 = dcipher.doFinal(dec);
                return new String(utf8, "UTF8");
            } catch (Exception e) {
                
            }
            return null;
        }
        public static final String encriptar(String clave){
        	Encriptador encrypter = new Encriptador("Altura");
            String encrypted = encrypter.encrypt(clave);
            return encrypted;
        }
        public static final String desencriptar(String clave){
        	Encriptador encrypter = new Encriptador("Altura");
            String dencrypted = encrypter.decrypt(clave);
            return dencrypted;
        }

    }
