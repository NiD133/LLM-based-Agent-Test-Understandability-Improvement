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
 * Tests that {@link LineIterator#nextLine()} returns every line of a file,
 * in order, when the iterator is created via {@link FileUtils#lineIterator(File)}
 * (which uses the platform's default encoding).
 */
public class LineIteratorTest_testNextLineOnlyDefaultEncoding {

    @TempDir
    public File temporaryFolder;

    @Test
    void testNextLineOnlyDefaultEncoding() throws Exception {
        // Given: a file written with three known lines using the default encoding.
        final File testFile = new File(temporaryFolder, "LineIterator-nextOnly.txt");
        final List<String> expectedLines = writeLinesToFile(testFile, 3);

        // When: iterating over the file with a LineIterator.
        final LineIterator iterator = FileUtils.lineIterator(testFile);

        // Then: nextLine() yields each expected line in order, and nothing more.
        try {
            for (int i = 0; i < expectedLines.size(); i++) {
                assertEquals(expectedLines.get(i), iterator.nextLine(), "nextLine() line " + i);
            }
            assertFalse(iterator.hasNext(), "No more lines expected");
        } finally {
            IOUtils.closeQuietly(iterator);
        }
    }

    /**
     * Writes {@code lineCount} lines ("LINE 0", "LINE 1", ...) to the given file
     * using the default encoding and returns them.
     */
    private List<String> writeLinesToFile(final File file, final int lineCount) throws IOException {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        FileUtils.writeLines(file, lines);
        return lines;
    }
}
