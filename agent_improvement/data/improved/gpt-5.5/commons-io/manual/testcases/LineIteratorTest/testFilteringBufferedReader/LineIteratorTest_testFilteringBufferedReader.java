package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class LineIteratorTest_testFilteringBufferedReader {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();
    private static final int TOTAL_LINE_COUNT = 9;
    private static final int EXPECTED_FILTERED_LINE_COUNT = 6;

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

    private void assertFilteredLines(final List<String> lines, final Reader reader) throws IOException {
        try (LineIterator iterator = new LineIterator(reader) {

            @Override
            protected boolean isValidLine(final String line) {
                final char c = line.charAt(line.length() - 1);
                return (c - 48) % 3 != 1;
            }
        }) {
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            int expectedLineIndex = 0;
            int actualLineCount = 0;
            while (iterator.hasNext()) {
                final String actualLine = iterator.next();
                actualLineCount++;

                assertEquals(lines.get(expectedLineIndex), actualLine, "Comparing line " + expectedLineIndex);
                assertTrue(expectedLineIndex < lines.size(), "Exceeded expected idx=" + expectedLineIndex + " size=" + lines.size());

                expectedLineIndex++;
                if (expectedLineIndex % 3 == 1) {
                    expectedLineIndex++;
                }
            }

            assertEquals(TOTAL_LINE_COUNT, lines.size(), "Line Count doesn't match");
            assertEquals(TOTAL_LINE_COUNT, expectedLineIndex, "Line Count doesn't match");
            assertEquals(EXPECTED_FILTERED_LINE_COUNT, actualLineCount, "Line Count doesn't match");
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }

    @Test
    void testFilteringBufferedReader() throws Exception {
        final String encoding = UTF_8;
        final String fileName = "LineIterator-Filter-test.txt";
        final File testFile = new File(temporaryFolder, fileName);
        final List<String> lines = createLinesFile(testFile, encoding, TOTAL_LINE_COUNT);
        final Reader reader = new BufferedReader(Files.newBufferedReader(testFile.toPath()));

        assertFilteredLines(lines, reader);
    }
}
