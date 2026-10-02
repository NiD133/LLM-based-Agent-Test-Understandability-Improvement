package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class LineIteratorTest_testValidEncoding {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();
    private static final int EXPECTED_LINE_COUNT = 3;

    @TempDir
    public File temporaryFolder;

    @Test
    void testValidEncoding() throws Exception {
        final String encoding = UTF_8;
        final File testFile = new File(temporaryFolder, "LineIterator-validEncoding.txt");

        createLinesFile(testFile, encoding, EXPECTED_LINE_COUNT);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, encoding)) {
            int actualLineCount = 0;
            while (iterator.hasNext()) {
                assertNotNull(iterator.next());
                actualLineCount++;
            }
            assertEquals(EXPECTED_LINE_COUNT, actualLineCount);
        }
    }

    private List<String> createLinesFile(final File file, final String encoding, final int lineCount) throws IOException {
        final List<String> lines = createStringLines(lineCount);
        FileUtils.writeLines(file, encoding, lines);
        return lines;
    }

    private List<String> createStringLines(final int lineCount) {
        final List<String> lines = new ArrayList<>();
        for (int lineNumber = 0; lineNumber < lineCount; lineNumber++) {
            lines.add("LINE " + lineNumber);
        }
        return lines;
    }
}
