package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link SequenceReader} built from several empty readers behaves
 * like an exhausted stream: every {@link Reader#read()} call reports
 * end-of-file (-1).
 */
public class SequenceReaderTest_testReadLength0Readers {

    /** Value returned by {@link Reader#read()} once no more characters are available. */
    private static final int END_OF_FILE = -1;

    /** Number of consecutive reads used to confirm the reader stays at end-of-file. */
    private static final int READS_TO_VERIFY = 10;

    /**
     * Asserts that the reader is exhausted by reading from it several times in a
     * row; each read must report end-of-file.
     */
    private void assertReaderIsAtEndOfFile(final Reader reader) throws IOException {
        for (int i = 0; i < READS_TO_VERIFY; i++) {
            assertEquals(END_OF_FILE, reader.read());
        }
    }

    @Test
    void testReadLength0Readers() throws IOException {
        final Reader firstEmptyReader = new StringReader(StringUtils.EMPTY);
        final Reader secondEmptyReader = new StringReader(StringUtils.EMPTY);
        final Reader thirdEmptyReader = new StringReader(StringUtils.EMPTY);

        try (Reader sequenceOfEmptyReaders =
                new SequenceReader(firstEmptyReader, secondEmptyReader, thirdEmptyReader)) {
            assertReaderIsAtEndOfFile(sequenceOfEmptyReaders);
        }
    }
}
