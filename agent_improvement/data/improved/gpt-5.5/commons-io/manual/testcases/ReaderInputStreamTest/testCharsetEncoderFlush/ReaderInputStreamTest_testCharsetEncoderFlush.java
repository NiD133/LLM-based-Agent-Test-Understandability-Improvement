package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ReaderInputStreamTest_testCharsetEncoderFlush {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    static Stream<Arguments> charsetData() {
        return Stream.of(
                Arguments.of("Cp930", "\u0391"),
                Arguments.of("ISO_8859_1", "A"),
                Arguments.of(UTF_8, "\u0391"));
    }

    @ParameterizedTest
    @MethodSource("charsetData")
    void testCharsetEncoderFlush(final String charsetName, final String data) throws IOException {
        final Charset charset = Charset.forName(charsetName);
        final byte[] expectedEncodedBytes = data.getBytes(charset);

        try (InputStream inputStream = new ReaderInputStream(new StringReader(data), charset)) {
            assertEncodedBytes(expectedEncodedBytes, inputStream);
        }

        try (InputStream inputStream = ReaderInputStream.builder().setReader(new StringReader(data)).setCharset(charset).get()) {
            assertEncodedBytes(expectedEncodedBytes, inputStream);
        }
    }

    private void assertEncodedBytes(final byte[] expectedEncodedBytes, final InputStream inputStream) throws IOException {
        assertEquals(Arrays.toString(expectedEncodedBytes), Arrays.toString(IOUtils.toByteArray(inputStream)));
    }
}
