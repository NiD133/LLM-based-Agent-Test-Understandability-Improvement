package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class LineIteratorTest_testMissingFile {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();
    private static final String MISSING_FILE_NAME = "dummy-missing-file.txt";

    @TempDir
    public File temporaryFolder;

    @Test
    void testMissingFile() throws Exception {
        final File missingFile = new File(temporaryFolder, MISSING_FILE_NAME);

        assertThrows(NoSuchFileException.class, () -> FileUtils.lineIterator(missingFile, UTF_8));
    }
}
