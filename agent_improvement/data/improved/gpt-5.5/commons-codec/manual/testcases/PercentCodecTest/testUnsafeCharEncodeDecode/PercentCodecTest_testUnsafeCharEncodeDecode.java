package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class PercentCodecTest_testUnsafeCharEncodeDecode {

    private static final String TEXT_WITH_UNSAFE_CHARACTERS = "\u03B1\u03B2\u03B3\u03B4\u03B5\u03B6% ";
    private static final String EXPECTED_PERCENT_ENCODED_TEXT = "%CE%B1%CE%B2%CE%B3%CE%B4%CE%B5%CE%B6%25 ";

    @Test
    void testUnsafeCharEncodeDecode() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();

        final byte[] encodedBytes = percentCodec.encode(TEXT_WITH_UNSAFE_CHARACTERS.getBytes(StandardCharsets.UTF_8));
        final String encodedText = new String(encodedBytes, StandardCharsets.UTF_8);

        final byte[] decodedBytes = percentCodec.decode(encodedBytes);
        final String decodedText = new String(decodedBytes, StandardCharsets.UTF_8);

        assertEquals(EXPECTED_PERCENT_ENCODED_TEXT, encodedText, "Basic PercentCodec unsafe char encoding test");
        assertEquals(TEXT_WITH_UNSAFE_CHARACTERS, decodedText, "Basic PercentCodec unsafe char decoding test");
    }
}
