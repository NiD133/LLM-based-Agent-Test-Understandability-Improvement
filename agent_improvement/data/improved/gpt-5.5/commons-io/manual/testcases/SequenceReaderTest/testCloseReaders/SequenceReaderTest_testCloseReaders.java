package org.apache.commons.io.input;

import static org.apache.commons.io.IOUtils.EOF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testCloseReaders {

    private static class CloseTrackingReader extends Reader {

        private boolean closed;

        protected void checkOpen() throws IOException {
            if (closed) {
                throw new IOException("emptyReader already closed");
            }
        }

        @Override
        public void close() throws IOException {
            closed = true;
        }

        boolean isClosed() {
            return closed;
        }

        @Override
        public int read(final char[] cbuf, final int off, final int len) throws IOException {
            checkOpen();
            close();
            return EOF;
        }
    }

    private static final class SingleCharacterReader extends CloseTrackingReader {

        private final char[] content = { 'A' };
        private int position;

        @Override
        public int read(final char[] cbuf, final int off, final int len) throws IOException {
            checkOpen();
            if (off < 0) {
                throw new IndexOutOfBoundsException("off is negative");
            }
            if (len < 0) {
                throw new IndexOutOfBoundsException("len is negative");
            }
            if (len > cbuf.length - off) {
                throw new IndexOutOfBoundsException("len is greater than cbuf.length - off");
            }
            if (position > 0) {
                return EOF;
            }
            cbuf[off] = content[0];
            position++;
            return 1;
        }
    }

    @Test
    void testCloseReaders() throws IOException {
        final CloseTrackingReader emptyReader = new CloseTrackingReader();
        final CloseTrackingReader singleCharacterReader = new SingleCharacterReader();

        try (SequenceReader sequenceReader = new SequenceReader(singleCharacterReader, emptyReader)) {
            assertEquals('A', sequenceReader.read());
            assertEquals(EOF, sequenceReader.read());
        } finally {
            assertTrue(singleCharacterReader.isClosed());
            assertTrue(emptyReader.isClosed());
        }
        assertTrue(singleCharacterReader.isClosed());
        assertTrue(emptyReader.isClosed());
    }
}
