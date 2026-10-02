package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests that {@link LineIterator} correctly reads all lines from a UTF-8 encoded file
 * when opened via {@link FileUtils#lineIterator(File, String)}.
 */
public class LineIteratorTest_testValidEncoding {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();
    private static final int LINE_COUNT = 3;

    @TempDir
    public File temporaryFolder;

    // ---------------------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------------------

    /** Builds {@code lineCount} predictable lines ("LINE 0", "LINE 1", …). */
    private List<String> createStringLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }

    /** Writes {@code lineCount} lines to {@code file} using the given encoding. */
    private List<String> createLinesFile(final File file, final String encoding, final int lineCount)
            throws IOException {
        final List<String> lines = createStringLines(lineCount);
        FileUtils.writeLines(file, encoding, lines);
        return lines;
    }

    // ---------------------------------------------------------------------------
    // Test
    // ---------------------------------------------------------------------------

    /**
     * Verifies that iterating a UTF-8 file with {@link FileUtils#lineIterator}
     * returns exactly as many non-null lines as were written.
     *
     * <p>Steps:
     * <ol>
     *   <li>Write {@value LINE_COUNT} lines to a temp file in UTF-8.</li>
     *   <li>Open a {@link LineIterator} with the same encoding.</li>
     *   <li>Assert every line returned by {@code next()} is non-null.</li>
     *   <li>Assert the total line count matches what was written.</li>
     * </ol>
     */
    @Test
    void testValidEncoding() throws Exception {
        // Arrange: create a UTF-8 encoded file with a known number of lines
        final File testFile = new File(temporaryFolder, "LineIterator-validEncoding.txt");
        createLinesFile(testFile, UTF_8, LINE_COUNT);

        // Act & Assert: each line read back must be non-null,
        // and the total count must equal the number of lines written
        int linesRead = 0;
        try (LineIterator iterator = FileUtils.lineIterator(testFile, UTF_8)) {
            while (iterator.hasNext()) {
                final String line = iterator.next();
                assertNotNull(line, "Line " + linesRead + " should not be null");
                linesRead++;
            }
        }

        assertEquals(LINE_COUNT, linesRead,
                "Iterator should return exactly " + LINE_COUNT + " lines");
    }
}
