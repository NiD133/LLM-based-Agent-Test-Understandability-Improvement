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

    private static final String UTF_8 = StandardCharsets.UTF_8.name();
    private static final int FILTERED_FILE_LINE_COUNT = 9;
    private static final int FILTERED_LINE_COUNT = 6;
    private static final int STANDARD_FILE_LINE_COUNT = 3;

    @TempDir
    public File temporaryFolder;

    private void assertIteratorExhausted(final LineIterator iterator) {
        assertFalse(iterator.hasNext(), "No more expected");
        assertThrows(NoSuchElementException.class, iterator::next);
        assertThrows(NoSuchElementException.class, iterator::nextLine);
    }

    private void assertLinesReadWithNext(final List<String> lines, final LineIterator iterator) {
        for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
            final String line = iterator.next();
            assertEquals(lines.get(lineIndex), line, "next() line " + lineIndex);
        }
        assertFalse(iterator.hasNext(), "No more expected");
    }

    private void assertLinesReadWithNextLine(final List<String> lines, final LineIterator iterator) {
        try {
            for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
                final String line = iterator.nextLine();
                assertEquals(lines.get(lineIndex), line, "nextLine() line " + lineIndex);
            }
            assertFalse(iterator.hasNext(), "No more expected");
        } finally {
            IOUtils.closeQuietly(iterator);
        }
    }

    private File fileInTemporaryFolder(final String fileName) {
        return new File(temporaryFolder, fileName);
    }

    /**
     * Creates a test file with a specified number of lines.
     *
     * @param file target file.
     * @param lineCount number of lines to create.
     * @throws IOException If an I/O error occurs.
     */
    private List<String> createLinesFile(final File file, final int lineCount) throws IOException {
        final List<String> lines = createStringLines(lineCount);
        FileUtils.writeLines(file, lines);
        return lines;
    }

    /**
     * Creates a test file with a specified number of lines.
     *
     * @param file target file.
     * @param encoding the encoding to use while writing the lines.
     * @param lineCount number of lines to create.
     * @throws IOException If an I/O error occurs.
     */
    private List<String> createLinesFile(final File file, final String encoding, final int lineCount) throws IOException {
        final List<String> lines = createStringLines(lineCount);
        FileUtils.writeLines(file, encoding, lines);
        return lines;
    }

    /**
     * Creates String data lines.
     *
     * @param lineCount number of lines to create.
     * @return a new lines list.
     */
    private List<String> createStringLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {
            lines.add("LINE " + lineIndex);
        }
        return lines;
    }

    /**
     * Utility method to create and test a file with a specified number of lines.
     *
     * @param lineCount the lines to create in the test file.
     * @throws IOException If an I/O error occurs while creating the file.
     */
    private void doTestFileWithSpecifiedLines(final int lineCount) throws IOException {
        final String encoding = UTF_8;

        final String fileName = "LineIterator-" + lineCount + "-test.txt";
        final File testFile = fileInTemporaryFolder(fileName);
        final List<String> lines = createLinesFile(testFile, encoding, lineCount);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, encoding)) {
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            int lineIndex = 0;
            while (iterator.hasNext()) {
                final String line = iterator.next();
                assertEquals(lines.get(lineIndex), line, "Comparing line " + lineIndex);
                assertTrue(lineIndex < lines.size(), "Exceeded expected idx=" + lineIndex + " size=" + lines.size());
                lineIndex++;
            }
            assertEquals(lineIndex, lines.size(), "Line Count doesn't match");

            assertIteratorExhausted(iterator);
        }
    }

    @Test
    void testCloseEarly() throws Exception {
        final String encoding = UTF_8;

        final File testFile = fileInTemporaryFolder("LineIterator-closeEarly.txt");
        createLinesFile(testFile, encoding, STANDARD_FILE_LINE_COUNT);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, encoding)) {
            assertNotNull("Line expected", iterator.next());
            assertTrue(iterator.hasNext(), "More expected");

            iterator.close();
            assertIteratorExhausted(iterator);

            iterator.close();
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }

    @Test
    void testConstructor() {
        assertThrows(NullPointerException.class, () -> new LineIterator(null));
    }

    private void testFiltering(final List<String> lines, final Reader reader) throws IOException {
        try (LineIterator iterator = new LineIterator(reader) {
            @Override
            protected boolean isValidLine(final String line) {
                final char c = line.charAt(line.length() - 1);
                return (c - 48) % 3 != 1;
            }
        }) {
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            int expectedLineIndex = 0;
            int actualLines = 0;
            while (iterator.hasNext()) {
                final String line = iterator.next();
                actualLines++;
                assertEquals(lines.get(expectedLineIndex), line, "Comparing line " + expectedLineIndex);
                assertTrue(expectedLineIndex < lines.size(), "Exceeded expected idx=" + expectedLineIndex + " size=" + lines.size());
                expectedLineIndex++;
                if (expectedLineIndex % 3 == 1) {
                    expectedLineIndex++;
                }
            }
            assertEquals(FILTERED_FILE_LINE_COUNT, lines.size(), "Line Count doesn't match");
            assertEquals(FILTERED_FILE_LINE_COUNT, expectedLineIndex, "Line Count doesn't match");
            assertEquals(FILTERED_LINE_COUNT, actualLines, "Line Count doesn't match");

            assertIteratorExhausted(iterator);
        }
    }

    @Test
    void testFilteringBufferedReader() throws Exception {
        final String encoding = UTF_8;

        final String fileName = "LineIterator-Filter-test.txt";
        final File testFile = fileInTemporaryFolder(fileName);
        final List<String> lines = createLinesFile(testFile, encoding, FILTERED_FILE_LINE_COUNT);

        final Reader reader = new BufferedReader(Files.newBufferedReader(testFile.toPath()));
        testFiltering(lines, reader);
    }

    @Test
    void testFilteringFileReader() throws Exception {
        final String encoding = UTF_8;

        final String fileName = "LineIterator-Filter-test.txt";
        final File testFile = fileInTemporaryFolder(fileName);
        final List<String> lines = createLinesFile(testFile, encoding, FILTERED_FILE_LINE_COUNT);

        final Reader reader = Files.newBufferedReader(testFile.toPath());
        testFiltering(lines, reader);
    }

    @Test
    void testInvalidEncoding() throws Exception {
        final String encoding = "XXXXXXXX";

        final File testFile = fileInTemporaryFolder("LineIterator-invalidEncoding.txt");
        createLinesFile(testFile, UTF_8, STANDARD_FILE_LINE_COUNT);

        assertThrows(UnsupportedCharsetException.class, () -> FileUtils.lineIterator(testFile, encoding));
    }

    @Test
    void testMissingFile() throws Exception {
        final File testFile = fileInTemporaryFolder("dummy-missing-file.txt");
        assertThrows(NoSuchFileException.class, () -> FileUtils.lineIterator(testFile, UTF_8));
    }

    @Test
    void testNextLineOnlyDefaultEncoding() throws Exception {
        final File testFile = fileInTemporaryFolder("LineIterator-nextOnly.txt");
        final List<String> lines = createLinesFile(testFile, STANDARD_FILE_LINE_COUNT);

        final LineIterator iterator = FileUtils.lineIterator(testFile);
        assertLinesReadWithNextLine(lines, iterator);
    }

    @Test
    void testNextLineOnlyNullEncoding() throws Exception {
        final String encoding = null;

        final File testFile = fileInTemporaryFolder("LineIterator-nextOnly.txt");
        final List<String> lines = createLinesFile(testFile, encoding, STANDARD_FILE_LINE_COUNT);

        final LineIterator iterator = FileUtils.lineIterator(testFile, encoding);
        assertLinesReadWithNextLine(lines, iterator);
    }

    @Test
    void testNextLineOnlyUtf8Encoding() throws Exception {
        final String encoding = UTF_8;

        final File testFile = fileInTemporaryFolder("LineIterator-nextOnly.txt");
        final List<String> lines = createLinesFile(testFile, encoding, STANDARD_FILE_LINE_COUNT);

        final LineIterator iterator = FileUtils.lineIterator(testFile, encoding);
        assertLinesReadWithNextLine(lines, iterator);
    }

    @Test
    void testNextOnly() throws Exception {
        final String encoding = null;

        final File testFile = fileInTemporaryFolder("LineIterator-nextOnly.txt");
        final List<String> lines = createLinesFile(testFile, encoding, STANDARD_FILE_LINE_COUNT);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, encoding)) {
            assertLinesReadWithNext(lines, iterator);
        }
    }

    @Test
    void testNextWithException() throws Exception {
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

        final File testFile = fileInTemporaryFolder("LineIterator-validEncoding.txt");
        createLinesFile(testFile, encoding, STANDARD_FILE_LINE_COUNT);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, encoding)) {
            int count = 0;
            while (iterator.hasNext()) {
                assertNotNull(iterator.next());
                count++;
            }
            assertEquals(STANDARD_FILE_LINE_COUNT, count);
        }
    }

    @Test
    void testZeroLines() throws Exception {
        doTestFileWithSpecifiedLines(0);
    }

}
