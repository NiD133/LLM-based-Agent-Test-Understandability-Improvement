package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class ReaderInputStreamTest_testCodingErrorAction {

    private static final String MALFORMED_UTF16_INPUT = "\uD800aa";

    /**
     * IO-717: malformed input configured with REPORT must fail instead of looping.
     */
    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testCodingErrorAction() throws IOException {
        final Charset charset = StandardCharsets.UTF_8;
        final CharsetEncoder encoder = charset.newEncoder().onMalformedInput(CodingErrorAction.REPORT);

        try (InputStream in = new ReaderInputStream(new StringReader(MALFORMED_UTF16_INPUT), encoder,
                (int) ReaderInputStream.minBufferSize(encoder))) {
            assertThrows(CharacterCodingException.class, in::read);
        }

        try (InputStream in = ReaderInputStream.builder()
                .setReader(new StringReader(MALFORMED_UTF16_INPUT))
                .setCharsetEncoder(encoder)
                .setBufferSize((int) ReaderInputStream.minBufferSize(charset.newEncoder()))
                .get()) {
            assertThrows(CharacterCodingException.class, in::read);
        }
    }
}
