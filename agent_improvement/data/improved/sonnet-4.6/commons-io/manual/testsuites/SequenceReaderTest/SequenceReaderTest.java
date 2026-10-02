/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.io.input;

import static org.apache.commons.io.IOUtils.EOF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link SequenceReader}.
 */
class SequenceReaderTest {

    /**
     * A Reader that tracks whether it has been closed and returns EOF on the first read.
     * Used as a base class for more specific test readers.
     */
    private static class CustomReader extends Reader {

        private boolean closed;

        protected void checkOpen() throws IOException {
            if (closed) {
                throw new IOException("reader already closed");
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
     * A CustomReader that returns the single character 'A' on the first read, then EOF.
     */
    private static class SingleCharReader extends CustomReader {

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

    /** Sentinel value for uninitialized char array slots (the zero char, '\0'). */
    private static final char NUL = 0;

    private void checkArray(final char[] expected, final char[] actual) {
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i], "Compare[" + i + "]");
        }
    }

    private void checkRead(final Reader reader, final String expected) throws IOException {
        for (int i = 0; i < expected.length(); i++) {
            assertEquals(expected.charAt(i), (char) reader.read(), "Read[" + i + "] of '" + expected + "'");
        }
    }

    private void checkReadEof(final Reader reader) throws IOException {
        for (int i = 0; i < 10; i++) {
            assertEquals(EOF, reader.read());
        }
    }

    /**
     * Verifies that explicitly closing inside a try-with-resources block (which causes a second
     * close on exit) does not throw and continues to return EOF on subsequent reads.
     */
    @Test
    void testAutoClose() throws IOException {
        try (Reader reader = new SequenceReader(new CharSequenceReader("FooBar"))) {
            checkRead(reader, "Foo");
            reader.close();
            checkReadEof(reader);
        }
    }

    /** Verifies that closing the reader causes subsequent reads to return EOF. */
    @Test
    void testClose() throws IOException {
        final Reader reader = new SequenceReader(new CharSequenceReader("FooBar"));
        checkRead(reader, "Foo");
        reader.close();
        checkReadEof(reader);
    }

    /** Verifies that all underlying readers are closed when the SequenceReader is closed. */
    @Test
    void testCloseReaders() throws IOException {
        final CustomReader emptyReader = new CustomReader();
        final SingleCharReader singleCharReader = new SingleCharReader();

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

    @Test
    void testMarkSupported() throws Exception {
        try (Reader reader = new SequenceReader()) {
            assertFalse(reader.markSupported());
        }
    }

    @Test
    void testRead() throws IOException {
        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {
            assertEquals('F', reader.read());
            assertEquals('o', reader.read());
            assertEquals('o', reader.read());
            assertEquals('B', reader.read());
            assertEquals('a', reader.read());
            assertEquals('r', reader.read());
            checkReadEof(reader);
        }
    }

    @Test
    void testReadCharArray() throws IOException {
        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {
            char[] chars = new char[2];
            assertEquals(2, reader.read(chars));
            checkArray(new char[] { 'F', 'o' }, chars);
            chars = new char[3];
            assertEquals(3, reader.read(chars));
            checkArray(new char[] { 'o', 'B', 'a' }, chars);
            chars = new char[3];
            assertEquals(1, reader.read(chars));
            checkArray(new char[] { 'r', NUL, NUL }, chars);
            assertEquals(EOF, reader.read(chars));
        }
    }

    @Test
    void testReadCharArrayPortion() throws IOException {
        final char[] chars = new char[10];
        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {
            assertEquals(3, reader.read(chars, 3, 3));
            checkArray(new char[] { NUL, NUL, NUL, 'F', 'o', 'o' }, chars);
            assertEquals(3, reader.read(chars, 0, 3));
            checkArray(new char[] { 'B', 'a', 'r', 'F', 'o', 'o', NUL }, chars);
            assertEquals(EOF, reader.read(chars));
            assertThrows(IndexOutOfBoundsException.class, () -> reader.read(chars, 10, 10));
            assertThrows(NullPointerException.class, () -> reader.read(null, 0, 10));
        }
    }

    @Test
    void testReadClosedReader() throws IOException {
        // Intentionally closed before reading; suppress the "resource not closed in try" warning.
        @SuppressWarnings("resource")
        final Reader reader = new SequenceReader(new CharSequenceReader("FooBar"));
        reader.close();
        checkReadEof(reader);
    }

    /** Verifies that {@link SequenceReader} accepts a {@link Collection} of readers. */
    @Test
    void testReadCollection() throws IOException {
        final Collection<Reader> readers = new ArrayList<>();
        readers.add(new StringReader("F"));
        readers.add(new StringReader("B"));
        try (Reader reader = new SequenceReader(readers)) {
            assertEquals('F', reader.read());
            assertEquals('B', reader.read());
            checkReadEof(reader);
        }
    }

    /** Verifies that {@link SequenceReader} accepts a raw {@link Iterable} of readers. */
    @Test
    void testReadIterable() throws IOException {
        final Collection<Reader> readers = new ArrayList<>();
        readers.add(new StringReader("F"));
        readers.add(new StringReader("B"));
        final Iterable<Reader> iterable = readers;
        try (Reader reader = new SequenceReader(iterable)) {
            assertEquals('F', reader.read());
            assertEquals('B', reader.read());
            checkReadEof(reader);
        }
    }

    @Test
    void testReadLength0Readers() throws IOException {
        try (Reader reader = new SequenceReader(new StringReader(StringUtils.EMPTY),
            new StringReader(StringUtils.EMPTY), new StringReader(StringUtils.EMPTY))) {
            checkReadEof(reader);
        }
    }

    @Test
    void testReadLength1Readers() throws IOException {
        try (Reader reader = new SequenceReader(
            new StringReader("0"),
            new StringReader("1"),
            new StringReader("2"),
            new StringReader("3"))) {
            assertEquals('0', reader.read());
            assertEquals('1', reader.read());
            assertEquals('2', reader.read());
            assertEquals('3', reader.read());
        }
    }

    /** Verifies that {@link SequenceReader} accepts a {@link List} of readers. */
    @Test
    void testReadList() throws IOException {
        final List<Reader> readers = new ArrayList<>();
        readers.add(new StringReader("F"));
        readers.add(new StringReader("B"));
        try (Reader reader = new SequenceReader(readers)) {
            assertEquals('F', reader.read());
            assertEquals('B', reader.read());
            checkReadEof(reader);
        }
    }

    @Test
    void testSkip() throws IOException {
        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {
            assertEquals(3, reader.skip(3));
            checkRead(reader, "Bar");
            assertEquals(0, reader.skip(3));
        }
    }
}
