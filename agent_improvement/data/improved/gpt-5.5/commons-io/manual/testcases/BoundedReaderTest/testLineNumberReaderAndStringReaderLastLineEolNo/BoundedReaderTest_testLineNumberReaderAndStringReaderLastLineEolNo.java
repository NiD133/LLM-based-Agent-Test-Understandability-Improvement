package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.time.Duration;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testLineNumberReaderAndStringReaderLastLineEolNo {

    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    private static final String STRING_END_NO_EOL = "0\n1\n2";

    private void readAllLinesThroughBoundedReader(final Reader source) throws IOException {
        try (LineNumberReader reader = new LineNumberReader(new BoundedReader(source, 10_000_000))) {
            while (reader.readLine() != null) {
                // Drain the reader to verify that the unterminated final line reaches EOF.
            }
        }
    }

    @Test
    void testLineNumberReaderAndStringReaderLastLineEolNo() {
        assertTimeout(TIMEOUT, () -> readAllLinesThroughBoundedReader(new StringReader(STRING_END_NO_EOL)));
    }
}
