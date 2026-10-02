package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class LineIteratorTest_testZeroLines {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    @TempDir
    public File temporaryFolder;

    /**
     * Creates a test file containing {@code lineCount} lines, opens a
     * {@link LineIterator} over it, and verifies that:
     * <ul>
     *   <li>{@code remove()} always throws {@link UnsupportedOperationException}</li>
     *   <li>each line returned by the iterator matches the expected content</li>
     *   <li>the total number of lines read equals {@code lineCount}</li>
     *   <li>both {@code next()} and {@code nextLine()} throw {@link NoSuchElementException}
     *       once the iterator is exhausted</li>
     * </ul>
     */
    private void doTestFileWithSpecifiedLines(final int lineCount) throws IOException {
        final String encoding = UTF_8;
        final String fileName = "LineIterator-" + lineCount + "-test.txt";
        final File testFile = new File(temporaryFolder, fileName);
        final List<String> expectedLines = createLinesFile(testFile, encoding, lineCount);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, encoding)) {
            // remove() is unsupported regardless of file content
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            int linesRead = 0;
            while (iterator.hasNext()) {
                final String line = iterator.next();
                assertEquals(expectedLines.get(linesRead), line, "Content mismatch at line " + linesRead);
                assertTrue(linesRead < expectedLines.size(),
                        "Iterator yielded more lines than expected: linesRead=" + linesRead
                                + " expectedSize=" + expectedLines.size());
                linesRead++;
            }
            assertEquals(linesRead, expectedLines.size(), "Total lines read does not match expected line count");

            // After the iterator is exhausted, both retrieval methods must throw
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }

    /**
     * Writes {@code lineCount} sequentially-numbered lines to {@code file}
     * using the given {@code encoding}, and returns those lines for later
     * comparison against the iterator output.
     */
    private List<String> createLinesFile(final File file, final String encoding, final int lineCount)
            throws IOException {
        final List<String> lines = createStringLines(lineCount);
        FileUtils.writeLines(file, encoding, lines);
        return lines;
    }

    /**
     * Returns a list of {@code lineCount} strings of the form
     * {@code "LINE 0"}, {@code "LINE 1"}, …, {@code "LINE (lineCount-1)"}.
     */
    private List<String> createStringLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }

    /**
     * Verifies that a {@link LineIterator} over an empty file is immediately
     * exhausted: {@code hasNext()} returns {@code false} on the first call,
     * and both {@code next()} and {@code nextLine()} throw
     * {@link NoSuchElementException} without ever entering the iteration loop.
     */
    @Test
    @DisplayName("LineIterator over an empty file is immediately exhausted")
    void testZeroLines() throws Exception {
        doTestFileWithSpecifiedLines(0);
    }
}
