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
 * Tests that {@link LineIterator} correctly reads all lines from a file
 * when a null encoding is supplied, which causes {@link FileUtils} to fall
 * back to the platform default encoding.
 */
public class LineIteratorTest_testNextLineOnlyNullEncoding {

    @TempDir
    public File temporaryFolder;

    /**
     * Reads every line via {@link LineIterator#nextLine()} and asserts that
     * each line matches the expected value.  Also asserts that the iterator
     * reports no further lines once all expected lines have been consumed.
     * The iterator is always closed in the finally block.
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
     * Writes {@code lineCount} sequentially numbered lines ("LINE 0", "LINE 1", …)
     * to {@code file} using the given encoding (may be {@code null}) and returns
     * the list of lines that were written.
     */
    private List<String> createLinesFile(final File file, final String encoding, final int lineCount) throws IOException {
        final List<String> lines = createStringLines(lineCount);
        FileUtils.writeLines(file, encoding, lines);
        return lines;
    }

    /** Builds a list of {@code lineCount} strings of the form "LINE 0", "LINE 1", … */
    private List<String> createStringLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }

    /**
     * Verifies that passing {@code null} as the encoding to
     * {@link FileUtils#lineIterator(File, String)} does not throw and that
     * {@link LineIterator#nextLine()} returns every line of the file in order.
     *
     * <p>A null encoding signals "use the platform default", so the file is also
     * written with a null encoding to guarantee the written bytes and read bytes
     * use the same charset.</p>
     */
    @Test
    void testNextLineOnlyNullEncoding() throws Exception {
        final String nullEncoding = null;
        final File testFile = new File(temporaryFolder, "LineIterator-nextOnly.txt");

        // Write the file and obtain the expected line content
        final List<String> expectedLines = createLinesFile(testFile, nullEncoding, 3);

        // Open an iterator with the same null encoding and verify all lines
        final LineIterator iterator = FileUtils.lineIterator(testFile, nullEncoding);
        assertLines(expectedLines, iterator);
    }
}
