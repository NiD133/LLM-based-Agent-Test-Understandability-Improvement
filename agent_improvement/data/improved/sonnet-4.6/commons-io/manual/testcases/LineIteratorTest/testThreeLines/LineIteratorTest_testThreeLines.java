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

public class LineIteratorTest_testThreeLines {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();
    private static final int LINE_COUNT = 3;

    @TempDir
    public File temporaryFolder;

    @Test
    void testThreeLines() throws Exception {
        // Arrange: write a file containing exactly 3 numbered lines
        final File testFile = new File(temporaryFolder, "LineIterator-3-test.txt");
        final List<String> expectedLines = createLinesFile(testFile, UTF_8, LINE_COUNT);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, UTF_8)) {
            // remove() is always unsupported on a LineIterator
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            // Iterate and verify each line matches the written content
            int lineIndex = 0;
            while (iterator.hasNext()) {
                final String actualLine = iterator.next();
                assertEquals(expectedLines.get(lineIndex), actualLine,
                        "Line content mismatch at index " + lineIndex);
                assertTrue(lineIndex < expectedLines.size(),
                        "Iterator produced more lines than expected: index=" + lineIndex);
                lineIndex++;
            }
            assertEquals(LINE_COUNT, lineIndex, "Iterator did not produce the expected number of lines");

            // After exhaustion, both next() and nextLine() must throw NoSuchElementException
            assertThrows(NoSuchElementException.class, iterator::next,
                    "next() should throw NoSuchElementException after iterator is exhausted");
            assertThrows(NoSuchElementException.class, iterator::nextLine,
                    "nextLine() should throw NoSuchElementException after iterator is exhausted");
        }
    }

    private List<String> createLinesFile(final File file, final String encoding, final int lineCount) throws IOException {
        final List<String> lines = createLines(lineCount);
        FileUtils.writeLines(file, encoding, lines);
        return lines;
    }

    private List<String> createLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }
}
