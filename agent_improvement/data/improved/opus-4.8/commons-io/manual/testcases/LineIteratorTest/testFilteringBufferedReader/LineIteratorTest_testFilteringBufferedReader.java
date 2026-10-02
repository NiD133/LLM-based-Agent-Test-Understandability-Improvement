package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests that a {@link LineIterator} backed by a {@link BufferedReader} honors a custom
 * {@link LineIterator#isValidLine(String)} filter, skipping the lines the filter rejects.
 */
public class LineIteratorTest_testFilteringBufferedReader {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    /** Total number of lines written to the test file: "LINE 0" .. "LINE 8". */
    private static final int TOTAL_LINES = 9;

    /**
     * Number of lines the filter keeps. The filter drops every line whose trailing digit {@code d}
     * satisfies {@code d % 3 == 1}, i.e. lines ending in 1, 4 and 7. That removes 3 of the 9 lines,
     * leaving 6.
     */
    private static final int EXPECTED_KEPT_LINES = 6;

    @TempDir
    public File temporaryFolder;

    /**
     * Builds the line contents "LINE 0", "LINE 1", ... "LINE {lineCount - 1}".
     *
     * @param lineCount number of lines to create.
     * @return a new list of line strings.
     */
    private List<String> createStringLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }

    /**
     * Writes a test file with the given number of lines using the given encoding.
     *
     * @param file target file.
     * @param encoding the encoding to use while writing the lines.
     * @param lineCount number of lines to create.
     * @return the lines that were written.
     * @throws IOException if an I/O error occurs.
     */
    private List<String> createLinesFile(final File file, final String encoding, final int lineCount) throws IOException {
        final List<String> lines = createStringLines(lineCount);
        FileUtils.writeLines(file, encoding, lines);
        return lines;
    }

    /**
     * Iterates over {@code reader} with a {@link LineIterator} whose {@code isValidLine} filter keeps
     * only the lines whose trailing digit {@code d} satisfies {@code d % 3 != 1}, then verifies that:
     * <ul>
     *   <li>{@code remove()} is unsupported,</li>
     *   <li>exactly the un-filtered lines are returned in order, and</li>
     *   <li>iterating past the end throws {@link NoSuchElementException}.</li>
     * </ul>
     */
    private void testFiltering(final List<String> lines, final Reader reader) throws IOException {
        try (LineIterator iterator = new LineIterator(reader) {

            @Override
            protected boolean isValidLine(final String line) {
                final char lastChar = line.charAt(line.length() - 1);
                final int digit = lastChar - '0';
                return digit % 3 != 1;
            }
        }) {
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            // Walk the expected lines, skipping the indices the filter rejects (idx % 3 == 1).
            int expectedIndex = 0;
            int returnedLineCount = 0;
            while (iterator.hasNext()) {
                final String line = iterator.next();
                returnedLineCount++;
                assertEquals(lines.get(expectedIndex), line, "Comparing line " + expectedIndex);
                assertTrue(expectedIndex < lines.size(),
                        "Exceeded expected idx=" + expectedIndex + " size=" + lines.size());
                expectedIndex++;
                if (expectedIndex % 3 == 1) {
                    // This index would have been dropped by the filter, so skip over it.
                    expectedIndex++;
                }
            }

            assertEquals(TOTAL_LINES, lines.size(), "Line Count doesn't match");
            assertEquals(TOTAL_LINES, expectedIndex, "Line Count doesn't match");
            assertEquals(EXPECTED_KEPT_LINES, returnedLineCount, "Line Count doesn't match");

            // The reader is exhausted, so further reads must fail.
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }

    @Test
    void testFilteringBufferedReader() throws Exception {
        final File testFile = new File(temporaryFolder, "LineIterator-Filter-test.txt");
        final List<String> lines = createLinesFile(testFile, UTF_8, TOTAL_LINES);
        final Reader reader = new BufferedReader(Files.newBufferedReader(testFile.toPath()));
        testFiltering(lines, reader);
    }
}
