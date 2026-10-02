package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Verifies that {@link LineIterator} correctly iterates over a file containing a single line.
 */
public class LineIteratorTest_testOneLines {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    @TempDir
    public File temporaryFolder;

    @Test
    void testOneLines() throws Exception {
        final int lineCount = 1;

        // Write a UTF-8 file whose lines are "LINE 0", "LINE 1", ...
        final List<String> expectedLines = buildExpectedLines(lineCount);
        final File testFile = new File(temporaryFolder, "LineIterator-" + lineCount + "-test.txt");
        FileUtils.writeLines(testFile, UTF_8, expectedLines);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, UTF_8)) {
            // remove() is not supported by LineIterator.
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            // Each iterated line must match the corresponding written line, in order.
            int index = 0;
            while (iterator.hasNext()) {
                final String actualLine = iterator.next();
                assertEquals(expectedLines.get(index), actualLine, "Comparing line " + index);
                assertTrue(index < expectedLines.size(),
                        "Exceeded expected idx=" + index + " size=" + expectedLines.size());
                index++;
            }
            assertEquals(index, expectedLines.size(), "Line Count doesn't match");

            // Once the file is fully consumed, both next() and nextLine() must fail.
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }

    /**
     * Builds the list of expected line contents ("LINE 0", "LINE 1", ...).
     *
     * @param lineCount number of lines to create.
     * @return a new list of line contents.
     */
    private List<String> buildExpectedLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }
}
