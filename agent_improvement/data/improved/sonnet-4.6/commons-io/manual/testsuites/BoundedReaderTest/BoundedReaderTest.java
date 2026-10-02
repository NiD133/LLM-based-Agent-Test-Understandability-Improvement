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
 */
class BoundedReaderTest {

    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    /** Default character bound used by most tests: smaller than the long source, larger than the short source. */
    private static final int DEFAULT_BOUND = 3;

    /** Multi-line content whose last line is not terminated by a newline. */
    private static final String CONTENT_NO_TRAILING_NEWLINE = "0\n1\n2";

    /** Multi-line content whose last line is terminated by a newline. */
    private static final String CONTENT_WITH_TRAILING_NEWLINE = "0\n1\n2\n";

    // JUnit creates a fresh test instance per @Test, so these readers are never shared between tests.
    private final Reader longStringReader  = new BufferedReader(new StringReader("01234567890")); // 11 chars
    private final Reader shortStringReader = new BufferedReader(new StringReader("01"));          //  2 chars

    @Test
    void testCloseUnderlyingReaderOnClose() throws IOException {
        final AtomicBoolean closed = new AtomicBoolean();
        try (Reader sr = new BufferedReader(new StringReader("01234567890")) {
            @Override
            public void close() throws IOException {
                closed.set(true);
                super.close();
            }
        }) {
            try (BoundedReader mr = new BoundedReader(sr, DEFAULT_BOUND)) {
                // nothing
            }
        }
        assertTrue(closed.get());
    }

    private void assertLineNumberReaderDoesNotHang(final Reader source) throws IOException {
        try (LineNumberReader reader = new LineNumberReader(new BoundedReader(source, 10_000_000))) {
            while (reader.readLine() != null) {
                // drain all lines
            }
        }
    }

    private void assertLineNumberReaderDoesNotHangWithFileSource(final String data) throws IOException {
        try (TempFile path = TempFile.create(getClass().getSimpleName(), ".txt")) {
            final File file = path.toFile();
            FileUtils.write(file, data, StandardCharsets.ISO_8859_1);
            try (Reader source = Files.newBufferedReader(file.toPath())) {
                assertLineNumberReaderDoesNotHang(source);
            }
        }
    }

    @Test
    void testLineNumberReaderWithFileSourceAndNoTrailingNewline() {
        assertTimeout(TIMEOUT, () -> assertLineNumberReaderDoesNotHangWithFileSource(CONTENT_NO_TRAILING_NEWLINE));
    }

    @Test
    void testLineNumberReaderWithFileSourceAndTrailingNewline() {
        assertTimeout(TIMEOUT, () -> assertLineNumberReaderDoesNotHangWithFileSource(CONTENT_WITH_TRAILING_NEWLINE));
    }

    @Test
    void testLineNumberReaderWithStringSourceAndNoTrailingNewline() {
        assertTimeout(TIMEOUT, () -> assertLineNumberReaderDoesNotHang(new StringReader(CONTENT_NO_TRAILING_NEWLINE)));
    }

    @Test
    void testLineNumberReaderWithStringSourceAndTrailingNewline() {
        assertTimeout(TIMEOUT, () -> assertLineNumberReaderDoesNotHang(new StringReader(CONTENT_WITH_TRAILING_NEWLINE)));
    }

    @Test
    void testMarkResetRestoresPosition() throws IOException {
        try (BoundedReader mr = new BoundedReader(longStringReader, DEFAULT_BOUND)) {
            mr.mark(DEFAULT_BOUND);
            mr.read(); // '0'
            mr.read(); // '1'
            mr.read(); // '2' — at bound
            mr.reset();
            mr.read(); // '0' again after reset
            mr.read(); // '1'
            mr.read(); // '2' — at bound again
            assertEquals(-1, mr.read()); // EOF: bound exhausted
        }
    }

    @Test
    void testMarkResetFromOffset1() throws IOException {
        try (BoundedReader mr = new BoundedReader(longStringReader, DEFAULT_BOUND)) {
            mr.mark(DEFAULT_BOUND);
            mr.read(); // '0'
            mr.read(); // '1'
            mr.read(); // '2' — at bound
            assertEquals(-1, mr.read()); // EOF before reset
            mr.reset();
            mr.mark(1);
            mr.read(); // '0' again after reset
            assertEquals(-1, mr.read()); // EOF: new read-ahead limit of 1 exhausted
        }
    }

    @Test
    void testMarkResetWithReadAheadLimitLargerThanBound() throws IOException {
        // read-ahead limit (4) exceeds bound (3); the bound still caps total reads
        try (BoundedReader mr = new BoundedReader(longStringReader, DEFAULT_BOUND)) {
            mr.mark(DEFAULT_BOUND + 1);
            mr.read(); // '0'
            mr.read(); // '1'
            mr.read(); // '2' — at bound
            mr.reset();
            mr.read(); // '0' again
            mr.read(); // '1'
            mr.read(); // '2' — at bound again
            assertEquals(-1, mr.read()); // EOF: bound enforced despite larger read-ahead limit
        }
    }

    @Test
    void testMarkResetWithMarkOutsideBoundedReaderMax() throws IOException {
        // read-ahead limit (4) exceeds bound (3); no reset is called — bound caps reads without recovery
        try (BoundedReader mr = new BoundedReader(longStringReader, DEFAULT_BOUND)) {
            mr.mark(DEFAULT_BOUND + 1);
            mr.read(); // '0'
            mr.read(); // '1'
            mr.read(); // '2' — at bound
            assertEquals(-1, mr.read()); // EOF: bound enforced
        }
    }

    @Test
    void testMarkResetWithMarkOutsideBoundedReaderMaxAndInitialOffset() throws IOException {
        // one char is read before mark(); total chars allowed is still DEFAULT_BOUND
        try (BoundedReader mr = new BoundedReader(longStringReader, DEFAULT_BOUND)) {
            mr.read();              // '0' — consumed before mark
            mr.mark(DEFAULT_BOUND);
            mr.read();              // '1'
            mr.read();              // '2' — total reads == bound
            assertEquals(-1, mr.read()); // EOF: bound reached (pre-mark read counts toward total)
        }
    }

    @Test
    void testBufferedReadDoesNotHangAtEof() {
        assertTimeout(TIMEOUT, () -> {
            final BoundedReader mr = new BoundedReader(longStringReader, DEFAULT_BOUND);
            try (BufferedReader br = new BufferedReader(mr)) {
                br.readLine();
                br.readLine();
            }
        });
    }

    @Test
    void testReadMulti() throws IOException {
        final char SENTINEL = 'X'; // marks buffer positions that must remain untouched
        try (BoundedReader mr = new BoundedReader(longStringReader, DEFAULT_BOUND)) {
            final char[] cbuf = new char[4];
            Arrays.fill(cbuf, SENTINEL);
            final int read = mr.read(cbuf, 0, 4); // request 4, but bound limits to DEFAULT_BOUND
            assertEquals(DEFAULT_BOUND, read);
            assertEquals('0', cbuf[0]);
            assertEquals('1', cbuf[1]);
            assertEquals('2', cbuf[2]);
            assertEquals(SENTINEL, cbuf[3]); // fourth slot not overwritten
        }
    }

    @Test
    void testReadMultiWithOffset() throws IOException {
        final char SENTINEL = 'X';
        try (BoundedReader mr = new BoundedReader(longStringReader, DEFAULT_BOUND)) {
            final char[] cbuf = new char[4];
            Arrays.fill(cbuf, SENTINEL);
            final int read = mr.read(cbuf, 1, 2); // write into cbuf[1] and cbuf[2]
            assertEquals(2, read);
            assertEquals(SENTINEL, cbuf[0]); // slot before offset untouched
            assertEquals('0', cbuf[1]);
            assertEquals('1', cbuf[2]);
            assertEquals(SENTINEL, cbuf[3]); // slot after range untouched
        }
    }

    @Test
    void testReadReturnsEofAfterBoundExhausted() throws IOException {
        try (BoundedReader mr = new BoundedReader(longStringReader, DEFAULT_BOUND)) {
            mr.read(); // '0'
            mr.read(); // '1'
            mr.read(); // '2' — reaches bound
            assertEquals(-1, mr.read()); // EOF: bound exhausted
        }
    }

    @Test
    void testEofWhenSourceShorterThanBound() throws IOException {
        // shortStringReader has only 2 chars; bound is DEFAULT_BOUND (3). Source runs out first.
        try (BoundedReader mr = new BoundedReader(shortStringReader, DEFAULT_BOUND)) {
            mr.read(); // '0'
            mr.read(); // '1' — source exhausted
            assertEquals(-1, mr.read()); // EOF from underlying reader, not from bound
        }
    }

    @Test
    void testSkipCountsTowardBound() throws IOException {
        try (BoundedReader mr = new BoundedReader(longStringReader, DEFAULT_BOUND)) {
            mr.skip(2);           // skip '0' and '1' (2 of 3 allowed chars consumed)
            mr.read();            // '2' — last char within bound
            assertEquals(-1, mr.read()); // EOF: bound reached
        }
    }
}
