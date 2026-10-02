package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
 * Tests that a {@link LineIterator} can be closed before all lines have been
 * consumed, and that it behaves correctly afterwards.
 */
public class LineIteratorTest_testCloseEarly {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    @TempDir
    public File temporaryFolder;

    /**
     * Writes a file containing {@code lineCount} lines using the given encoding.
     *
     * @param file      the target file to write.
     * @param encoding  the character encoding to use.
     * @param lineCount the number of lines to write.
     * @return the list of lines that were written.
     */
    private List<String> writeLinesFile(final File file, final String encoding, final int lineCount) throws IOException {
        final List<String> lines = buildLines(lineCount);
        FileUtils.writeLines(file, encoding, lines);
        return lines;
    }

    /**
     * Builds a list of placeholder text lines of the form {@code "LINE <index>"}.
     *
     * @param lineCount the number of lines to build.
     * @return a new list of lines.
     */
    private List<String> buildLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }

    @Test
    void testCloseEarly() throws Exception {
        final File testFile = new File(temporaryFolder, "LineIterator-closeEarly.txt");
        writeLinesFile(testFile, UTF_8, 3);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, UTF_8)) {
            // Read the first line; more lines should still be available.
            assertNotNull("Line expected", iterator.next());
            assertTrue(iterator.hasNext(), "More expected");

            // Close the iterator before reading every line.
            iterator.close();

            // After closing, no more lines are reported and reads fail.
            assertFalse(iterator.hasNext(), "No more expected");
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);

            // Closing a second time is safe and the iterator stays exhausted.
            iterator.close();
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }
}
