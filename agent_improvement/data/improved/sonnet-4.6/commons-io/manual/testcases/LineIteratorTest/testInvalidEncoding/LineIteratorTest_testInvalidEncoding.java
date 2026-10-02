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

public class LineIteratorTest_testInvalidEncoding {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    // A charset name that does not correspond to any real encoding
    private static final String INVALID_ENCODING = "XXXXXXXX";

    @TempDir
    public File temporaryFolder;

    private List<String> createLinesFile(final File file, final String encoding, final int lineCount) throws IOException {
        final List<String> lines = createStringLines(lineCount);
        FileUtils.writeLines(file, encoding, lines);
        return lines;
    }

    private List<String> createStringLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }

    /**
     * Verifies that requesting a line iterator with an unrecognised charset name
     * throws UnsupportedCharsetException immediately, before any lines are read.
     */
    @Test
    void testInvalidEncoding() throws Exception {
        final File testFile = new File(temporaryFolder, "LineIterator-invalidEncoding.txt");
        createLinesFile(testFile, UTF_8, 3);

        assertThrows(UnsupportedCharsetException.class,
                () -> FileUtils.lineIterator(testFile, INVALID_ENCODING));
    }
}
