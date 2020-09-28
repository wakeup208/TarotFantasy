package com.wakeup.tarot.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class FileEncryptor {
    private final static String key = "Olkf98KkdfkHhf83"; // needs to be at least 8 characters for DES

//    public static void main(String[] args) {
//        FileInputStream fis;
//        FileOutputStream fos;
//        try {
//
//            fis = new FileInputStream("/home/anhle/eclipse/workspace/Tarot/assets/CardsDetail.json");
//            fos = new FileOutputStream("/home/anhle/eclipse/workspace/Tarot/assets/CardsDetail.json.enc");
//            encrypt(key, fis, fos);
//
//            fis = new FileInputStream("/home/anhle/eclipse/workspace/Tarot/assets/Number.json");
//            fos = new FileOutputStream("/home/anhle/eclipse/workspace/Tarot/assets/Number.json.enc");
//            encrypt(key, fis, fos);
//
//            fis = new FileInputStream("/home/anhle/eclipse/workspace/Tarot/assets/SpreadCard.json");
//            fos = new FileOutputStream("/home/anhle/eclipse/workspace/Tarot/assets/SpreadCard.json.enc");
//            encrypt(key, fis, fos);
//
//            fis = new FileInputStream("/home/anhle/eclipse/workspace/Tarot/assets/Star.json");
//            fos = new FileOutputStream("/home/anhle/eclipse/workspace/Tarot/assets/Star.json.enc");
//            encrypt(key, fis, fos);
//
//            fis = new FileInputStream("/home/anhle/eclipse/workspace/Tarot/assets/Suit.json");
//            fos = new FileOutputStream("/home/anhle/eclipse/workspace/Tarot/assets/Suit.json.enc");
//            encrypt(key, fis, fos);
//
//            fis = new FileInputStream("/home/anhle/eclipse/workspace/Tarot/assets/Symbol.json");
//            fos = new FileOutputStream("/home/anhle/eclipse/workspace/Tarot/assets/Symbol.json.enc");
//            encrypt(key, fis, fos);
//
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//    }

    public static void encrypt(String key, InputStream is, OutputStream os)
            throws Throwable {
        encryptOrDecrypt(key, Cipher.ENCRYPT_MODE, is, os);
    }

    public static void decrypt(String key, InputStream is, OutputStream os)
            throws Throwable {
        encryptOrDecrypt(key, Cipher.DECRYPT_MODE, is, os);
    }

    public static void encryptOrDecrypt(String key, int mode, InputStream is,
                                        OutputStream os) throws Throwable {

        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for
        // SunJCE

        if (mode == Cipher.ENCRYPT_MODE) {
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            CipherInputStream cis = new CipherInputStream(is, cipher);
            doCopy(cis, os);
        } else if (mode == Cipher.DECRYPT_MODE) {
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            CipherOutputStream cos = new CipherOutputStream(os, cipher);
            doCopy(is, cos);
        }
    }

    public static void doCopy(InputStream is, OutputStream os)
            throws IOException {
        byte[] bytes = new byte[64];
        int numBytes;
        while ((numBytes = is.read(bytes)) != -1) {
            os.write(bytes, 0, numBytes);
        }
        os.flush();
        os.close();
        is.close();
    }

    public static String decrypt(InputStream is) throws Throwable {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for
        // SunJCE

        cipher.init(Cipher.DECRYPT_MODE, desKey);
        CipherOutputStream cos = new CipherOutputStream(os, cipher);
        doCopy(is, cos);

        return new String(os.toByteArray(), "UTF-8");
    }
}
