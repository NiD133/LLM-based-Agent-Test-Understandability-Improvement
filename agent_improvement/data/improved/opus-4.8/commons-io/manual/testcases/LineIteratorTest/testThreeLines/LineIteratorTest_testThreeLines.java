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
 * Tests that {@link LineIterator} iterates over every line of a file and then
 * signals exhaustion, using a three-line file as the fixture.
 */
public class LineIteratorTest_testThreeLines {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    @TempDir
    public File temporaryFolder;

    @Test
    void testThreeLines() throws Exception {
        // Arrange: write a file containing exactly three lines ("LINE 0".."LINE 2").
        final int lineCount = 3;
        final List<String> expectedLines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            expectedLines.add("LINE " + i);
        }
        final File testFile = new File(temporaryFolder, "LineIterator-" + lineCount + "-test.txt");
        FileUtils.writeLines(testFile, UTF_8, expectedLines);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, UTF_8)) {
            // remove() is unsupported regardless of iterator state.
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            // Act + Assert: every line is returned in order.
            int readCount = 0;
            while (iterator.hasNext()) {
                final String actualLine = iterator.next();
                assertEquals(expectedLines.get(readCount), actualLine, "Comparing line " + readCount);
                assertTrue(readCount < expectedLines.size(),
                    "Exceeded expected idx=" + readCount + " size=" + expectedLines.size());
                readCount++;
            }
            assertEquals(readCount, expectedLines.size(), "Line Count doesn't match");

            // Once the file is fully read, both accessors report exhaustion.
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }
}
