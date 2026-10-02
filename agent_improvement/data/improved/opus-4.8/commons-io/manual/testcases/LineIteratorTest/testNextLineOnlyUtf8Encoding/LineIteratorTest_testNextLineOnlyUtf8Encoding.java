package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Verifies that {@link LineIterator#nextLine()} returns every line of a
 * UTF-8 encoded file in order and then reports no further lines.
 */
public class LineIteratorTest_testNextLineOnlyUtf8Encoding {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    @TempDir
    public File temporaryFolder;

    /**
     * Builds the list of expected lines, e.g. {@code ["LINE 0", "LINE 1", ...]}.
     *
     * @param lineCount number of lines to create.
     * @return the expected lines.
     */
    private List<String> buildExpectedLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }

    /**
     * Reads the given file line-by-line through {@link LineIterator#nextLine()}
     * and asserts that the lines match {@code expectedLines}, with nothing left
     * over once they are all consumed. Closes the iterator afterwards.
     *
     * @param expectedLines the lines the iterator is expected to yield.
     * @param iterator the iterator under test.
     */
    private void assertAllLinesThenExhausted(final List<String> expectedLines, final LineIterator iterator) {
        try {
            for (int i = 0; i < expectedLines.size(); i++) {
                final String actualLine = iterator.nextLine();
                assertEquals(expectedLines.get(i), actualLine, "nextLine() line " + i);
            }
            assertFalse(iterator.hasNext(), "No more expected");
        } finally {
            IOUtils.closeQuietly(iterator);
        }
    }

    @Test
    void testNextLineOnlyUtf8Encoding() throws IOException {
        final File testFile = new File(temporaryFolder, "LineIterator-nextOnly.txt");
        final List<String> expectedLines = buildExpectedLines(3);
        FileUtils.writeLines(testFile, UTF_8, expectedLines);

        final LineIterator iterator = FileUtils.lineIterator(testFile, UTF_8);

        assertAllLinesThenExhausted(expectedLines, iterator);
    }
}
