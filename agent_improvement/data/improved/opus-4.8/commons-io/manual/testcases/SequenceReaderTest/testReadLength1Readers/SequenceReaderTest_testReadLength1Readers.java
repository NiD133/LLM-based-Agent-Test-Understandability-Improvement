package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testReadLength1Readers {

    /**
     * Verifies that a {@link SequenceReader} built from several single-character
     * {@link Reader}s yields each reader's character in order, one per {@code read()} call.
     */
    @Test
    void testReadLength1Readers() throws IOException {
        try (Reader sequenceReader = new SequenceReader(
                new StringReader("0"),
                new StringReader("1"),
                new StringReader("2"),
                new StringReader("3"))) {
            assertEquals('0', sequenceReader.read());
            assertEquals('1', sequenceReader.read());
            assertEquals('2', sequenceReader.read());
            assertEquals('3', sequenceReader.read());
        }
    }
}
