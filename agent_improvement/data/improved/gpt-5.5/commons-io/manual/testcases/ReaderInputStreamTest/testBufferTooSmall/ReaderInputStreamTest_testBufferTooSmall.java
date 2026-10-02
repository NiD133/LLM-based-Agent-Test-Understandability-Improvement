package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testBufferTooSmall {

    private static final String UNPAIRED_HIGH_SURROGATE = "\uD800";

    @Test
    void testBufferTooSmall() {
        assertThrows(IllegalArgumentException.class,
                () -> new ReaderInputStream(new StringReader(UNPAIRED_HIGH_SURROGATE), StandardCharsets.UTF_8, -1));
        assertThrows(IllegalArgumentException.class,
                () -> new ReaderInputStream(new StringReader(UNPAIRED_HIGH_SURROGATE), StandardCharsets.UTF_8, 0));
        assertThrows(IllegalArgumentException.class,
                () -> new ReaderInputStream(new StringReader(UNPAIRED_HIGH_SURROGATE), StandardCharsets.UTF_8, 1));
    }
}
