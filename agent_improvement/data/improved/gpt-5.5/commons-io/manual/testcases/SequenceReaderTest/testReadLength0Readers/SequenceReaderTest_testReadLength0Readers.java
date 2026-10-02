package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testReadLength0Readers {

    private static final int EOF_READ_ATTEMPTS = 10;

    private void assertReadsEndOfFile(final Reader reader) throws IOException {
        for (int i = 0; i < EOF_READ_ATTEMPTS; i++) {
            assertEquals(-1, reader.read());
        }
    }

    @Test
    void testReadLength0Readers() throws IOException {
        try (Reader reader = new SequenceReader(
                new StringReader(StringUtils.EMPTY),
                new StringReader(StringUtils.EMPTY),
                new StringReader(StringUtils.EMPTY))) {
            assertReadsEndOfFile(reader);
        }
    }
}
