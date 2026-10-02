package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests that {@link LineIterator#next()} returns every line of a file, in order,
 * and that {@link LineIterator#hasNext()} reports {@code false} once the file is exhausted.
 */
public class LineIteratorTest_testNextOnly {

    /** Number of lines written to the test file. */
    private static final int LINE_COUNT = 3;

    /** Encoding passed to FileUtils; {@code null} means use the platform default. */
    private static final String DEFAULT_ENCODING = null;

    @TempDir
    public File temporaryFolder;

    /**
     * Builds the expected line content, e.g. {@code ["LINE 0", "LINE 1", ...]}.
     *
     * @param lineCount number of lines to create.
     * @return the generated lines.
     */
    private List<String> buildExpectedLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }

    @Test
    void testNextOnly() throws Exception {
        // Given: a file containing a known set of lines.
        final File testFile = new File(temporaryFolder, "LineIterator-nextOnly.txt");
        final List<String> expectedLines = buildExpectedLines(LINE_COUNT);
        writeLinesToFile(testFile, expectedLines);

        // When / Then: next() yields each line in order, then no more remain.
        try (LineIterator iterator = FileUtils.lineIterator(testFile, DEFAULT_ENCODING)) {
            for (int i = 0; i < expectedLines.size(); i++) {
                assertEquals(expectedLines.get(i), iterator.next(), "next() line " + i);
            }
            assertFalse(iterator.hasNext(), "No more expected");
        }
    }

    /**
     * Writes the given lines to the file using the default encoding.
     *
     * @param file  target file.
     * @param lines lines to write.
     * @throws IOException if writing fails.
     */
    private void writeLinesToFile(final File file, final List<String> lines) throws IOException {
        FileUtils.writeLines(file, DEFAULT_ENCODING, lines);
    }
}
