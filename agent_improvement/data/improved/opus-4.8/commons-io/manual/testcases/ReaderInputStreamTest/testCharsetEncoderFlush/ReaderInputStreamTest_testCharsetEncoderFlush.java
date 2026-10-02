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

public class ReaderInputStreamTest_testCharsetEncoderFlush {

    /**
     * Test inputs: each entry pairs a charset name with a single-character string that must be
     * encoded into the bytes produced by that charset.
     */
    static Stream<Arguments> charsetData() {
        // @formatter:off
        return Stream.of(
                Arguments.of("Cp930", "Α"),
                Arguments.of("ISO_8859_1", "A"),
                Arguments.of("UTF-8", "Α"));
        // @formatter:on
    }

    /**
     * Verifies that {@link ReaderInputStream} encodes the supplied character data into exactly the
     * same bytes that {@link String#getBytes(Charset)} would produce for the given charset. The
     * check is performed for both ways of constructing the stream: the direct constructor and the
     * fluent builder.
     */
    @ParameterizedTest
    @MethodSource("charsetData")
    void testCharsetEncoderFlush(final String charsetName, final String data) throws IOException {
        final Charset charset = Charset.forName(charsetName);
        final byte[] expectedBytes = data.getBytes(charset);

        // Stream created via the constructor.
        try (InputStream in = new ReaderInputStream(new StringReader(data), charset)) {
            assertEncodedBytesEqual(expectedBytes, in);
        }

        // Stream created via the builder; must yield the same encoded bytes.
        try (InputStream in = ReaderInputStream.builder()
                .setReader(new StringReader(data))
                .setCharset(charset)
                .get()) {
            assertEncodedBytesEqual(expectedBytes, in);
        }
    }

    /**
     * Asserts that reading all bytes from the given stream yields exactly the expected bytes.
     * Bytes are compared via {@link Arrays#toString(byte[])} so a mismatch reports the actual byte
     * values.
     */
    private static void assertEncodedBytesEqual(final byte[] expectedBytes, final InputStream in) throws IOException {
        assertEquals(Arrays.toString(expectedBytes), Arrays.toString(IOUtils.toByteArray(in)));
    }
}
