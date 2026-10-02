package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Verifies how {@link LineIterator} behaves when iterating over a file that
 * contains no lines at all.
 */
public class LineIteratorTest_testZeroLines {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    @TempDir
    public File temporaryFolder;

    @Test
    void testZeroLines() throws Exception {
        // Given: an empty file (zero lines written).
        final File emptyFile = new File(temporaryFolder, "LineIterator-0-test.txt");
        final List<String> noLines = Collections.emptyList();
        FileUtils.writeLines(emptyFile, UTF_8, noLines);

        try (LineIterator iterator = FileUtils.lineIterator(emptyFile, UTF_8)) {
            // remove() is never supported, regardless of file contents.
            assertThrows(UnsupportedOperationException.class, iterator::remove);

            // Then: the iterator yields nothing.
            assertFalse(iterator.hasNext(), "An empty file should produce no lines");

            // And: asking for the next line throws once the file is exhausted.
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThrows(NoSuchElementException.class, iterator::nextLine);
        }
    }
}
