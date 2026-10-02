package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.CodecPolicy;
import org.junit.jupiter.api.Test;

/**
 * Verifies the {@link Base16#Base16(boolean, CodecPolicy)} constructor.
 */
public class Base16Test_testConstructor_LowerCase_DecodingPolicy {

    @Test
    void testConstructor_LowerCase_DecodingPolicy() {
        // Use the upper-case alphabet (lowerCase = false) with a strict decoding policy.
        final Base16 base16 = new Base16(false, CodecPolicy.STRICT);

        // Encoding well-known input must yield its known upper-case Base16 representation.
        final byte[] encoded = base16.encode(BaseNTestData.DECODED);
        final String actualEncoded = StringUtils.newStringUtf8(encoded);
        final String expectedEncoded = Base16TestData.ENCODED_UTF8_UPPERCASE;

        assertEquals(actualEncoded, expectedEncoded, "new base16(false, CodecPolicy.STRICT)");
    }
}
