package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.time.Duration;
import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testReadBytesEOF {

    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    private final Reader sourceReader = new BufferedReader(new StringReader("01234567890"));

    @Test
    void testReadBytesEOF() {
        // Verifies that BoundedReader signals EOF correctly when wrapped in a BufferedReader:
        // the second readLine() call should return null immediately rather than blocking.
        assertTimeout(TIMEOUT, () -> {
            final BoundedReader boundedReader = new BoundedReader(sourceReader, 3);
            try (BufferedReader br = new BufferedReader(boundedReader)) {
                br.readLine();
                br.readLine();
            }
        });
    }
}
