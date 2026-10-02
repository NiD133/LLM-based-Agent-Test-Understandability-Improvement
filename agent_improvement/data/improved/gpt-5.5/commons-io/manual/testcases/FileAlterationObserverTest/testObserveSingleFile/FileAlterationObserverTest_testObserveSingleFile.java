package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testObserveSingleFile extends AbstractMonitorTest {

    private static final String OBSERVED_FILE_NAME = "A-file1.java";
    private static final String IGNORED_TEXT_FILE_NAME = "A-file2.txt";
    private static final String IGNORED_JAVA_FILE_NAME = "A-file3.java";

    /**
     * Call {@link FileAlterationObserver#checkAndNotify()}.
     */
    protected void checkAndNotify() {
        observer.checkAndNotify();
    }

    /**
     * Test checkAndNotify() method.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testObserveSingleFile() throws IOException {
        final File observedDirectory = new File(testDir, "test-dir-A");
        File observedFile = new File(observedDirectory, OBSERVED_FILE_NAME);
        observedDirectory.mkdir();

        final FileFilter observedFileNameFilter = FileFilterUtils.nameFileFilter(observedFile.getName());
        createObserver(observedDirectory, observedFileNameFilter);

        checkAndNotify();
        checkCollectionsEmpty("A");
        assertFalse(observedFile.exists(), "A testDirAFile1 exists");

        // Create files: only observedFile matches the observer's name filter.
        observedFile = touch(observedFile);
        File ignoredTextFile = touch(new File(observedDirectory, IGNORED_TEXT_FILE_NAME));
        File ignoredJavaFile = touch(new File(observedDirectory, IGNORED_JAVA_FILE_NAME));

        assertTrue(observedFile.exists(), "B testDirAFile1 exists");
        assertTrue(ignoredTextFile.exists(), "B testDirAFile2 exists");
        assertTrue(ignoredJavaFile.exists(), "B testDirAFile3 exists");

        checkAndNotify();
        checkCollectionSizes("C", 0, 0, 0, 1, 0, 0);
        assertTrue(listener.getCreatedFiles().contains(observedFile), "C created");
        assertFalse(listener.getCreatedFiles().contains(ignoredTextFile), "C created");
        assertFalse(listener.getCreatedFiles().contains(ignoredJavaFile), "C created");

        // Modify every file: the filter should still report only observedFile.
        observedFile = touch(observedFile);
        ignoredTextFile = touch(ignoredTextFile);
        ignoredJavaFile = touch(ignoredJavaFile);

        checkAndNotify();
        checkCollectionSizes("D", 0, 0, 0, 0, 1, 0);
        assertTrue(listener.getChangedFiles().contains(observedFile), "D changed");
        assertFalse(listener.getChangedFiles().contains(ignoredTextFile), "D changed");
        assertFalse(listener.getChangedFiles().contains(ignoredJavaFile), "D changed");

        // Delete every file: the filter should still report only observedFile.
        FileUtils.deleteQuietly(observedFile);
        FileUtils.deleteQuietly(ignoredTextFile);
        FileUtils.deleteQuietly(ignoredJavaFile);

        assertFalse(observedFile.exists(), "E testDirAFile1 exists");
        assertFalse(ignoredTextFile.exists(), "E testDirAFile2 exists");
        assertFalse(ignoredJavaFile.exists(), "E testDirAFile3 exists");

        checkAndNotify();
        checkCollectionSizes("E", 0, 0, 0, 0, 0, 1);
        assertTrue(listener.getDeletedFiles().contains(observedFile), "E deleted");
        assertFalse(listener.getDeletedFiles().contains(ignoredTextFile), "E deleted");
        assertFalse(listener.getDeletedFiles().contains(ignoredJavaFile), "E deleted");
    }
}
