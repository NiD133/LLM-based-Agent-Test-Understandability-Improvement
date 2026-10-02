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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Verifies that {@link LineIterator}, obtained via {@link FileUtils#lineIterator(File, String)},
 * iterates correctly over a UTF-8 file containing a single line.
 */
public class LineIteratorTest_testOneLines {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    /** JUnit-managed temporary directory used to hold the test file. */
    @TempDir
    public File temporaryFolder;

    @Test
    void testOneLines() throws Exception {
        assertLineIteratorReadsAllLines(1);
    }

    /**
     * Writes a UTF-8 file containing {@code lineCount} lines, then asserts that a
     * {@link LineIterator} over that file returns exactly those lines in order and
     * behaves correctly at its boundaries (unsupported removal and exhaustion).
     *
     * @param lineCount the number of lines to write into the test file.
     * @throws IOException if writing or reading the test file fails.
     */
    private void assertLineIteratorReadsAllLines(final int lineCount) throws IOException {
        final File testFile = new File(temporaryFolder, "LineIterator-" + lineCount + "-test.txt");
        final List<String> expectedLines = writeLinesToFile(testFile, UTF_8, lineCount);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, UTF_8)) {
            // The iterator does not support element removal.
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            // Every line read back must match the corresponding line that was written.
            int readCount = 0;
            while (iterator.hasNext()) {
                final String actualLine = iterator.next();
                assertEquals(expectedLines.get(readCount), actualLine, "Comparing line " + readCount);
                assertTrue(readCount < expectedLines.size(),
                    "Exceeded expected idx=" + readCount + " size=" + expectedLines.size());
                readCount++;
            }
            assertEquals(readCount, expectedLines.size(), "Line Count doesn't match");

            // Once the file is fully consumed, further reads must fail.
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }

    /**
     * Creates {@code lineCount} lines of test data and writes them to the given file
     * using the specified encoding.
     *
     * @param file     the target file.
     * @param encoding the character encoding to use when writing.
     * @param lineCount the number of lines to generate.
     * @return the list of lines that were written, for later comparison.
     * @throws IOException if writing the file fails.
     */
    private List<String> writeLinesToFile(final File file, final String encoding, final int lineCount)
        throws IOException {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        FileUtils.writeLines(file, encoding, lines);
        return lines;
    }
}
