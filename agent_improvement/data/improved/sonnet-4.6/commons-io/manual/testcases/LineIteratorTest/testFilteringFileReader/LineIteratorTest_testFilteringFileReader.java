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

public class LineIteratorTest_testFilteringFileReader {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    /** Total number of lines written to the test file ("LINE 0" … "LINE 8"). */
    private static final int TOTAL_LINES = 9;

    /**
     * Lines whose last digit satisfies {@code (digit % 3) == 1} are filtered
     * out by {@link #testFiltering}.  For 9 lines (digits 0–8) those are lines
     * 1, 4, and 7, leaving 6 valid lines.
     */
    private static final int EXPECTED_VALID_LINES = 6;

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

    private void testFiltering(final List<String> lines, final Reader reader) throws IOException {
        try (LineIterator iterator = new LineIterator(reader) {

            @Override
            protected boolean isValidLine(final String line) {
                final char c = line.charAt(line.length() - 1);
                // Keep lines whose trailing digit is NOT ≡ 1 (mod 3).
                // ('0' acts as the numeric base for ASCII digit characters.)
                return (c - '0') % 3 != 1;
            }
        }) {
            assertThrows(UnsupportedOperationException.class, iterator::remove);
            int idx = 0;
            int actualLines = 0;
            while (iterator.hasNext()) {
                final String line = iterator.next();
                actualLines++;
                assertEquals(lines.get(idx), line, "Comparing line " + idx);
                assertTrue(idx < lines.size(), "Exceeded expected idx=" + idx + " size=" + lines.size());
                idx++;
                // Skip the index that corresponds to the next filtered-out line
                // (the one whose trailing digit satisfies digit % 3 == 1).
                if (idx % 3 == 1) {
                    idx++;
                }
            }
            assertEquals(TOTAL_LINES, lines.size(), "Line Count doesn't match");
            assertEquals(TOTAL_LINES, idx, "Line Count doesn't match");
            assertEquals(EXPECTED_VALID_LINES, actualLines, "Line Count doesn't match");
            // try calling next() after file processed
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
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

    @Test
    void testFilteringFileReader() throws Exception {
        final String encoding = UTF_8;
        final String fileName = "LineIterator-Filter-test.txt";
        final File testFile = new File(temporaryFolder, fileName);
        final List<String> lines = createLinesFile(testFile, encoding, TOTAL_LINES);
        final Reader reader = Files.newBufferedReader(testFile.toPath());
        testFiltering(lines, reader);
    }
}
