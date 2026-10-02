package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class LineIteratorTest_testNextOnly {

    private static final int LINE_COUNT = 3;
    private static final String DEFAULT_ENCODING = null;

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
     * Verifies that {@link LineIterator#next()} returns each line in file order
     * and that {@link LineIterator#hasNext()} returns false once all lines are consumed,
     * without ever calling {@code hasNext()} before {@code next()}.
     */
    @Test
    void testNextOnly() throws Exception {
        final File testFile = new File(temporaryFolder, "LineIterator-nextOnly.txt");
        final List<String> expectedLines = createLinesFile(testFile, DEFAULT_ENCODING, LINE_COUNT);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, DEFAULT_ENCODING)) {
            for (int lineIndex = 0; lineIndex < expectedLines.size(); lineIndex++) {
                final String actualLine = iterator.next();
                assertEquals(expectedLines.get(lineIndex), actualLine, "next() line " + lineIndex);
            }
            // All lines consumed — iterator should report no more elements
            assertFalse(iterator.hasNext(), "No more expected");
        }
    }
}
