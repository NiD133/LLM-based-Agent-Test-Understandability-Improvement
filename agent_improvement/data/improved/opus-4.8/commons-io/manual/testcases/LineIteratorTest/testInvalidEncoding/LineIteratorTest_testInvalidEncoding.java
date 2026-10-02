package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests that {@link FileUtils#lineIterator(File, String)} rejects an unknown
 * charset name, rather than silently opening the file with some default.
 */
public class LineIteratorTest_testInvalidEncoding {

    private static final String VALID_ENCODING = StandardCharsets.UTF_8.name();
    private static final String UNKNOWN_ENCODING = "XXXXXXXX";

    /** JUnit-managed temporary directory, cleaned up automatically after the test. */
    @TempDir
    public File temporaryFolder;

    @Test
    void testInvalidEncoding() throws Exception {
        // Given: a real file written with a valid encoding.
        final File testFile = new File(temporaryFolder, "LineIterator-invalidEncoding.txt");
        FileUtils.writeLines(testFile, VALID_ENCODING, threeSampleLines());

        // When/Then: requesting a line iterator with an unknown charset name fails fast.
        assertThrows(UnsupportedCharsetException.class,
                () -> FileUtils.lineIterator(testFile, UNKNOWN_ENCODING));
    }

    /**
     * Builds three sample lines ("LINE 0", "LINE 1", "LINE 2"). The exact content is
     * irrelevant to this test; the file only needs to exist with valid contents.
     */
    private List<String> threeSampleLines() throws IOException {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }
}
