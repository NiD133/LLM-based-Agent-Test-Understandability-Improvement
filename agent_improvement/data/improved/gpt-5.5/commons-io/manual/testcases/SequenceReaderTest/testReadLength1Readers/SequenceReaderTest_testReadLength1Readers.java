package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testReadLength1Readers {

    @Test
    void testReadLength1Readers() throws IOException {
        try (Reader reader = new SequenceReader(
                new StringReader("0"),
                new StringReader("1"),
                new StringReader("2"),
                new StringReader("3"))) {
            assertEquals('0', reader.read());
            assertEquals('1', reader.read());
            assertEquals('2', reader.read());
            assertEquals('3', reader.read());
        }
    }
}
