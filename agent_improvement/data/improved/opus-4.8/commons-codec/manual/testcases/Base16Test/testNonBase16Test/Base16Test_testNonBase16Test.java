package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base16} rejects characters that are not part of the Base16 alphabet.
 */
public class Base16Test_testNonBase16Test {

    /**
     * Each of these characters sits just outside the valid Base16 ranges
     * ('0'-'9', 'A'-'F', 'a'-'f'), so decoding any of them must fail:
     * <ul>
     *   <li>'/' (0x2F) is just before '0'</li>
     *   <li>':' (0x3A) is just after '9'</li>
     *   <li>'@' (0x40) is just before 'A'</li>
     *   <li>'G' (0x47) is just after 'F'</li>
     *   <li>'%' (0x25) is well below the digit range</li>
     *   <li>'`' (0x60) is just before 'a'</li>
     *   <li>'g' (0x67) is just after 'f'</li>
     * </ul>
     */
    private static final char[] NON_BASE16_CHARS = { '/', ':', '@', 'G', '%', '`', 'g' };

    @Test
    void testNonBase16Test() {
        for (final char nonBase16Char : NON_BASE16_CHARS) {
            final byte[] encoded = { (byte) nonBase16Char };

            assertThrows(IllegalArgumentException.class,
                    () -> new Base16().decode(encoded),
                    "Decoding should reject the non-Base16 char: " + nonBase16Char);
        }
    }
}
