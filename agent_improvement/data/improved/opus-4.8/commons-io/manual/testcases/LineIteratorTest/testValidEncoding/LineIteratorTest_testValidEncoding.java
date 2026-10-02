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
 * Verifies that {@link LineIterator} reads every line back from a file that was
 * written with a valid, explicitly named character encoding (UTF-8).
 */
public class LineIteratorTest_testValidEncoding {

    /** Name of the character encoding used to both write and read the test file. */
    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    /** Number of lines written to (and expected back from) the test file. */
    private static final int LINE_COUNT = 3;

    /** JUnit-managed temporary directory; cleaned up automatically after the test. */
    @TempDir
    public File temporaryFolder;

    /**
     * Builds {@code lineCount} simple, distinct text lines: {@code "LINE 0"}, {@code "LINE 1"}, ...
     *
     * @param lineCount the number of lines to generate.
     * @return the generated lines.
     */
    private List<String> buildSampleLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }

    /**
     * Writes {@code lineCount} sample lines to the given file using the given encoding.
     *
     * @param file     the target file.
     * @param encoding the character encoding to write with.
     * @param lineCount the number of lines to write.
     * @return the lines that were written.
     * @throws IOException if writing the file fails.
     */
    private List<String> writeSampleLinesFile(final File file, final String encoding, final int lineCount)
            throws IOException {
        final List<String> lines = buildSampleLines(lineCount);
        FileUtils.writeLines(file, encoding, lines);
        return lines;
    }

    @Test
    void testValidEncoding() throws Exception {
        // Given a UTF-8 encoded file containing a known number of lines.
        final File testFile = new File(temporaryFolder, "LineIterator-validEncoding.txt");
        writeSampleLinesFile(testFile, UTF_8, LINE_COUNT);

        // When iterating over the file with the same valid encoding.
        try (LineIterator iterator = FileUtils.lineIterator(testFile, UTF_8)) {
            int readLineCount = 0;
            while (iterator.hasNext()) {
                assertNotNull(iterator.next(), "Each iterated line should be non-null");
                readLineCount++;
            }

            // Then every written line is read back, with none lost or duplicated.
            assertEquals(LINE_COUNT, readLineCount, "Iterator should yield exactly the lines that were written");
        }
    }
}
