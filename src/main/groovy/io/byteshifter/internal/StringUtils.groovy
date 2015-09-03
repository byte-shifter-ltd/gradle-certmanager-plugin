package io.byteshifter.internal

/**
 * @author Sion Williams
 */
class StringUtils {
    private static final char[] HEXDIGITS = "0123456789abcdef".toCharArray()
    static final String HEXES = "0123456789ABCDEF";

    /**
     * Convert a byte array to a Hex string
     * @param bytes
     * @return return a formatted String with a space or null if no bytes supplied
     */
    static String formattedToHexString(byte[] bytes) {
        if ( bytes == null ) {
            return null
        }
        StringBuilder sb = new StringBuilder(3 * bytes.length)
        for (int b : bytes) {
            b &= 0xff
            sb.append(HEXDIGITS[b >> 4])
            sb.append(HEXDIGITS[b & 15])
            sb.append(' ')
        }
        return sb.toString()
    }

    static String toHexString(byte[] bytes) {
        if ( bytes == null ) {
            return null;
        }
        final StringBuilder hex = new StringBuilder( 2 * bytes.length );
        for ( final byte b : bytes ) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4))
                    .append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }
}
