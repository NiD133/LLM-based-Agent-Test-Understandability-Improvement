package org.apache.commons.io.input;

import static org.apache.commons.io.IOUtils.EOF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testReadCharArrayPortion {

    // Represents an unwritten (zero-initialized) slot in the char buffer
    private static final char NUL = 0;

    private void checkArray(final char[] expected, final char[] actual) {
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i], "Compare[" + i + "]");
        }
    }

    @Test
    @DisplayName("read(char[], off, len) reads into the correct buffer portion and enforces contract violations")
    void testReadCharArrayPortion() throws IOException {
        // Buffer large enough to verify that reads land in the correct slice
        final char[] buffer = new char[10];

        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {

            // Read first 3 chars ("Foo") into buffer positions [3, 6)
            assertEquals(3, reader.read(buffer, 3, 3),
                    "First read should return 3 chars from the first reader (\"Foo\")");
            char[] afterFirstRead = { NUL, NUL, NUL, 'F', 'o', 'o' };
            checkArray(afterFirstRead, buffer);

            // Read next 3 chars ("Bar") into buffer positions [0, 3), leaving "Foo" untouched at [3, 6)
            assertEquals(3, reader.read(buffer, 0, 3),
                    "Second read should return 3 chars from the second reader (\"Bar\")");
            char[] afterSecondRead = { 'B', 'a', 'r', 'F', 'o', 'o', NUL };
            checkArray(afterSecondRead, buffer);

            // Both underlying readers are exhausted; any further read must signal EOF
            assertEquals(EOF, reader.read(buffer),
                    "Read on exhausted SequenceReader should return EOF (-1)");

            // Off + len exceeds buffer length — must throw IndexOutOfBoundsException
            assertThrows(IndexOutOfBoundsException.class, () -> reader.read(buffer, 10, 10),
                    "read() with out-of-bounds offset/length should throw IndexOutOfBoundsException");

            // Null buffer — must throw NullPointerException
            assertThrows(NullPointerException.class, () -> reader.read(null, 0, 10),
                    "read() with null buffer should throw NullPointerException");
        }
    }
}
