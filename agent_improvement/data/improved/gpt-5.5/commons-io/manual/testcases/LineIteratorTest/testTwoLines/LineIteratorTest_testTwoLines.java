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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class LineIteratorTest_testTwoLines {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();
    private static final int TWO_LINES = 2;

    @TempDir
    public File temporaryFolder;

    @Test
    void testTwoLines() throws Exception {
        assertFileLineIteratorReadsExpectedNumberOfLines(TWO_LINES);
    }

    private void assertFileLineIteratorReadsExpectedNumberOfLines(final int lineCount) throws IOException {
        final String encoding = UTF_8;
        final String fileName = "LineIterator-" + lineCount + "-test.txt";
        final File testFile = new File(temporaryFolder, fileName);
        final List<String> expectedLines = createLinesFile(testFile, encoding, lineCount);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, encoding)) {
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            int lineIndex = 0;
            while (iterator.hasNext()) {
                final String actualLine = iterator.next();
                assertEquals(expectedLines.get(lineIndex), actualLine, "Comparing line " + lineIndex);
                assertTrue(lineIndex < expectedLines.size(),
                        "Exceeded expected idx=" + lineIndex + " size=" + expectedLines.size());
                lineIndex++;
            }

            assertEquals(lineIndex, expectedLines.size(), "Line Count doesn't match");
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
