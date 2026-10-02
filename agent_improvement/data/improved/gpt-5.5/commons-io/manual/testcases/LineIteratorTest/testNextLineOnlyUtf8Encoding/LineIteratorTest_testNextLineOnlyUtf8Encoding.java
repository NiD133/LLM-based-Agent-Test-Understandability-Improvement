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

public class LineIteratorTest_testNextLineOnlyUtf8Encoding {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();
    private static final String TEST_FILE_NAME = "LineIterator-nextOnly.txt";
    private static final int LINE_COUNT = 3;

    @TempDir
    public File temporaryFolder;

    private void assertLines(final List<String> lines, final LineIterator iterator) {
        try {
            for (int i = 0; i < lines.size(); i++) {
                final String line = iterator.nextLine();
                assertEquals(lines.get(i), line, "nextLine() line " + i);
            }
            assertFalse(iterator.hasNext(), "No more expected");
        } finally {
            IOUtils.closeQuietly(iterator);
        }
    }

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

    @Test
    void testNextLineOnlyUtf8Encoding() throws Exception {
        final String encoding = UTF_8;
        final File testFile = new File(temporaryFolder, TEST_FILE_NAME);
        final List<String> lines = createLinesFile(testFile, encoding, LINE_COUNT);
        final LineIterator iterator = FileUtils.lineIterator(testFile, encoding);
        assertLines(lines, iterator);
    }
}
