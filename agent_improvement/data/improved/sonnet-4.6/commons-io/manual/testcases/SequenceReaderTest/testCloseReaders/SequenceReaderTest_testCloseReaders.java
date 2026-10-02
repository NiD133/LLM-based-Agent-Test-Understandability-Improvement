package org.apache.commons.io.input;

import static org.apache.commons.io.IOUtils.EOF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testCloseReaders {

    /**
     * A Reader that tracks whether it has been closed and throws if read after closing.
     */
    private static class TrackingReader extends Reader {

        private boolean closed;

        protected void checkOpen() throws IOException {
            if (closed) {
                throw new IOException("Reader already closed");
            }
        }

        @Override
        public void close() throws IOException {
            closed = true;
        }

        public boolean isClosed() {
            return closed;
        }

        @Override
        public int read(final char[] cbuf, final int off, final int len) throws IOException {
            checkOpen();
            close();
            return EOF;
        }
    }

    /**
     * A TrackingReader that yields exactly one character before returning EOF.
     */
    private static class SingleCharReader extends TrackingReader {

        private final char ch;
        private boolean consumed;

        SingleCharReader(final char ch) {
            this.ch = ch;
        }

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
            if (consumed) {
                return EOF;
            }
            cbuf[off] = ch;
            consumed = true;
            return 1;
        }
    }

    /**
     * Verifies that SequenceReader closes all underlying readers when the
     * try-with-resources block exits, regardless of whether reading succeeds.
     *
     * Sequence: [singleCharReader('A'), emptyReader] → reads 'A', then EOF.
     */
    @Test
    void testCloseReaders() throws IOException {
        final SingleCharReader singleCharReader = new SingleCharReader('A');
        final TrackingReader emptyReader = new TrackingReader();

        try (SequenceReader sequenceReader = new SequenceReader(singleCharReader, emptyReader)) {
            assertEquals('A', sequenceReader.read());
            assertEquals(EOF, sequenceReader.read());
        } finally {
            assertTrue(singleCharReader.isClosed());
            assertTrue(emptyReader.isClosed());
        }
        assertTrue(singleCharReader.isClosed());
        assertTrue(emptyReader.isClosed());
    }
}
