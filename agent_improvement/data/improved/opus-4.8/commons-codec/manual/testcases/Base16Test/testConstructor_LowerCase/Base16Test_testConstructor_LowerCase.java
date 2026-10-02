package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base16#Base16(boolean)} configured with {@code lowerCase = true}
 * encodes data using the lower-case Base16 alphabet.
 */
public class Base16Test_testConstructor_LowerCase {

    @Test
    void testConstructor_LowerCase() {
        // Build a Base16 codec that uses the lower-case alphabet (a-f instead of A-F).
        final Base16 lowerCaseBase16 = new Base16(true);

        // Encode the shared test payload and turn the encoded bytes back into a String.
        final byte[] encodedBytes = lowerCaseBase16.encode(BaseNTestData.DECODED);
        final String actualEncoded = StringUtils.newStringUtf8(encodedBytes);

        // The encoding must match the expected lower-case Base16 representation.
        final String expectedEncoded = Base16TestData.ENCODED_UTF8_LOWERCASE;
        assertEquals(expectedEncoded, actualEncoded, "new Base16(true)");
    }
}
