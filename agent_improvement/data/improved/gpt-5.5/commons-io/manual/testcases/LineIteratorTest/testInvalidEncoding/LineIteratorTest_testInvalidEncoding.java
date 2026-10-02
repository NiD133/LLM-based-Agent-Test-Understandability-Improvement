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

    private static final String INVALID_ENCODING = "XXXXXXXX";
    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    @TempDir
    public File temporaryFolder;

    @Test
    void testInvalidEncoding() throws Exception {
        final File testFile = new File(temporaryFolder, "LineIterator-invalidEncoding.txt");
        createUtf8FileWithThreeLines(testFile);

        assertThrows(
                UnsupportedCharsetException.class,
                () -> FileUtils.lineIterator(testFile, INVALID_ENCODING));
    }

    private void createUtf8FileWithThreeLines(final File file) throws IOException {
        FileUtils.writeLines(file, UTF_8, createStringLines(3));
    }

    private List<String> createStringLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }
}
