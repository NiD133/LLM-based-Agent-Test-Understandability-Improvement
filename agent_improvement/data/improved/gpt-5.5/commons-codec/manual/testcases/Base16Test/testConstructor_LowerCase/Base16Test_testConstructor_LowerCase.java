package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Base16Test_testConstructor_LowerCase {

    @Test
    void testConstructor_LowerCase() {
        final Base16 lowerCaseBase16 = new Base16(true);
        final byte[] encodedBytes = lowerCaseBase16.encode(BaseNTestData.DECODED);
        final String expectedLowerCaseEncoding = Base16TestData.ENCODED_UTF8_LOWERCASE;
        final String actualLowerCaseEncoding = StringUtils.newStringUtf8(encodedBytes);

        assertEquals(expectedLowerCaseEncoding, actualLowerCaseEncoding, "new Base16(true)");
    }
}
