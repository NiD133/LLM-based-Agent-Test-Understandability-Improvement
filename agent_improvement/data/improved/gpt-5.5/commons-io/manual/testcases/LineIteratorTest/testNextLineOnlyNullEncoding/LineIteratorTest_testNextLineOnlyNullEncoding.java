package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class LineIteratorTest_testNextLineOnlyNullEncoding {

    private static final String DEFAULT_ENCODING = null;
    private static final int LINE_COUNT = 3;

    @TempDir
    public File temporaryFolder;

    @Test
    void testNextLineOnlyNullEncoding() throws Exception {
        final File testFile = new File(temporaryFolder, "LineIterator-nextOnly.txt");
        final List<String> expectedLines = createLinesFile(testFile, DEFAULT_ENCODING, LINE_COUNT);

        final LineIterator iterator = FileUtils.lineIterator(testFile, DEFAULT_ENCODING);

        assertNextLineReturnsEachLine(expectedLines, iterator);
    }

    private void assertNextLineReturnsEachLine(final List<String> expectedLines, final LineIterator iterator) {
        try {
            for (int lineIndex = 0; lineIndex < expectedLines.size(); lineIndex++) {
                final String actualLine = iterator.nextLine();
                assertEquals(expectedLines.get(lineIndex), actualLine, "nextLine() line " + lineIndex);
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
        for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {
            lines.add("LINE " + lineIndex);
        }
        return lines;
    }
}
