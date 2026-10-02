package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests that opening a {@link LineIterator} (via {@link FileUtils#lineIterator})
 * on a file that does not exist fails with a {@link NoSuchFileException}.
 */
public class LineIteratorTest_testMissingFile {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    /** A real, empty directory provided by JUnit; nothing is created inside it. */
    @TempDir
    public File temporaryFolder;

    @Test
    void testMissingFile() {
        // Point at a file name that was never created inside the temp folder.
        final File missingFile = new File(temporaryFolder, "dummy-missing-file.txt");

        // Opening a line iterator on a non-existent file must fail fast.
        assertThrows(NoSuchFileException.class, () -> FileUtils.lineIterator(missingFile, UTF_8));
    }
}
