package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.CodecPolicy;
import org.junit.jupiter.api.Test;

public class Base16Test_testConstructor_LowerCase_DecodingPolicy {

    @Test
    void testConstructor_LowerCase_DecodingPolicy() {
        // Construct uppercase Base16 encoder with strict decoding policy
        final Base16 base16 = new Base16(false, CodecPolicy.STRICT);
        final byte[] encoded = base16.encode(BaseNTestData.DECODED);
        final String expectedResult = Base16TestData.ENCODED_UTF8_UPPERCASE;
        final String result = StringUtils.newStringUtf8(encoded);
        assertEquals(expectedResult, result, "new base16(false, CodecPolicy.STRICT)");
    }
}
