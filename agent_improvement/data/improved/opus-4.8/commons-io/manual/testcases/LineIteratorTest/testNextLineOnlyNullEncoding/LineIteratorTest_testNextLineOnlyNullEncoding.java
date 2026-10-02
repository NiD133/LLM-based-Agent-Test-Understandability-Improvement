package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests that {@link LineIterator} reads back every line of a file when the file
 * was written and re-read using a {@code null} encoding (the platform default).
 */
public class LineIteratorTest_testNextLineOnlyNullEncoding {

    /** Number of lines written to the test file. */
    private static final int LINE_COUNT = 3;

    /** A {@code null} encoding tells Commons IO to use the platform default charset. */
    private static final String DEFAULT_ENCODING = null;

    @TempDir
    public File temporaryFolder;

    @Test
    void testNextLineOnlyNullEncoding() throws Exception {
        // Build the expected content and write it to a file using the default encoding.
        final List<String> expectedLines = buildLines(LINE_COUNT);
        final File testFile = new File(temporaryFolder, "LineIterator-nextOnly.txt");
        FileUtils.writeLines(testFile, DEFAULT_ENCODING, expectedLines);

        // Read the file back through a LineIterator using the same default encoding.
        final LineIterator iterator = FileUtils.lineIterator(testFile, DEFAULT_ENCODING);
        try {
            // Every written line must be returned, in order, by nextLine().
            for (int i = 0; i < expectedLines.size(); i++) {
                assertEquals(expectedLines.get(i), iterator.nextLine(), "nextLine() line " + i);
            }
            // After the last line there must be nothing left to iterate.
            assertFalse(iterator.hasNext(), "No more expected");
        } finally {
            IOUtils.closeQuietly(iterator);
        }
    }

    /**
     * Builds {@code lineCount} lines of the form {@code "LINE 0"}, {@code "LINE 1"}, ...
     *
     * @param lineCount number of lines to create.
     * @return the generated lines.
     */
    private List<String> buildLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }
}
