package com.kt.urlshortener.utils;

import java.math.BigInteger;
import java.util.UUID;

public class Base62Utils {
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String uuidToBase62(UUID uuid) {
        BigInteger number = BigInteger.valueOf(uuid.getMostSignificantBits())
                .shiftLeft(64)
                .add(BigInteger.valueOf(uuid.getLeastSignificantBits()));

        if (number.signum() < 0) {
            number = number.add(BigInteger.ONE.shiftLeft(128));
        }

        return toBase62(number);
    }

    private static String toBase62(BigInteger number) {
        StringBuilder sb = new StringBuilder();
        BigInteger base = BigInteger.valueOf(62);

        while (number.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divmod = number.divideAndRemainder(base);
            sb.append(BASE62.charAt(divmod[1].intValue()));
            number = divmod[0];
        }

        return sb.reverse().toString();
    }

    public static String generateShortCode(int length) {
        String base62 = uuidToBase62(UUID.randomUUID());
        if (base62.length() > length) {
            return base62.substring(0, length);
        }
        return base62;
    }
}
