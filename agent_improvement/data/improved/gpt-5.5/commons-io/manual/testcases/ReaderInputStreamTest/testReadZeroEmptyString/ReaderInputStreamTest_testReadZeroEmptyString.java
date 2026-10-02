package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testReadZeroEmptyString {

    private static final int BUFFER_SIZE = 30;

    @SuppressWarnings("deprecation")
    @Test
    void testReadZeroEmptyString() throws Exception {
        try (ReaderInputStream inputStream = new ReaderInputStream(new StringReader(""))) {
            final byte[] buffer = new byte[BUFFER_SIZE];

            assertEquals(0, inputStream.read(buffer, 0, 0));
            assertEquals(-1, inputStream.read(buffer, 0, 1));
            assertEquals(0, inputStream.read(buffer, 0, 0));
            assertEquals(-1, inputStream.read(buffer, 0, 1));
        }
    }
}
