package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testReadMultiWithOffset {

    @Test
    void testReadMultiWithOffset() throws IOException {
        final Reader source = new BufferedReader(new StringReader("01234567890"));
        // Limit the reader to at most 3 characters total
        try (BoundedReader mr = new BoundedReader(source, 3)) {
            // Buffer is larger than the requested read; pre-fill with sentinel 'X'
            // to detect which positions are written by read()
            final char[] cbuf = new char[4];
            Arrays.fill(cbuf, 'X');

            // Read 2 characters starting at offset 1, leaving cbuf[0] and cbuf[3] untouched
            final int read = mr.read(cbuf, 1, 2);

            assertEquals(2, read, "Should have read exactly 2 characters");
            assertEquals('X', cbuf[0], "cbuf[0] is before the offset, must remain 'X'");
            assertEquals('0', cbuf[1], "cbuf[1] receives the 1st character from the source");
            assertEquals('1', cbuf[2], "cbuf[2] receives the 2nd character from the source");
            assertEquals('X', cbuf[3], "cbuf[3] is beyond offset+len, must remain 'X'");
        }
    }
}
