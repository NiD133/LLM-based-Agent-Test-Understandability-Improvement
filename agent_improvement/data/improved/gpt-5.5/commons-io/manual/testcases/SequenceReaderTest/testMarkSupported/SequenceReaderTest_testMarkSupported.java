package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.Reader;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testMarkSupported {

    @Test
    void testMarkSupported() throws Exception {
        try (Reader emptySequenceReader = new SequenceReader()) {
            assertFalse(emptySequenceReader.markSupported());
        }
    }
}
