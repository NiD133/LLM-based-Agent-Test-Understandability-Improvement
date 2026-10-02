package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SequenceReader#read(char[], int, int)}, focusing on reading into a
 * specific portion (offset + length) of the destination buffer rather than the whole array.
 */
public class SequenceReaderTest_testReadCharArrayPortion {

    /** The default value of an uninitialized {@code char} array element; used to mark untouched slots. */
    private static final char UNTOUCHED = 0;

    @Test
    void testReadCharArrayPortion() throws IOException {
        // The SequenceReader yields "Foo" followed by "Bar", i.e. the stream "FooBar".
        final char[] buffer = new char[10];

        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {
            // Read the first 3 characters ("Foo") into buffer[3..5], leaving buffer[0..2] untouched.
            assertEquals(3, reader.read(buffer, 3, 3));
            assertBufferStartsWith(buffer, UNTOUCHED, UNTOUCHED, UNTOUCHED, 'F', 'o', 'o');

            // Read the next 3 characters ("Bar") into buffer[0..2], overwriting the leading slots.
            assertEquals(3, reader.read(buffer, 0, 3));
            assertBufferStartsWith(buffer, 'B', 'a', 'r', 'F', 'o', 'o', UNTOUCHED);

            // The "FooBar" stream is now exhausted, so a further read reports end-of-stream.
            assertEquals(-1, reader.read(buffer));

            // An offset/length beyond the buffer bounds must be rejected.
            assertThrows(IndexOutOfBoundsException.class, () -> reader.read(buffer, 10, 10));

            // A null destination buffer must be rejected.
            assertThrows(NullPointerException.class, () -> reader.read(null, 0, 10));
        }
    }

    /**
     * Asserts that the leading {@code expectedPrefix.length} elements of {@code actual} match
     * {@code expectedPrefix}, element by element.
     */
    private void assertBufferStartsWith(final char[] actual, final char... expectedPrefix) {
        final char[] actualPrefix = new char[expectedPrefix.length];
        System.arraycopy(actual, 0, actualPrefix, 0, expectedPrefix.length);
        assertArrayEquals(expectedPrefix, actualPrefix);
    }
}
