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
 * Tests that {@link LineIterator} (obtained via {@link FileUtils#lineIterator(File, String)})
 * iterates correctly over a file containing exactly two lines.
 */
public class LineIteratorTest_testTwoLines {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    /** JUnit-managed temporary directory; test files are created inside it. */
    @TempDir
    public File temporaryFolder;

    @Test
    void testTwoLines() throws Exception {
        final int lineCount = 2;

        // Arrange: write a UTF-8 file holding "LINE 0" and "LINE 1".
        final List<String> expectedLines = buildExpectedLines(lineCount);
        final File testFile = new File(temporaryFolder, "LineIterator-" + lineCount + "-test.txt");
        FileUtils.writeLines(testFile, UTF_8, expectedLines);

        // Act & Assert: iterate over the file and verify each line in turn.
        try (LineIterator iterator = FileUtils.lineIterator(testFile, UTF_8)) {
            // remove() is unsupported on a freshly opened iterator.
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            int index = 0;
            while (iterator.hasNext()) {
                final String actualLine = iterator.next();
                assertEquals(expectedLines.get(index), actualLine, "Comparing line " + index);
                assertTrue(index < expectedLines.size(),
                        "Exceeded expected index=" + index + " size=" + expectedLines.size());
                index++;
            }
            assertEquals(index, expectedLines.size(), "Line count doesn't match");

            // Once the file is exhausted, requesting another line must fail.
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }

    /**
     * Builds the list of lines expected in the test file: "LINE 0", "LINE 1", ...
     *
     * @param lineCount number of lines to generate.
     * @return the expected lines, in order.
     */
    private List<String> buildExpectedLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }
}
