package org.apache.commons.io.input;

import static org.apache.commons.io.IOUtils.EOF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testRead {

    /**
     * Asserts that the reader returns EOF for 10 consecutive reads,
     * verifying that exhaustion is stable and not a one-time signal.
     */
    private void checkReadEof(final Reader reader) throws IOException {
        for (int i = 0; i < 10; i++) {
            assertEquals(EOF, reader.read(), "Expected EOF on read #" + (i + 1) + " after content is exhausted");
        }
    }

    /**
     * Verifies that SequenceReader concatenates multiple readers transparently:
     * characters from "Foo" are followed immediately by characters from "Bar",
     * and further reads return EOF once all content is consumed.
     */
    @Test
    void testRead() throws IOException {
        // Two readers whose content should appear as a single character stream: "FooBar"
        final String firstPart  = "Foo";
        final String secondPart = "Bar";

        try (Reader reader = new SequenceReader(new StringReader(firstPart), new StringReader(secondPart))) {

            // Characters from the first reader
            assertEquals('F', reader.read(), "1st char should be 'F' from first reader");
            assertEquals('o', reader.read(), "2nd char should be 'o' from first reader");
            assertEquals('o', reader.read(), "3rd char should be 'o' from first reader");

            // Seamless transition: characters now come from the second reader
            assertEquals('B', reader.read(), "4th char should be 'B' from second reader");
            assertEquals('a', reader.read(), "5th char should be 'a' from second reader");
            assertEquals('r', reader.read(), "6th char should be 'r' from second reader");

            // All content consumed — every subsequent read must return EOF
            checkReadEof(reader);
        }
    }
}
