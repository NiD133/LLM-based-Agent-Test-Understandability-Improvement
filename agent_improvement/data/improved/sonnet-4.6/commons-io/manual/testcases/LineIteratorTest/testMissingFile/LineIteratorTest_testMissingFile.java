package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class LineIteratorTest_testMissingFile {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    @TempDir
    public File temporaryFolder;

    @Test
    @DisplayName("lineIterator throws NoSuchFileException when the target file does not exist")
    void testMissingFile() throws Exception {
        final File nonExistentFile = new File(temporaryFolder, "dummy-missing-file.txt");
        assertThrows(NoSuchFileException.class, () -> FileUtils.lineIterator(nonExistentFile, UTF_8));
    }
}
