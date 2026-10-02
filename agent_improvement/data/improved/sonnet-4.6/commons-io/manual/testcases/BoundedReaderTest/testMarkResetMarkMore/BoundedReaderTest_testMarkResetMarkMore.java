package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testMarkResetMarkMore {

    // A reader bounded to 3 characters from a longer source string
    private final Reader sourceReader = new BufferedReader(new StringReader("01234567890"));

    @Test
    void testMarkResetMarkMore() throws IOException {
        // BoundedReader limits total characters to 3; mark(4) sets a reset point
        // with readAheadLimit larger than the remaining bound.
        try (BoundedReader mr = new BoundedReader(sourceReader, 3)) {
            mr.mark(4);

            // Read 3 characters up to the bound
            mr.read();
            mr.read();
            mr.read();

            // Reset back to the mark position (charsRead returns to 0)
            mr.reset();

            // Re-read the same 3 characters after reset
            mr.read();
            mr.read();
            mr.read();

            // The bound of 3 is exhausted; any further read must return EOF
            assertEquals(-1, mr.read());
        }
    }
}
