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
 * Verifies that {@link LineIterator} (obtained via {@link FileUtils#lineIterator})
 * correctly iterates over a file that contains a single line.
 */
public class LineIteratorTest_testOneLines {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    @TempDir
    public File temporaryFolder;

    @Test
    void testOneLines() throws Exception {
        // Arrange: write a file containing exactly one line ("LINE 0").
        final List<String> expectedLines = new ArrayList<>();
        expectedLines.add("LINE 0");

        final File testFile = new File(temporaryFolder, "LineIterator-1-test.txt");
        FileUtils.writeLines(testFile, UTF_8, expectedLines);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, UTF_8)) {
            // remove() is never supported by LineIterator.
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            // Act + Assert: the iterator yields each expected line in order.
            int readCount = 0;
            while (iterator.hasNext()) {
                final String actualLine = iterator.next();
                assertEquals(expectedLines.get(readCount), actualLine, "Comparing line " + readCount);
                assertTrue(readCount < expectedLines.size(),
                        "Exceeded expected idx=" + readCount + " size=" + expectedLines.size());
                readCount++;
            }
            assertEquals(readCount, expectedLines.size(), "Line Count doesn't match");

            // Once the file is fully read, requesting another line fails.
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }
}
