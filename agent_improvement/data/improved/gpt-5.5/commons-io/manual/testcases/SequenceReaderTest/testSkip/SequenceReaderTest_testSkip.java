package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testSkip {

    private void assertReaderContent(final Reader reader, final String expected) throws IOException {
        for (int index = 0; index < expected.length(); index++) {
            assertEquals(expected.charAt(index), (char) reader.read(), "Read[" + index + "] of '" + expected + "'");
        }
    }

    @Test
    void testSkip() throws IOException {
        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {
            assertEquals(3, reader.skip(3));
            assertReaderContent(reader, "Bar");
            assertEquals(0, reader.skip(3));
        }
    }
}
