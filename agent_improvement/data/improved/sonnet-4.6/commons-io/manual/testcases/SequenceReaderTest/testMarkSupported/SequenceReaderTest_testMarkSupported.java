package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertFalse;
import java.io.Reader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testMarkSupported {

    @Test
    @DisplayName("SequenceReader does not support mark/reset operations")
    void testMarkSupported() throws Exception {
        try (Reader reader = new SequenceReader()) {
            assertFalse(reader.markSupported());
        }
    }
}
