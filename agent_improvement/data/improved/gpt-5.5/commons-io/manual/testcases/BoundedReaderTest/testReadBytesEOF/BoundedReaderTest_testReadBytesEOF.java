package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.time.Duration;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testReadBytesEOF {

    private static final Duration TIMEOUT = Duration.ofSeconds(10);
    private static final int MAXIMUM_READABLE_CHARACTERS = 3;
    private static final String CONTENT_LONGER_THAN_BOUND = "01234567890";

    private final Reader sourceReader = new BufferedReader(new StringReader(CONTENT_LONGER_THAN_BOUND));

    @Test
    void testReadBytesEOF() {
        assertTimeout(TIMEOUT, () -> {
            final BoundedReader boundedReader = new BoundedReader(sourceReader, MAXIMUM_READABLE_CHARACTERS);
            try (BufferedReader lineReader = new BufferedReader(boundedReader)) {
                lineReader.readLine();
                lineReader.readLine();
            }
        });
    }
}
