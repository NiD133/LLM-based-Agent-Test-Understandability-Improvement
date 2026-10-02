package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testSkipTest {

    @Test
    void testSkipTest() throws IOException {
        final Reader sourceReader = new BufferedReader(new StringReader("01234567890"));

        try (BoundedReader boundedReader = new BoundedReader(sourceReader, 3)) {
            boundedReader.skip(2);
            boundedReader.read();

            assertEquals(-1, boundedReader.read());
        }
    }
}
