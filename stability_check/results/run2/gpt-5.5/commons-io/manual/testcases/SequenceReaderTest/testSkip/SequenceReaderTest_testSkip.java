package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testSkip {

    private static final String SKIPPED_READER_CONTENT = "Foo";
    private static final String REMAINING_READER_CONTENT = "Bar";
    private static final int SKIP_LENGTH = 3;

    private void assertReadsExpectedContent(final Reader reader, final String expected) throws IOException {
        for (int index = 0; index < expected.length(); index++) {
            assertEquals(expected.charAt(index), (char) reader.read(), "Read[" + index + "] of '" + expected + "'");
        }
    }

    @Test
    void testSkip() throws IOException {
        try (Reader reader = new SequenceReader(
                new StringReader(SKIPPED_READER_CONTENT),
                new StringReader(REMAINING_READER_CONTENT))) {
            assertEquals(SKIP_LENGTH, reader.skip(SKIP_LENGTH));
            assertReadsExpectedContent(reader, REMAINING_READER_CONTENT);
            assertEquals(0, reader.skip(SKIP_LENGTH));
        }
    }
}
