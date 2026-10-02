package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.Reader;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SequenceReader#markSupported()}.
 */
public class SequenceReaderTest_testMarkSupported {

    /**
     * A {@link SequenceReader} does not support mark/reset, so
     * {@link Reader#markSupported()} must always return {@code false}.
     */
    @Test
    void testMarkSupported() throws Exception {
        try (Reader sequenceReader = new SequenceReader()) {
            assertFalse(sequenceReader.markSupported(), "SequenceReader must not support mark/reset");
        }
    }
}
