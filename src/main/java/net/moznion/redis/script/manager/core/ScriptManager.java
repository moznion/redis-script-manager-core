package net.moznion.redis.script.manager.core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public abstract class ScriptManager<K, V> {
    protected String sha1;

    public abstract Object eval(K[] keys);

    public abstract Object eval(K[] keys, V[] values);

    protected ScriptManager(final String sha1) {
        this.sha1 = sha1;
    }

    protected String getSHA1(final V script) {
        if (sha1 == null) {
            sha1 = digestSHA1(script);
        }
        return sha1;
    }

    public static String digestSHA1(final Object script) {
        final MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to instantiate SHA-1 message digest", e);
        }

        return DatatypeConverter.printHexBinary(
                messageDigest.digest(script.toString().getBytes(StandardCharsets.UTF_8)));
    }
}
