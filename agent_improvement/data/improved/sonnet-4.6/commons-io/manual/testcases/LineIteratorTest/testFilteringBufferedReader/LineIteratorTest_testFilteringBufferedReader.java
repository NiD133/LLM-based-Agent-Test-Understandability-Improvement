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

public class LineIteratorTest_testFilteringBufferedReader {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    @TempDir
    public File temporaryFolder;

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
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }

    /**
     * Exercises a filtering LineIterator that rejects lines whose trailing digit is congruent
     * to 1 mod 3 (i.e., lines ending in 1, 4, or 7 are skipped).
     * <p>
     * With 9 source lines "LINE 0"–"LINE 8", exactly 3 lines are filtered out (LINE 1, LINE 4,
     * LINE 7), so the iterator must yield exactly 6 lines in their original order.
     */
    private void testFiltering(final List<String> lines, final Reader reader) throws IOException {
        try (LineIterator iterator = new LineIterator(reader) {

            /**
             * Accepts a line when its last character (a digit) is NOT congruent to 1 mod 3.
             * Lines ending in 1, 4, or 7 are rejected; all others pass through.
             */
            @Override
            protected boolean isValidLine(final String line) {
                final char lastDigit = line.charAt(line.length() - 1);
                return (lastDigit - '0') % 3 != 1;
            }
        }) {
            assertThrows(UnsupportedOperationException.class, iterator::remove,
                "remove() must always throw UnsupportedOperationException");

            // sourceIdx tracks our position in the full source list, manually skipping
            // filtered indices (those where sourceIdx % 3 == 1: indices 1, 4, 7).
            int sourceIdx = 0;
            int visitedLineCount = 0;
            while (iterator.hasNext()) {
                final String line = iterator.next();
                visitedLineCount++;
                assertEquals(lines.get(sourceIdx), line,
                    "Iterator returned wrong line at source index " + sourceIdx);
                assertTrue(sourceIdx < lines.size(),
                    "sourceIdx exceeded list bounds: sourceIdx=" + sourceIdx + ", size=" + lines.size());
                sourceIdx++;
                // Advance past the next filtered index (where sourceIdx % 3 == 1)
                if (sourceIdx % 3 == 1) {
                    sourceIdx++;
                }
            }

            assertEquals(9, lines.size(),
                "Source file should contain exactly 9 lines");
            assertEquals(9, sourceIdx,
                "sourceIdx should reach 9 after all valid lines are consumed");
            assertEquals(6, visitedLineCount,
                "Iterator should yield exactly 6 lines (3 of 9 filtered out)");

            // After exhaustion, next() and nextLine() must both throw NoSuchElementException
            assertThrows(NoSuchElementException.class, iterator::next,
                "next() must throw NoSuchElementException when the iterator is exhausted");
            assertThrows(NoSuchElementException.class, iterator::nextLine,
                "nextLine() must throw NoSuchElementException when the iterator is exhausted");
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
}
