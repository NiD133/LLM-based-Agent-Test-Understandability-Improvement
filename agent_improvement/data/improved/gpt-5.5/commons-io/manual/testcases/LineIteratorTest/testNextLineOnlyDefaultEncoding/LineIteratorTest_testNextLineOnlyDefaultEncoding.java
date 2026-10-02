package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class LineIteratorTest_testNextLineOnlyDefaultEncoding {

    @TempDir
    public File temporaryFolder;

    @Test
    void testNextLineOnlyDefaultEncoding() throws Exception {
        final File testFile = new File(temporaryFolder, "LineIterator-nextOnly.txt");
        final List<String> expectedLines = createLinesFile(testFile, 3);
        final LineIterator iterator = FileUtils.lineIterator(testFile);

        assertLineIteratorContainsOnly(expectedLines, iterator);
    }

    private List<String> createLinesFile(final File file, final int lineCount) throws IOException {
        final List<String> lines = createStringLines(lineCount);
        FileUtils.writeLines(file, lines);
        return lines;
    }

    private List<String> createStringLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int lineNumber = 0; lineNumber < lineCount; lineNumber++) {
            lines.add("LINE " + lineNumber);
        }
        return lines;
    }

    private void assertLineIteratorContainsOnly(final List<String> expectedLines, final LineIterator iterator) {
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
}
