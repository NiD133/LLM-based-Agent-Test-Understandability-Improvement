/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.file.TempFile;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link BoundedReader}.
 *
 * <p>
 * {@link BoundedReader} wraps another {@link Reader} and reports EOF (-1) once a configured maximum number of
 * characters has been read, regardless of how much data the underlying reader still holds. Most tests below use a
 * bound of {@value #MAX_CHARS} characters over a reader that actually contains more data, so the bound (not the
 * underlying data) is what stops reading.
 * </p>
 */
class BoundedReaderTest {

    /** Upper bound for tests that wrap a possibly never-ending read in a time limit. */
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    /** The character limit imposed on the {@link BoundedReader} in most tests. */
    private static final int MAX_CHARS = 3;

    /** Three lines of content with no trailing end-of-line after the last line. */
    private static final String STRING_END_NO_EOL = "0\n1\n2";

    /** Three lines of content with a trailing end-of-line after the last line. */
    private static final String STRING_END_EOL = "0\n1\n2\n";

    /** Underlying reader with 11 characters ("01234567890"), i.e. more than {@link #MAX_CHARS}. */
    private final Reader underlyingReaderLong = new BufferedReader(new StringReader("01234567890"));

    /** Underlying reader with only 2 characters ("01"), i.e. fewer than {@link #MAX_CHARS}. */
    private final Reader underlyingReaderShort = new BufferedReader(new StringReader("01"));

    /**
     * Drives the given source through a {@link BoundedReader} (with a very large bound) and a
     * {@link LineNumberReader}, reading every line. The large bound ensures the bound never trips, so this exercises
     * normal line-by-line reading to EOF.
     */
    private void readAllLines(final Reader source) throws IOException {
        try (LineNumberReader reader = new LineNumberReader(new BoundedReader(source, 10_000_000))) {
            while (reader.readLine() != null) {
                // Consume every line; we only care that reading terminates.
            }
        }
    }

    /**
     * Writes {@code data} to a temporary file, then reads it back through {@link #readAllLines(Reader)} using a
     * file-backed reader.
     */
    private void readAllLinesFromTempFile(final String data) throws IOException {
        try (TempFile path = TempFile.create(getClass().getSimpleName(), ".txt")) {
            final File file = path.toFile();
            FileUtils.write(file, data, StandardCharsets.ISO_8859_1);
            try (Reader source = Files.newBufferedReader(file.toPath())) {
                readAllLines(source);
            }
        }
    }

    /**
     * Closing the {@link BoundedReader} must close the underlying reader.
     */
    @Test
    void testCloseClosesUnderlyingReader() throws IOException {
        final AtomicBoolean closed = new AtomicBoolean();
        try (Reader underlying = new BufferedReader(new StringReader("01234567890")) {
            @Override
            public void close() throws IOException {
                closed.set(true);
                super.close();
            }
        }) {
            try (BoundedReader boundedReader = new BoundedReader(underlying, MAX_CHARS)) {
                // Intentionally read nothing; we only verify close propagation.
            }
        }
        assertTrue(closed.get());
    }

    /**
     * Reading lines from a file whose last line has no trailing EOL must terminate within the timeout.
     */
    @Test
    void testLineNumberReaderAndFileReaderLastLineEolNo() {
        assertTimeout(TIMEOUT, () -> readAllLinesFromTempFile(STRING_END_NO_EOL));
    }

    /**
     * Reading lines from a file whose last line has a trailing EOL must terminate within the timeout.
     */
    @Test
    void testLineNumberReaderAndFileReaderLastLineEolYes() {
        assertTimeout(TIMEOUT, () -> readAllLinesFromTempFile(STRING_END_EOL));
    }

    /**
     * Reading lines from a string whose last line has no trailing EOL must terminate within the timeout.
     */
    @Test
    void testLineNumberReaderAndStringReaderLastLineEolNo() {
        assertTimeout(TIMEOUT, () -> readAllLines(new StringReader(STRING_END_NO_EOL)));
    }

    /**
     * Reading lines from a string whose last line has a trailing EOL must terminate within the timeout.
     */
    @Test
    void testLineNumberReaderAndStringReaderLastLineEolYes() {
        assertTimeout(TIMEOUT, () -> readAllLines(new StringReader(STRING_END_EOL)));
    }

    /**
     * After mark/reset, reading should still be capped at {@link #MAX_CHARS} total characters: read 3, reset to the
     * mark, read 3 again, then hit EOF.
     */
    @Test
    void testMarkReset() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(underlyingReaderLong, MAX_CHARS)) {
            boundedReader.mark(MAX_CHARS);
            boundedReader.read();
            boundedReader.read();
            boundedReader.read();
            boundedReader.reset();
            boundedReader.read();
            boundedReader.read();
            boundedReader.read();
            assertEquals(-1, boundedReader.read());
        }
    }

    /**
     * After exhausting the bound, resetting and marking with a smaller read-ahead limit still yields EOF once that
     * smaller limit is reached.
     */
    @Test
    void testMarkResetFromOffset1() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(underlyingReaderLong, MAX_CHARS)) {
            boundedReader.mark(MAX_CHARS);
            boundedReader.read();
            boundedReader.read();
            boundedReader.read();
            assertEquals(-1, boundedReader.read());
            boundedReader.reset();
            boundedReader.mark(1);
            boundedReader.read();
            assertEquals(-1, boundedReader.read());
        }
    }

    /**
     * A read-ahead limit larger than the bound has no effect: the bound of {@link #MAX_CHARS} still applies after
     * mark/reset.
     */
    @Test
    void testMarkResetMarkMore() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(underlyingReaderLong, MAX_CHARS)) {
            boundedReader.mark(4);
            boundedReader.read();
            boundedReader.read();
            boundedReader.read();
            boundedReader.reset();
            boundedReader.read();
            boundedReader.read();
            boundedReader.read();
            assertEquals(-1, boundedReader.read());
        }
    }

    /**
     * When the read-ahead limit exceeds the bound, the bound still stops reading at {@link #MAX_CHARS} characters.
     */
    @Test
    void testMarkResetWithMarkOutsideBoundedReaderMax() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(underlyingReaderLong, MAX_CHARS)) {
            boundedReader.mark(4);
            boundedReader.read();
            boundedReader.read();
            boundedReader.read();
            assertEquals(-1, boundedReader.read());
        }
    }

    /**
     * Marking after an initial read (so the mark starts at offset 1) with a read-ahead limit beyond the bound still
     * stops at the overall {@link #MAX_CHARS} bound.
     */
    @Test
    void testMarkResetWithMarkOutsideBoundedReaderMaxAndInitialOffset() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(underlyingReaderLong, MAX_CHARS)) {
            boundedReader.read();
            boundedReader.mark(MAX_CHARS);
            boundedReader.read();
            boundedReader.read();
            assertEquals(-1, boundedReader.read());
        }
    }

    /**
     * Reading lines through a {@link BufferedReader} backed by a {@link BoundedReader} must reach EOF and finish
     * within the timeout.
     */
    @Test
    void testReadBytesEOF() {
        assertTimeout(TIMEOUT, () -> {
            final BoundedReader boundedReader = new BoundedReader(underlyingReaderLong, MAX_CHARS);
            try (BufferedReader bufferedReader = new BufferedReader(boundedReader)) {
                bufferedReader.readLine();
                bufferedReader.readLine();
            }
        });
    }

    /**
     * A bulk read requesting more than the bound returns only {@link #MAX_CHARS} characters and leaves the rest of the
     * buffer untouched.
     */
    @Test
    void testReadMulti() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(underlyingReaderLong, MAX_CHARS)) {
            final char[] buffer = new char[4];
            Arrays.fill(buffer, 'X');
            final int charsRead = boundedReader.read(buffer, 0, 4);
            assertEquals(3, charsRead);
            assertEquals('0', buffer[0]);
            assertEquals('1', buffer[1]);
            assertEquals('2', buffer[2]);
            assertEquals('X', buffer[3]); // Untouched: only 3 characters were read.
        }
    }

    /**
     * A bulk read into the middle of the buffer (offset 1, length 2) fills exactly that slice and leaves the
     * surrounding positions untouched.
     */
    @Test
    void testReadMultiWithOffset() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(underlyingReaderLong, MAX_CHARS)) {
            final char[] buffer = new char[4];
            Arrays.fill(buffer, 'X');
            final int charsRead = boundedReader.read(buffer, 1, 2);
            assertEquals(2, charsRead);
            assertEquals('X', buffer[0]); // Before the offset: untouched.
            assertEquals('0', buffer[1]);
            assertEquals('1', buffer[2]);
            assertEquals('X', buffer[3]); // Beyond the requested length: untouched.
        }
    }

    /**
     * Reading exactly {@link #MAX_CHARS} characters one at a time leaves the reader at EOF.
     */
    @Test
    void testReadTillEnd() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(underlyingReaderLong, MAX_CHARS)) {
            boundedReader.read();
            boundedReader.read();
            boundedReader.read();
            assertEquals(-1, boundedReader.read());
        }
    }

    /**
     * When the underlying reader holds fewer characters than the bound, EOF comes from the exhausted underlying
     * reader rather than from the bound.
     */
    @Test
    void testShortReader() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(underlyingReaderShort, MAX_CHARS)) {
            boundedReader.read();
            boundedReader.read();
            assertEquals(-1, boundedReader.read());
        }
    }

    /**
     * Skipped characters count toward the bound: skipping 2 then reading 1 reaches the bound, so the next read is EOF.
     */
    @Test
    void testSkipTest() throws IOException {
        try (BoundedReader boundedReader = new BoundedReader(underlyingReaderLong, MAX_CHARS)) {
            boundedReader.skip(2);
            boundedReader.read();
            assertEquals(-1, boundedReader.read());
        }
    }
}
