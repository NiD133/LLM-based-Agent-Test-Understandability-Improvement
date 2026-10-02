package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Base16} constructed with {@code lowerCase=true} encodes bytes
 * using the lower-case hex alphabet (0-9, a-f) rather than the default upper-case
 * alphabet (0-9, A-F).
 */
public class Base16Test_testConstructor_LowerCase {

    /**
     * Verifies that {@code new Base16(true)} produces lower-case hex output.
     * Encodes the standard test data and confirms the result matches the
     * expected lower-case UTF-8 encoded string.
     */
    @Test
    void testConstructor_LowerCase() {
        final Base16 base16 = new Base16(true);
        final byte[] encoded = base16.encode(BaseNTestData.DECODED);
        final String expectedResult = Base16TestData.ENCODED_UTF8_LOWERCASE;
        final String result = StringUtils.newStringUtf8(encoded);
        assertEquals(expectedResult, result, "new Base16(true)");
    }
}
