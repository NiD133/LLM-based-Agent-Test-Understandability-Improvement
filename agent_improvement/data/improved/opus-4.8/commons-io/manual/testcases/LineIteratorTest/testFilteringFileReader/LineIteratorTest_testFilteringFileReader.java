package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests that a {@link LineIterator} subclass overriding {@link LineIterator#isValidLine(String)}
 * only returns the lines that pass the filter, while transparently skipping the rest.
 */
public class LineIteratorTest_testFilteringFileReader {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    /** Number of lines written to the test file: "LINE 0" .. "LINE 8". */
    private static final int TOTAL_LINE_COUNT = 9;

    @TempDir
    public File temporaryFolder;

    /**
     * Builds the canonical line contents "LINE 0", "LINE 1", ... used throughout the test.
     *
     * @param lineCount number of lines to create.
     * @return a new mutable list of lines.
     */
    private List<String> createStringLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }

    /**
     * The filter used by the iterator under test: a line is kept when its trailing digit
     * {@code d} satisfies {@code d % 3 != 1}. Lines ending in 1, 4 or 7 are therefore dropped.
     *
     * @param line the line to test.
     * @return {@code true} if the line should be returned by the iterator.
     */
    private static boolean keepsLine(final String line) {
        final char lastChar = line.charAt(line.length() - 1);
        return (lastChar - '0') % 3 != 1;
    }

    @Test
    void testFilteringFileReader() throws Exception {
        // Write "LINE 0" .. "LINE 8" to a temp file.
        final List<String> allLines = createStringLines(TOTAL_LINE_COUNT);
        final File testFile = new File(temporaryFolder, "LineIterator-Filter-test.txt");
        FileUtils.writeLines(testFile, UTF_8, allLines);

        // The lines we expect the filtering iterator to return, in order.
        // Survivors for "LINE 0".."LINE 8" are 0, 2, 3, 5, 6, 8 (lines ending in 1/4/7 are dropped).
        final List<String> expectedKeptLines = new ArrayList<>();
        for (final String line : allLines) {
            if (keepsLine(line)) {
                expectedKeptLines.add(line);
            }
        }

        final Reader reader = Files.newBufferedReader(testFile.toPath());
        try (LineIterator iterator = new LineIterator(reader) {

            @Override
            protected boolean isValidLine(final String line) {
                return keepsLine(line);
            }
        }) {
            // remove() is unsupported regardless of iterator state.
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            // The iterator must yield exactly the kept lines, in order.
            final List<String> returnedLines = new ArrayList<>();
            while (iterator.hasNext()) {
                returnedLines.add(iterator.next());
            }

            assertEquals(TOTAL_LINE_COUNT, allLines.size(), "Wrote the wrong number of lines");
            assertEquals(expectedKeptLines, returnedLines, "Filtered lines do not match");
            assertEquals(6, returnedLines.size(), "Filter should keep 6 of 9 lines");

            // Once the input is exhausted, both accessors must report no more elements.
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }
}
