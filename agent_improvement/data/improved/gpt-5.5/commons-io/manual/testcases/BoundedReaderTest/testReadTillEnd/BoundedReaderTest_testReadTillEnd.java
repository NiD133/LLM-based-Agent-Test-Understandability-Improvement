package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testReadTillEnd {

    private final Reader readerWithMoreContentThanBound = new BufferedReader(new StringReader("01234567890"));

    @Test
    void testReadTillEnd() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(readerWithMoreContentThanBound, 3)) {
            boundedReader.read();
            boundedReader.read();
            boundedReader.read();

            assertEquals(-1, boundedReader.read());
        }
    }
}
