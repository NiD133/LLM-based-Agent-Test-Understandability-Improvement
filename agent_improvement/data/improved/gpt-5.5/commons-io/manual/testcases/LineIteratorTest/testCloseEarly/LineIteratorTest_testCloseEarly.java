package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

public class LineIteratorTest_testCloseEarly {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

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

    private void assertNextMethodsThrowNoSuchElement(final LineIterator iterator) {
        assertThrows(NoSuchElementException.class, iterator::next);
        assertThrows(NoSuchElementException.class, iterator::nextLine);
    }

    @Test
    void testCloseEarly() throws Exception {
        final String encoding = UTF_8;
        final File testFile = new File(temporaryFolder, "LineIterator-closeEarly.txt");
        createLinesFile(testFile, encoding, 3);

        try (LineIterator iterator = FileUtils.lineIterator(testFile, encoding)) {
            assertNotNull("Line expected", iterator.next());
            assertTrue(iterator.hasNext(), "More expected");

            iterator.close();
            assertFalse(iterator.hasNext(), "No more expected");
            assertNextMethodsThrowNoSuchElement(iterator);

            iterator.close();
            assertNextMethodsThrowNoSuchElement(iterator);
        }
    }
}
