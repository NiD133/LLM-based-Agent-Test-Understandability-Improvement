package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testReadMultiWithOffset {

    private static final char UNTOUCHED = 'X';

    private final Reader bufReader1 = new BufferedReader(new StringReader("01234567890"));

    @Test
    void testReadMultiWithOffset() throws IOException {
        try (BoundedReader mr = new BoundedReader(bufReader1, 3)) {
            final char[] cbuf = new char[4];
            Arrays.fill(cbuf, UNTOUCHED);

            final int read = mr.read(cbuf, 1, 2);

            assertEquals(2, read);
            assertEquals(UNTOUCHED, cbuf[0]);
            assertEquals('0', cbuf[1]);
            assertEquals('1', cbuf[2]);
            assertEquals(UNTOUCHED, cbuf[3]);
        }
    }
}
