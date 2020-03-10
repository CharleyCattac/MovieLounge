package com.lobach.movielounge.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The Encryptor class.
 *
 * @author Renata Lobach
 * @since 2020-03-02
 */

public class Encrypror{
    private final static Logger logger = LogManager.getLogger();
    private static final StringBuilder ZERO = new StringBuilder("0");
    private static final String MD5 = "MD5";

    /**
     * The private class constructor to hide the implicit public one.
     */
    private Encrypror() {
    }

    /**
     * This method encodes the string; uses the MD5 hash function.
     *
     * @param string - the string for encoding;
     * @return the encoded string;
     */
    public static String encode(String string) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance(MD5);
            messageDigest.reset();
            messageDigest.update(string.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.error("Encryption failed ", e);
        }
        BigInteger bigInt = new BigInteger(1, digest);
        StringBuilder encodedString = new StringBuilder(bigInt.toString(16));
        while (encodedString.length() < 32) {
            encodedString = ZERO.append(encodedString);
        }
        return encodedString.toString();
    }
}
