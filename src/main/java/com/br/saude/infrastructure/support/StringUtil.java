/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.saude.infrastructure.support;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author @jonatasfbastos
 */
public class StringUtil {
    /**
     * Represents the singleton instance of the class.
     */
    private static StringUtil instance;

    /**
     * Object monitor for synchronize.
     */
    private static final Object MONITOR = new Object();

    public String accessToken;

    /**
     * Create a new instance of the class.
     */
    private StringUtil() {
        super();
        generateToken();
    }

    /**
     * Instantiates a new {@link StringUtil}.
     * 
     * @return the instance.
     */
    public static StringUtil getInstance() {
        synchronized (MONITOR) {
            if (instance == null) {
                instance = new StringUtil();
            }
        }
        return instance;
    }

    /**
     * Validate if an string is <code>null</code>.
     * 
     * @param str a string to validate
     * @return <code>true</code> if is null.
     */
    public boolean isNull(final String str) {
        return (str == null);
    }

    public String generateToken() {
        this.accessToken = "tkn" + this.hashCode();

        return this.accessToken;
    }

    /**
     * Validate if a string is null and empty. If is null immediately return
     * <code>true</code>
     * otherwise test if is empty.
     * 
     * @param str a string to validate.
     * @return <code>true</code> if is null and empty.
     */
    public boolean isEmpty(final String str) {
        return (isNull(str) ? true : (str.trim().equals("")));
    }

    /**
     * Validate if a string is null and empty.
     * 
     * @param str the string to validate.
     * @return <code>true</code> if the string is null and empty.
     */
    public boolean isNullAndEmpty(final String str) {
        return (isNull(str) && isEmpty(str));
    }

    /**
     * Validate if a string is null or empty.
     * 
     * @param str the string to validate.
     * @return <code>true</code> if is null or empty.
     */
    public boolean isNullOrEmpty(final String str) {
        return (isNull(str) || isEmpty(str));
    }

    public static String toPasswordMD5(String plaintext) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashtext = bigInt.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return "password_" + hashtext;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static boolean comparePasswordMD5(String md5, String plaintext) {
        return md5.equals(StringUtil.toPasswordMD5("password_" + plaintext));
    }
}
