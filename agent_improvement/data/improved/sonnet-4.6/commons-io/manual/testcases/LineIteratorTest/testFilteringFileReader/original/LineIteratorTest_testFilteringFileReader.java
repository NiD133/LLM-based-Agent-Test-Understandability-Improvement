package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class LineIteratorTest_testFilteringFileReader {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    @TempDir
    public File temporaryFolder;

    /**
     * Creates a test file with a specified number of lines.
     *
     * @param file target file.
     * @param lineCount number of lines to create.
     * @throws IOException If an I/O error occurs.
     */
    private List<String> createLinesFile(final File file, final int lineCount) throws IOException {
        final List<String> lines = createStringLines(lineCount);
        FileUtils.writeLines(file, lines);
        return lines;
    }

    /**
     * Creates a test file with a specified number of lines.
     *
     * @param file target file.
     * @param encoding the encoding to use while writing the lines.
     * @param lineCount number of lines to create.
     * @throws IOException If an I/O error occurs.
     */
    private List<String> createLinesFile(final File file, final String encoding, final int lineCount) throws IOException {
        final List<String> lines = createStringLines(lineCount);
        FileUtils.writeLines(file, encoding, lines);
        return lines;
    }

    private void testFiltering(final List<String> lines, final Reader reader) throws IOException {
        try (LineIterator iterator = new LineIterator(reader) {

            @Override
            protected boolean isValidLine(final String line) {
                final char c = line.charAt(line.length() - 1);
                return (c - 48) % 3 != 1;
            }
        }) {
            assertThrows(UnsupportedOperationException.class, iterator::remove);
            int idx = 0;
            int actualLines = 0;
            while (iterator.hasNext()) {
                final String line = iterator.next();
                actualLines++;
                assertEquals(lines.get(idx), line, "Comparing line " + idx);
                assertTrue(idx < lines.size(), "Exceeded expected idx=" + idx + " size=" + lines.size());
                idx++;
                if (idx % 3 == 1) {
                    idx++;
                }
            }
            assertEquals(9, lines.size(), "Line Count doesn't match");
            assertEquals(9, idx, "Line Count doesn't match");
            assertEquals(6, actualLines, "Line Count doesn't match");
            // try calling next() after file processed
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }

    /**
     * Creates String data lines.
     *
     * @param lineCount number of lines to create.
     * @return a new lines list.
     */
    private List<String> createStringLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add("LINE " + i);
        }
        return lines;
    }

    @Test
    void testFilteringFileReader() throws Exception {
        final String encoding = UTF_8;
        final String fileName = "LineIterator-Filter-test.txt";
        final File testFile = new File(temporaryFolder, fileName);
        final List<String> lines = createLinesFile(testFile, encoding, 9);
        final Reader reader = Files.newBufferedReader(testFile.toPath());
        testFiltering(lines, reader);
    }
}
