package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testMarkReset {

    private static final String INPUT = "01234567890";
    private static final int BOUNDED_READER_LIMIT = 3;
    private static final int READ_AHEAD_LIMIT = 3;

    @Test
    void testMarkReset() throws IOException {
        try (Reader source = new BufferedReader(new StringReader(INPUT));
                BoundedReader reader = new BoundedReader(source, BOUNDED_READER_LIMIT)) {
            reader.mark(READ_AHEAD_LIMIT);

            reader.read();
            reader.read();
            reader.read();

            reader.reset();

            reader.read();
            reader.read();
            reader.read();

            assertEquals(-1, reader.read());
        }
    }
}
