package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.CodecPolicy;
import org.junit.jupiter.api.Test;

public class Base16Test_testConstructor_LowerCase_DecodingPolicy {

    @Test
    void testConstructor_LowerCase_DecodingPolicy() {
        final Base16 base16 = new Base16(false, CodecPolicy.STRICT);
        final byte[] encodedBytes = base16.encode(BaseNTestData.DECODED);

        final String expectedUpperCaseEncoding = Base16TestData.ENCODED_UTF8_UPPERCASE;
        final String actualEncoding = StringUtils.newStringUtf8(encodedBytes);

        assertEquals(actualEncoding, expectedUpperCaseEncoding, "new base16(false, CodecPolicy.STRICT)");
    }
}
