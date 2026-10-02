package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testReadCharArrayPortion {

    private static final char UNREAD = 0;

    private void assertBufferStartsWith(final char[] expectedPrefix, final char[] actualBuffer) {
        for (int i = 0; i < expectedPrefix.length; i++) {
            assertEquals(expectedPrefix[i], actualBuffer[i], "Compare[" + i + "]");
        }
    }

    @Test
    void testReadCharArrayPortion() throws IOException {
        final char[] buffer = new char[10];

        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {
            assertEquals(3, reader.read(buffer, 3, 3));
            assertBufferStartsWith(new char[] { UNREAD, UNREAD, UNREAD, 'F', 'o', 'o' }, buffer);

            assertEquals(3, reader.read(buffer, 0, 3));
            assertBufferStartsWith(new char[] { 'B', 'a', 'r', 'F', 'o', 'o', UNREAD }, buffer);

            assertEquals(-1, reader.read(buffer));
            assertThrows(IndexOutOfBoundsException.class, () -> reader.read(buffer, 10, 10));
            assertThrows(NullPointerException.class, () -> reader.read(null, 0, 10));
        }
    }
}
