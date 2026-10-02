package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HexTest_testCustomCharsetToString {

    /**
     * Verifies that the string representation of a default Hex instance
     * includes the default charset name, making the codec's configuration
     * visible in diagnostic output such as assertion failure messages.
     */
    @Test
    void testCustomCharsetToString() {
        String hexDescription = new Hex().toString();
        assertTrue(hexDescription.contains(Hex.DEFAULT_CHARSET_NAME),
                "Hex.toString() should include the default charset name '" + Hex.DEFAULT_CHARSET_NAME + "'");
    }
}
