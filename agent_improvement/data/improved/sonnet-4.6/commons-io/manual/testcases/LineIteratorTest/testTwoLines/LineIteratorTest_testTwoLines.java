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

public class LineIteratorTest_testTwoLines {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    @TempDir
    public File temporaryFolder;

    /**
     * Creates a list of numbered lines of the form "LINE 0", "LINE 1", ... and writes them
     * to {@code file} using the given {@code encoding}.
     */
    private List<String> createLinesFile(final File file, final String encoding, final int lineCount)
            throws IOException {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        FileUtils.writeLines(file, encoding, lines);
        return lines;
    }

    /**
     * Verifies that a {@link LineIterator} opened on a two-line file:
     * <ol>
     *   <li>throws {@link UnsupportedOperationException} on {@code remove()}</li>
     *   <li>returns each line in order and with the correct content</li>
     *   <li>throws {@link NoSuchElementException} on both {@code next()} and {@code nextLine()}
     *       once all lines have been consumed</li>
     * </ol>
     */
    @Test
    void testTwoLines() throws Exception {
        final int lineCount = 2;
        final File testFile = new File(temporaryFolder, "LineIterator-" + lineCount + "-test.txt");
        final List<String> expectedLines = createLinesFile(testFile, UTF_8, lineCount);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, UTF_8)) {
            // remove() is not supported by LineIterator
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            int lineIndex = 0;
            while (iterator.hasNext()) {
                final String actualLine = iterator.next();
                assertEquals(expectedLines.get(lineIndex), actualLine,
                        "Comparing line " + lineIndex);
                assertTrue(lineIndex < expectedLines.size(),
                        "Exceeded expected idx=" + lineIndex + " size=" + expectedLines.size());
                lineIndex++;
            }
            assertEquals(lineIndex, expectedLines.size(), "Line Count doesn't match");

            // Both next() and nextLine() must throw once the iterator is exhausted
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }
}
