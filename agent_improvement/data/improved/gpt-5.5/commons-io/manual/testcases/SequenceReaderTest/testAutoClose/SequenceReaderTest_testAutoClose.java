package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testAutoClose {

    private static final int REPEATED_EOF_READS = 10;

    private void assertReadsExpectedCharacters(final Reader reader, final String expected) throws IOException {
        for (int index = 0; index < expected.length(); index++) {
            assertEquals(expected.charAt(index), (char) reader.read(),
                    "Read[" + index + "] of '" + expected + "'");
        }
    }

    private void assertRepeatedReadsReturnEof(final Reader reader) throws IOException {
        for (int readAttempt = 0; readAttempt < REPEATED_EOF_READS; readAttempt++) {
            assertEquals(-1, reader.read());
        }
    }

    @Test
    void testAutoClose() throws IOException {
        try (Reader reader = new SequenceReader(new CharSequenceReader("FooBar"))) {
            assertReadsExpectedCharacters(reader, "Foo");
            reader.close();
            assertRepeatedReadsReturnEof(reader);
        }
    }
}
