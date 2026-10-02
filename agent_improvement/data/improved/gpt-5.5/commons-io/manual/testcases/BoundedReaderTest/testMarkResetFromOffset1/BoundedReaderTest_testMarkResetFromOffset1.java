package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testMarkResetFromOffset1 {

    @Test
    void testMarkResetFromOffset1() throws IOException {
        final Reader source = new BufferedReader(new StringReader("01234567890"));

        try (BoundedReader boundedReader = new BoundedReader(source, 3)) {
            boundedReader.mark(3);
            boundedReader.read();
            boundedReader.read();
            boundedReader.read();
            assertEquals(-1, boundedReader.read());

            boundedReader.reset();

            boundedReader.mark(1);
            boundedReader.read();
            assertEquals(-1, boundedReader.read());
        }
    }
}
