package org.apache.commons.io.input;

import static org.apache.commons.io.IOUtils.EOF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SequenceReader} closes every underlying reader once the
 * sequence has been fully consumed (and again when the SequenceReader itself is
 * closed).
 */
public class SequenceReaderTest_testCloseReaders {

    /**
     * A {@link Reader} that records whether it has been closed and refuses to be
     * read after closing. By default it behaves as an empty reader: a read
     * closes it and immediately reports end-of-stream.
     */
    private static class TrackingReader extends Reader {

        private boolean closed;

        /** Fails fast if a read is attempted on an already-closed reader. */
        protected void checkOpen() throws IOException {
            if (closed) {
                throw new IOException("reader already closed");
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

    @Test
    void testCloseReaders() throws IOException {
        // An empty reader: the first read closes it and returns EOF.
        final TrackingReader emptyReader = new TrackingReader();

        // A single-character reader that yields 'A' exactly once, then EOF.
        final TrackingReader singleCharReader = new TrackingReader() {

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
        };

        // Read 'A' from the first reader, then EOF once both readers are drained.
        try (SequenceReader sequenceReader = new SequenceReader(singleCharReader, emptyReader)) {
            assertEquals('A', sequenceReader.read());
            assertEquals(EOF, sequenceReader.read());
        } finally {
            // Draining the sequence should already have closed both readers.
            assertTrue(singleCharReader.isClosed());
            assertTrue(emptyReader.isClosed());
        }

        // They remain closed after the SequenceReader is closed.
        assertTrue(singleCharReader.isClosed());
        assertTrue(emptyReader.isClosed());
    }
}
