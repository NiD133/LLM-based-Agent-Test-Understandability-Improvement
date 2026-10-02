package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.UnsupportedCharsetException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that constructing a {@link Hex} codec with an unknown charset name fails fast.
 */
public class HexTest_testCustomCharsetBadName {

    /** A charset name that is guaranteed not to exist on any JVM. */
    private static final String UNKNOWN_CHARSET_NAME = "UNKNOWN";

    @Test
    void testCustomCharsetBadName() {
        // Hex(String) resolves the name via Charset.forName, which rejects unknown names.
        assertThrows(UnsupportedCharsetException.class, () -> new Hex(UNKNOWN_CHARSET_NAME));
    }
}
