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

    private static final String ISOLATED_HIGH_SURROGATE = "\uD800";

    private static CharsetEncoder newUtf8Encoder() {
        return StandardCharsets.UTF_8.newEncoder();
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testCodingError() throws IOException {
        try (ReaderInputStream input = new ReaderInputStream(new StringReader(ISOLATED_HIGH_SURROGATE), newUtf8Encoder())) {
            assertDoesNotThrow(() -> input.read());
        }

        try (ReaderInputStream input = ReaderInputStream.builder()
                .setReader(new StringReader(ISOLATED_HIGH_SURROGATE))
                .setCharsetEncoder(newUtf8Encoder())
                .get()) {
            assertDoesNotThrow(() -> input.read());
        }
    }
}
