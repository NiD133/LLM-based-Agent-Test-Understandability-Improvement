package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class LineIteratorTest_testZeroLines {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();
    private static final int ZERO_LINES = 0;

    @TempDir
    public File temporaryFolder;

    @Test
    void testZeroLines() throws Exception {
        doTestFileWithSpecifiedLines(ZERO_LINES);
    }

    private void doTestFileWithSpecifiedLines(final int lineCount) throws IOException {
        final String fileName = "LineIterator-" + lineCount + "-test.txt";
        final File testFile = new File(temporaryFolder, fileName);
        final List<String> expectedLines = createLinesFile(testFile, UTF_8, lineCount);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, UTF_8)) {
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            int actualLineCount = 0;
            while (iterator.hasNext()) {
                assertEquals(expectedLines.get(actualLineCount), iterator.next(), "Comparing line " + actualLineCount);
                actualLineCount++;
            }

            assertEquals(expectedLines.size(), actualLineCount, "Line Count doesn't match");
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
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
}
