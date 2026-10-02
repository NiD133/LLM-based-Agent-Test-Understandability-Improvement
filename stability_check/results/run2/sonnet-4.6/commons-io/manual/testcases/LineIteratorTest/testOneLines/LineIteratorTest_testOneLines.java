package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

public class LineIteratorTest_testOneLines {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    @TempDir
    public File temporaryFolder;

    /**
     * Creates a file with the given number of lines and verifies that a
     * {@link LineIterator} over that file returns exactly those lines in order.
     *
     * @param lineCount the number of lines to write to the test file.
     * @throws IOException if an I/O error occurs while creating or reading the file.
     */
    private void doTestFileWithSpecifiedLines(final int lineCount) throws IOException {
        final String encoding = UTF_8;
        final String fileName = "LineIterator-" + lineCount + "-test.txt";
        final File testFile = new File(temporaryFolder, fileName);
        final List<String> expectedLines = createLinesFile(testFile, encoding, lineCount);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, encoding)) {
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            int lineIndex = 0;
            while (iterator.hasNext()) {
                final String line = iterator.next();
                assertEquals(expectedLines.get(lineIndex), line, "Comparing line " + lineIndex);
                assertTrue(lineIndex < expectedLines.size(),
                        "Exceeded expected lineIndex=" + lineIndex + " size=" + expectedLines.size());
                lineIndex++;
            }
            assertEquals(lineIndex, expectedLines.size(), "Line Count doesn't match");

            // Verify that calling next() and nextLine() after exhaustion throw NoSuchElementException
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }

    /**
     * Writes {@code lineCount} generated lines to {@code file} using the given
     * encoding and returns the list of lines written.
     *
     * @param file      target file.
     * @param encoding  the character encoding to use when writing.
     * @param lineCount number of lines to create.
     * @return the lines that were written, in order.
     * @throws IOException if an I/O error occurs.
     */
    private List<String> createLinesFile(final File file, final String encoding, final int lineCount) throws IOException {
        final List<String> lines = createStringLines(lineCount);
        FileUtils.writeLines(file, encoding, lines);
        return lines;
    }

    /**
     * Builds a list of {@code lineCount} strings of the form "LINE 0", "LINE 1", …
     *
     * @param lineCount number of lines to create.
     * @return a new list of lines.
     */
    private List<String> createStringLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }

    @Test
    void testOneLines() throws Exception {
        doTestFileWithSpecifiedLines(1);
    }
}
