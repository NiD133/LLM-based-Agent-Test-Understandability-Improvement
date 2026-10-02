package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testRead {

    private static final int EOF = -1;
    private static final int EOF_READ_ATTEMPTS = 10;

    private void assertRepeatedEndOfFile(final Reader reader) throws IOException {
        for (int attempt = 0; attempt < EOF_READ_ATTEMPTS; attempt++) {
            assertEquals(EOF, reader.read());
        }
    }

    private void assertReadsCharactersInOrder(final Reader reader, final char... expectedCharacters) throws IOException {
        for (final char expectedCharacter : expectedCharacters) {
            assertEquals(expectedCharacter, reader.read());
        }
    }

    @Test
    void testRead() throws IOException {
        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {
            assertReadsCharactersInOrder(reader, 'F', 'o', 'o', 'B', 'a', 'r');
            assertRepeatedEndOfFile(reader);
        }
    }
}
