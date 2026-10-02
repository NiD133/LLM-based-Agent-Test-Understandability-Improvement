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
 * Tests that {@link LineIterator} reads all lines correctly when opened via
 * {@link FileUtils#lineIterator(File)} without an explicit charset (default encoding).
 */
public class LineIteratorTest_testNextLineOnlyDefaultEncoding {

    @TempDir
    public File temporaryFolder;

    /**
     * Asserts that successive {@link LineIterator#nextLine()} calls return each expected line
     * in order, and that the iterator reports no further lines once all have been consumed.
     * The iterator is closed unconditionally in the finally block.
     */
    private void assertLines(final List<String> expectedLines, final LineIterator iterator) {
        try {
            for (int i = 0; i < expectedLines.size(); i++) {
                final String actualLine = iterator.nextLine();
                assertEquals(expectedLines.get(i), actualLine, "nextLine() line " + i);
            }
            assertFalse(iterator.hasNext(), "No more expected");
        } finally {
            IOUtils.closeQuietly(iterator);
        }
    }

    /**
     * Writes {@code lineCount} sequentially-named lines ("LINE 0", "LINE 1", …) to {@code file}
     * using the default system charset, then returns those lines for use in assertions.
     */
    private List<String> createLinesFile(final File file, final int lineCount) throws IOException {
        final List<String> lines = createStringLines(lineCount);
        FileUtils.writeLines(file, lines);
        return lines;
    }

    /**
     * Returns a list of {@code lineCount} strings of the form "LINE 0", "LINE 1", …
     */
    private List<String> createStringLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }

    /**
     * Verifies that {@link FileUtils#lineIterator(File)} (no explicit charset) opens a
     * {@link LineIterator} that returns all lines via {@link LineIterator#nextLine()} in the
     * order they were written, and signals exhaustion after the last line.
     */
    @Test
    void testNextLineOnlyDefaultEncoding() throws Exception {
        final File testFile = new File(temporaryFolder, "LineIterator-nextOnly.txt");
        final List<String> expectedLines = createLinesFile(testFile, 3);

        final LineIterator iterator = FileUtils.lineIterator(testFile);

        assertLines(expectedLines, iterator);
    }
}
