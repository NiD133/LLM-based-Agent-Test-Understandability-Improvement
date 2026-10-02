package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testReadMultiWithOffset {

    private final Reader bufReader1 = new BufferedReader(new StringReader("01234567890"));

    @Test
    void testReadMultiWithOffset() throws IOException {
        final int readLimit = 3;
        final int offset = 1;
        final int length = 2;
        final char untouchedValue = 'X';

        try (BoundedReader mr = new BoundedReader(bufReader1, readLimit)) {
            final char[] cbuf = new char[4];
            Arrays.fill(cbuf, untouchedValue);

            final int read = mr.read(cbuf, offset, length);

            assertEquals(2, read);
            assertEquals('X', cbuf[0]);
            assertEquals('0', cbuf[1]);
            assertEquals('1', cbuf[2]);
            assertEquals('X', cbuf[3]);
        }
    }
}
