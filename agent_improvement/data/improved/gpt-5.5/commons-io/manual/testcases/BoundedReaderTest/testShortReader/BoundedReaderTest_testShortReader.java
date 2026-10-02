package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testShortReader {

    private final Reader twoCharacterReader = new BufferedReader(new StringReader("01"));

    @Test
    void testShortReader() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(twoCharacterReader, 3)) {
            boundedReader.read();
            boundedReader.read();

            assertEquals(-1, boundedReader.read());
        }
    }
}
