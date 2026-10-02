package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testMarkResetWithMarkOutsideBoundedReaderMax {

    @Test
    void testMarkResetWithMarkOutsideBoundedReaderMax() throws IOException {
        final int boundedReaderMaxChars = 3;
        final int markReadAheadLimit = 4;
        final Reader sourceReader = new BufferedReader(new StringReader("01234567890"));

        try (BoundedReader boundedReader = new BoundedReader(sourceReader, boundedReaderMaxChars)) {
            boundedReader.mark(markReadAheadLimit);

            boundedReader.read();
            boundedReader.read();
            boundedReader.read();

            assertEquals(-1, boundedReader.read());
        }
    }
}
