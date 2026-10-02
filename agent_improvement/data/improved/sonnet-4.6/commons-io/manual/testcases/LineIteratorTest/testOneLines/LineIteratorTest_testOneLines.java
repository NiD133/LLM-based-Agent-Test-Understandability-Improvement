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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class LineIteratorTest_testOneLines {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    @TempDir
    public File temporaryFolder;

    /**
     * Verifies that a {@link LineIterator} over a file with the given number of lines:
     * <ol>
     *   <li>rejects {@code remove()} with {@link UnsupportedOperationException}</li>
     *   <li>returns every expected line in order via {@code next()}</li>
     *   <li>throws {@link NoSuchElementException} on both {@code next()} and {@code nextLine()}
     *       once the file is exhausted</li>
     * </ol>
     */
    private void doTestFileWithSpecifiedLines(final int lineCount) throws IOException {
        final String encoding = UTF_8;
        final String fileName = "LineIterator-" + lineCount + "-test.txt";
        final File testFile = new File(temporaryFolder, fileName);

        // --- Setup: write known lines to a temp file ---
        final List<String> expectedLines = createLinesFile(testFile, encoding, lineCount);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, encoding)) {

            // remove() must always throw regardless of iterator state
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            // --- Exercise & verify: each line must match the expected content ---
            int lineIndex = 0;
            while (iterator.hasNext()) {
                final String actualLine = iterator.next();
                assertEquals(expectedLines.get(lineIndex), actualLine,
                        "Content mismatch at line index " + lineIndex);
                assertTrue(lineIndex < expectedLines.size(),
                        "Iterator returned more lines than expected: index=" + lineIndex
                                + ", expected size=" + expectedLines.size());
                lineIndex++;
            }
            assertEquals(expectedLines.size(), lineIndex,
                    "Iterator did not return all expected lines");

            // --- Verify post-exhaustion behaviour ---
            assertThrows(NoSuchElementException.class, iterator::next,
                    "next() must throw NoSuchElementException after all lines are consumed");
            assertThrows(NoSuchElementException.class, iterator::nextLine,
                    "nextLine() must throw NoSuchElementException after all lines are consumed");
        }
    }

    /**
     * Writes {@code lineCount} sequentially numbered lines ("LINE 0", "LINE 1", …) to
     * {@code file} using {@code encoding} and returns them as an ordered list.
     */
    private List<String> createLinesFile(final File file, final String encoding, final int lineCount)
            throws IOException {
        final List<String> lines = createStringLines(lineCount);
        FileUtils.writeLines(file, encoding, lines);
        return lines;
    }

    /**
     * Returns a list of {@code lineCount} strings of the form "LINE 0", "LINE 1", …
     */
    private List<String> createStringLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }

    @Test
    @DisplayName("Single-line file: iterator reads the one line and throws on subsequent next()/nextLine() calls")
    void testOneLines() throws Exception {
        doTestFileWithSpecifiedLines(1);
    }
}
