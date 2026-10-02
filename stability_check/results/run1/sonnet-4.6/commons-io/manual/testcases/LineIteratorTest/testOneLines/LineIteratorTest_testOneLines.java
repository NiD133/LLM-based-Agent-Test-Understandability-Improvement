package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

    private static final String ENCODING = StandardCharsets.UTF_8.name();

    @TempDir
    public File temporaryFolder;

    /**
     * Creates a file with {@code lineCount} lines, then iterates through a LineIterator
     * over that file and verifies each line matches the expected content in order.
     * Also verifies that calling next() or nextLine() after exhaustion throws NoSuchElementException.
     *
     * @param lineCount the number of lines to write into the test file.
     * @throws IOException if the test file cannot be created.
     */
    private void assertFileIterationMatchesExpectedLines(final int lineCount) throws IOException {
        final String fileName = "LineIterator-" + lineCount + "-test.txt";
        final File testFile = new File(temporaryFolder, fileName);
        final List<String> expectedLines = writeLinesToFile(testFile, ENCODING, lineCount);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, ENCODING)) {
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            int lineIndex = 0;
            while (iterator.hasNext()) {
                final String actualLine = iterator.next();
                assertEquals(expectedLines.get(lineIndex), actualLine,
                        "Line content mismatch at index " + lineIndex);
                assertTrue(lineIndex < expectedLines.size(),
                        "Iterator returned more lines than expected: index=" + lineIndex
                                + ", expected size=" + expectedLines.size());
                lineIndex++;
            }

            assertEquals(expectedLines.size(), lineIndex,
                    "Iterator did not return the expected number of lines");

            // Verify that the iterator is fully exhausted — both access paths must throw
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }

    /**
     * Writes {@code lineCount} numbered lines ("LINE 0", "LINE 1", …) to {@code file}
     * using the given encoding and returns the list of lines written.
     *
     * @param file      target file to write.
     * @param encoding  character encoding for the file.
     * @param lineCount number of lines to write.
     * @return the list of lines that were written.
     * @throws IOException if the file cannot be written.
     */
    private List<String> writeLinesToFile(final File file, final String encoding,
            final int lineCount) throws IOException {
        final List<String> lines = buildNumberedLines(lineCount);
        FileUtils.writeLines(file, encoding, lines);
        return lines;
    }

    /**
     * Builds a list of {@code lineCount} strings of the form "LINE 0", "LINE 1", etc.
     *
     * @param lineCount number of lines to generate.
     * @return the generated list.
     */
    private List<String> buildNumberedLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }

    @Test
    void testOneLines() throws Exception {
        assertFileIterationMatchesExpectedLines(1);
    }
}
