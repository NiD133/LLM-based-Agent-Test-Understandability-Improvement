package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class ReaderInputStreamTest_testCodingError {

    // \uD800 is a lone high surrogate with no paired low surrogate.
    // The encoder treats this as an underflow (incomplete input) rather than a
    // coding error, so read() must complete without throwing.
    private static final String LONE_HIGH_SURROGATE = "\uD800";

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testCodingError_viaConstructor_doesNotThrowOnLoneSurrogate() throws IOException {
        CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(LONE_HIGH_SURROGATE), encoder)) {
            assertDoesNotThrow(() -> in.read());
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testCodingError_viaBuilder_doesNotThrowOnLoneSurrogate() throws IOException {
        CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();
        try (ReaderInputStream in = ReaderInputStream.builder()
                .setReader(new StringReader(LONE_HIGH_SURROGATE))
                .setCharsetEncoder(encoder)
                .get()) {
            assertDoesNotThrow(() -> in.read());
        }
    }
}
