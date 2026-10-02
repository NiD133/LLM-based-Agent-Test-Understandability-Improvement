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

public class LineIteratorTest_testCloseEarly {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    @TempDir
    public File temporaryFolder;

    /**
     * Writes {@code lineCount} lines ("LINE 0", "LINE 1", …) to {@code file} using the given encoding.
     */
    private List<String> createLinesFile(final File file, final String encoding, final int lineCount) throws IOException {
        final List<String> lines = createStringLines(lineCount);
        FileUtils.writeLines(file, encoding, lines);
        return lines;
    }

    /**
     * Returns a list of {@code lineCount} strings in the form "LINE 0", "LINE 1", etc.
     */
    private List<String> createStringLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }

    /**
     * Verifies that closing a {@link LineIterator} before all lines have been consumed
     * immediately stops iteration. After {@code close()}:
     * <ul>
     *   <li>{@code hasNext()} returns {@code false}.</li>
     *   <li>{@code next()} and {@code nextLine()} throw {@link NoSuchElementException}.</li>
     * </ul>
     * Also verifies that calling {@code close()} a second time is safe (idempotent).
     */
    @Test
    void testCloseEarly() throws Exception {
        final File testFile = new File(temporaryFolder, "LineIterator-closeEarly.txt");
        createLinesFile(testFile, UTF_8, 3);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, UTF_8)) {
            // Advance past the first line and confirm more lines remain
            assertNotNull("Line expected", iterator.next());
            assertTrue(iterator.hasNext(), "More expected");

            // Close the iterator before exhausting the remaining lines
            iterator.close();

            // Iterator must immediately report no more elements
            assertFalse(iterator.hasNext(), "No more expected");

            // Attempting to advance after close must throw NoSuchElementException
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);

            // A second close must be safe and leave the iterator in the same exhausted state
            iterator.close();
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }
}
