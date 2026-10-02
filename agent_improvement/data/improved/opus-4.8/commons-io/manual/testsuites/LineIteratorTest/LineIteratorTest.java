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
package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests {@link LineIterator}.
 */
class LineIteratorTest {

    /** Canonical name of the UTF-8 charset, used as the default test encoding. */
    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    /** JUnit-managed temporary directory; a fresh directory is created per test. */
    @TempDir
    public File temporaryFolder;

    /**
     * Asserts that the iterator returns exactly the {@code expectedLines}, in order, via
     * {@link LineIterator#nextLine()}, and that it is exhausted afterwards. The iterator
     * is always closed, even if an assertion fails.
     *
     * @param expectedLines the lines the iterator is expected to yield.
     * @param iterator the iterator under test.
     */
    private void assertLines(final List<String> expectedLines, final LineIterator iterator) {
        try {
            for (int i = 0; i < expectedLines.size(); i++) {
                final String line = iterator.nextLine();
                assertEquals(expectedLines.get(i), line, "nextLine() line " + i);
            }
            assertFalse(iterator.hasNext(), "No more expected");
        } finally {
            IOUtils.closeQuietly(iterator);
        }
    }

    /**
     * Writes a test file containing {@code lineCount} lines using the platform default encoding.
     *
     * @param file target file.
     * @param lineCount number of lines to create.
     * @return the lines that were written, in order.
     * @throws IOException If an I/O error occurs.
     */
    private List<String> createLinesFile(final File file, final int lineCount) throws IOException {
        final List<String> lines = createStringLines(lineCount);
        FileUtils.writeLines(file, lines);
        return lines;
    }

    /**
     * Writes a test file containing {@code lineCount} lines using the given encoding.
     *
     * @param file target file.
     * @param encoding the encoding to use while writing the lines.
     * @param lineCount number of lines to create.
     * @return the lines that were written, in order.
     * @throws IOException If an I/O error occurs.
     */
    private List<String> createLinesFile(final File file, final String encoding, final int lineCount) throws IOException {
        final List<String> lines = createStringLines(lineCount);
        FileUtils.writeLines(file, encoding, lines);
        return lines;
    }

    /**
     * Builds a list of {@code lineCount} synthetic lines of the form {@code "LINE 0"}, {@code "LINE 1"}, ...
     *
     * @param lineCount number of lines to create.
     * @return a new lines list.
     */
    private List<String> createStringLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }

    /**
     * Writes a file with the given number of lines, then iterates over it verifying that every
     * line is returned in order, that {@link LineIterator#remove()} is unsupported, and that the
     * iterator throws once exhausted.
     *
     * @param lineCount the number of lines to create in the test file.
     * @throws IOException If an I/O error occurs while creating the file.
     */
    private void doTestFileWithSpecifiedLines(final int lineCount) throws IOException {
        final String encoding = UTF_8;

        final String fileName = "LineIterator-" + lineCount + "-test.txt";
        final File testFile = new File(temporaryFolder, fileName);
        final List<String> lines = createLinesFile(testFile, encoding, lineCount);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, encoding)) {
            // remove() is never supported.
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            int idx = 0;
            while (iterator.hasNext()) {
                final String line = iterator.next();
                assertEquals(lines.get(idx), line, "Comparing line " + idx);
                assertTrue(idx < lines.size(), "Exceeded expected idx=" + idx + " size=" + lines.size());
                idx++;
            }
            assertEquals(idx, lines.size(), "Line Count doesn't match");

            // Once every line has been consumed, both accessors must signal exhaustion.
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }

    @Test
    void testCloseEarly() throws Exception {
        final String encoding = UTF_8;

        final File testFile = new File(temporaryFolder, "LineIterator-closeEarly.txt");
        createLinesFile(testFile, encoding, 3);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, encoding)) {
            // Read a single line, leaving more lines available.
            assertNotNull("Line expected", iterator.next());
            assertTrue(iterator.hasNext(), "More expected");

            // Closing early must make the iterator behave as exhausted.
            iterator.close();
            assertFalse(iterator.hasNext(), "No more expected");
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);

            // close() is idempotent and the iterator stays exhausted.
            iterator.close();
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }

    @Test
    void testConstructor() {
        assertThrows(NullPointerException.class, () -> new LineIterator(null));
    }

    /**
     * Iterates over a {@link LineIterator} whose {@code isValidLine} accepts only lines whose
     * trailing digit {@code d} satisfies {@code d % 3 != 1}. For lines {@code "LINE 0".."LINE 8"}
     * this filters out the lines ending in 1, 4 and 7, leaving 6 of the 9 lines.
     *
     * @param expectedLines all lines present in the source (the unfiltered set of 9 lines).
     * @param reader the reader to iterate over.
     * @throws IOException If an I/O error occurs.
     */
    private void testFiltering(final List<String> expectedLines, final Reader reader) throws IOException {
        try (LineIterator iterator = new LineIterator(reader) {
            @Override
            protected boolean isValidLine(final String line) {
                // Inspect the trailing digit of e.g. "LINE 7"; reject lines where digit % 3 == 1.
                final char lastChar = line.charAt(line.length() - 1);
                final int lastDigit = lastChar - '0';
                return lastDigit % 3 != 1;
            }
        }) {
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            int expectedIdx = 0;
            int returnedLineCount = 0;
            while (iterator.hasNext()) {
                final String line = iterator.next();
                returnedLineCount++;
                assertEquals(expectedLines.get(expectedIdx), line, "Comparing line " + expectedIdx);
                assertTrue(expectedIdx < expectedLines.size(),
                        "Exceeded expected idx=" + expectedIdx + " size=" + expectedLines.size());
                expectedIdx++;
                // Skip over the filtered-out line (the one whose digit % 3 == 1).
                if (expectedIdx % 3 == 1) {
                    expectedIdx++;
                }
            }
            assertEquals(9, expectedLines.size(), "Line Count doesn't match");
            assertEquals(9, expectedIdx, "Line Count doesn't match");
            assertEquals(6, returnedLineCount, "Line Count doesn't match");

            // Once every valid line has been consumed, both accessors must signal exhaustion.
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }

    @Test
    void testFilteringBufferedReader() throws Exception {
        final String encoding = UTF_8;

        final String fileName = "LineIterator-Filter-test.txt";
        final File testFile = new File(temporaryFolder, fileName);
        final List<String> lines = createLinesFile(testFile, encoding, 9);

        final Reader reader = new BufferedReader(Files.newBufferedReader(testFile.toPath()));
        testFiltering(lines, reader);
    }

    @Test
    void testFilteringFileReader() throws Exception {
        final String encoding = UTF_8;

        final String fileName = "LineIterator-Filter-test.txt";
        final File testFile = new File(temporaryFolder, fileName);
        final List<String> lines = createLinesFile(testFile, encoding, 9);

        final Reader reader = Files.newBufferedReader(testFile.toPath());
        testFiltering(lines, reader);
    }

    @Test
    void testInvalidEncoding() throws Exception {
        final String invalidEncoding = "XXXXXXXX";

        final File testFile = new File(temporaryFolder, "LineIterator-invalidEncoding.txt");
        createLinesFile(testFile, UTF_8, 3);

        assertThrows(UnsupportedCharsetException.class, () -> FileUtils.lineIterator(testFile, invalidEncoding));
    }

    @Test
    void testMissingFile() throws Exception {
        final File testFile = new File(temporaryFolder, "dummy-missing-file.txt");
        assertThrows(NoSuchFileException.class, () -> FileUtils.lineIterator(testFile, UTF_8));
    }

    @Test
    void testNextLineOnlyDefaultEncoding() throws Exception {
        final File testFile = new File(temporaryFolder, "LineIterator-nextOnly.txt");
        final List<String> lines = createLinesFile(testFile, 3);

        final LineIterator iterator = FileUtils.lineIterator(testFile);
        assertLines(lines, iterator);
    }

    @Test
    void testNextLineOnlyNullEncoding() throws Exception {
        final String encoding = null;

        final File testFile = new File(temporaryFolder, "LineIterator-nextOnly.txt");
        final List<String> lines = createLinesFile(testFile, encoding, 3);

        final LineIterator iterator = FileUtils.lineIterator(testFile, encoding);
        assertLines(lines, iterator);
    }

    @Test
    void testNextLineOnlyUtf8Encoding() throws Exception {
        final String encoding = UTF_8;

        final File testFile = new File(temporaryFolder, "LineIterator-nextOnly.txt");
        final List<String> lines = createLinesFile(testFile, encoding, 3);

        final LineIterator iterator = FileUtils.lineIterator(testFile, encoding);
        assertLines(lines, iterator);
    }

    @Test
    void testNextOnly() throws Exception {
        final String encoding = null;

        final File testFile = new File(temporaryFolder, "LineIterator-nextOnly.txt");
        final List<String> lines = createLinesFile(testFile, encoding, 3);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, encoding)) {
            for (int i = 0; i < lines.size(); i++) {
                final String line = iterator.next();
                assertEquals(lines.get(i), line, "next() line " + i);
            }
            assertFalse(iterator.hasNext(), "No more expected");
        }
    }

    @Test
    void testNextWithException() throws Exception {
        // A reader that always fails on readLine() so hasNext() surfaces the I/O error.
        final Reader reader = new BufferedReader(new StringReader("")) {
            @Override
            public String readLine() throws IOException {
                throw new IOException("hasNext");
            }
        };
        try (LineIterator li = new LineIterator(reader)) {
            assertThrows(IllegalStateException.class, li::hasNext);
        }
    }

    @Test
    void testOneLines() throws Exception {
        doTestFileWithSpecifiedLines(1);
    }

    @Test
    void testThreeLines() throws Exception {
        doTestFileWithSpecifiedLines(3);
    }

    @Test
    void testTwoLines() throws Exception {
        doTestFileWithSpecifiedLines(2);
    }

    @Test
    void testValidEncoding() throws Exception {
        final String encoding = UTF_8;

        final File testFile = new File(temporaryFolder, "LineIterator-validEncoding.txt");
        createLinesFile(testFile, encoding, 3);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, encoding)) {
            int count = 0;
            while (iterator.hasNext()) {
                assertNotNull(iterator.next());
                count++;
            }
            assertEquals(3, count);
        }
    }

    @Test
    void testZeroLines() throws Exception {
        doTestFileWithSpecifiedLines(0);
    }

}
