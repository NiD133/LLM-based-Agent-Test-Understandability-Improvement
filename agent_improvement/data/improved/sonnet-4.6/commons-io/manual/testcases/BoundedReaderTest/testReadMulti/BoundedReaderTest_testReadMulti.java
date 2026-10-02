package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testReadMulti {

    // Source string longer than the bound to verify that BoundedReader stops at the limit
    private static final String SOURCE_STRING = "01234567890";

    // The maximum number of characters BoundedReader is allowed to return
    private static final int READ_LIMIT = 3;

    private final Reader sourceReader = new BufferedReader(new StringReader(SOURCE_STRING));

    @Test
    void testReadMulti() throws IOException {
        // Buffer is intentionally larger than READ_LIMIT so we can verify the unread slot stays untouched
        try (BoundedReader mr = new BoundedReader(sourceReader, READ_LIMIT)) {
            final char[] cbuf = new char[4];
            Arrays.fill(cbuf, 'X'); // sentinel value to detect any unintended writes

            final int charsRead = mr.read(cbuf, 0, 4);

            assertEquals(READ_LIMIT, charsRead, "BoundedReader must stop reading at the configured limit");
            assertEquals('0', cbuf[0], "First character should be '0'");
            assertEquals('1', cbuf[1], "Second character should be '1'");
            assertEquals('2', cbuf[2], "Third character should be '2'");
            assertEquals('X', cbuf[3], "Slot beyond the limit must remain untouched");
        }
    }
}
