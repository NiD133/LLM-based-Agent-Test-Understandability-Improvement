package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that ReaderInputStream correctly encodes characters to bytes and flushes
 * the CharsetEncoder, verifying both the constructor-based and builder-based APIs.
 */
public class ReaderInputStreamTest_testCharsetEncoderFlush {

    static Stream<Arguments> charsetData() {
        return Stream.of(
            Arguments.of("Cp930",       "Α"),
            Arguments.of("ISO_8859_1",  "A"),
            Arguments.of("UTF-8",       "Α")
        );
    }

    @ParameterizedTest
    @MethodSource("charsetData")
    void testCharsetEncoderFlush(final String charsetName, final String data) throws IOException {
        final Charset charset = Charset.forName(charsetName);
        final byte[] expected = data.getBytes(charset);

        // Verify constructor-based API
        try (InputStream in = new ReaderInputStream(new StringReader(data), charset)) {
            assertEquals(Arrays.toString(expected), Arrays.toString(IOUtils.toByteArray(in)));
        }

        // Verify builder-based API produces the same encoding
        try (InputStream in = ReaderInputStream.builder().setReader(new StringReader(data)).setCharset(charset).get()) {
            assertEquals(Arrays.toString(expected), Arrays.toString(IOUtils.toByteArray(in)));
        }
    }
}
