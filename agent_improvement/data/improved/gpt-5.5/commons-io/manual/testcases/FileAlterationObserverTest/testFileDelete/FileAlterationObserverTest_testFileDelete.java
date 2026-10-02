package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testFileDelete extends AbstractMonitorTest {

    /**
     * Call {@link FileAlterationObserver#checkAndNotify()}.
     */
    protected void checkAndNotify() {
        observer.checkAndNotify();
    }

    /**
     * Test checkAndNotify() deleting.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testFileDelete() throws IOException {
        checkAndNotify();
        checkCollectionsEmpty("A");

        File testDirA = new File(testDir, "test-dir-A");
        testDirA.mkdir();
        testDir = touch(testDir);
        testDirA = touch(testDirA);

        final File testDirAFile1 = touch(new File(testDirA, "A-file1.java"));
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.java"));
        final File testDirAFile3 = touch(new File(testDirA, "A-file3.java"));
        final File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        final File testDirAFile5 = touch(new File(testDirA, "A-file5.java"));

        assertCreatedFileExists("B", "testDirAFile1", testDirAFile1);
        assertCreatedFileExists("B", "testDirAFile2", testDirAFile2);
        assertCreatedFileExists("B", "testDirAFile3", testDirAFile3);
        assertCreatedFileExists("B", "testDirAFile4", testDirAFile4);
        assertCreatedFileExists("B", "testDirAFile5", testDirAFile5);

        checkAndNotify();
        checkCollectionSizes("B", 1, 0, 0, 5, 0, 0);
        assertCreatedFileWasReported("B", "testDirAFile1", testDirAFile1);
        assertCreatedFileWasReported("B", "testDirAFile2", testDirAFile2);
        assertCreatedFileWasReported("B", "testDirAFile3", testDirAFile3);
        assertCreatedFileWasReported("B", "testDirAFile4", testDirAFile4);
        assertCreatedFileWasReported("B", "testDirAFile5", testDirAFile5);

        checkAndNotify();
        checkCollectionsEmpty("C");

        FileUtils.deleteQuietly(testDirAFile1);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("D", 0, 1, 0, 0, 0, 1);
        assertDeletedFileWasReported("D", "testDirAFile1", testDirAFile1);

        FileUtils.deleteQuietly(testDirAFile3);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("E", 0, 1, 0, 0, 0, 1);
        assertDeletedFileWasReported("E", "testDirAFile3", testDirAFile3);

        FileUtils.deleteQuietly(testDirAFile5);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("F", 0, 1, 0, 0, 0, 1);
        assertDeletedFileWasReported("F", "testDirAFile5", testDirAFile5);
    }

    private void assertCreatedFileExists(final String stage, final String fileLabel, final File file) {
        assertTrue(file.exists(), stage + " " + fileLabel + " exists");
    }

    private void assertCreatedFileWasReported(final String stage, final String fileLabel, final File file) {
        assertTrue(listener.getCreatedFiles().contains(file), stage + " " + fileLabel);
    }

    private void assertDeletedFileWasReported(final String stage, final String fileLabel, final File file) {
        assertFalse(file.exists(), stage + " " + fileLabel + " exists");
        assertTrue(listener.getDeletedFiles().contains(file), stage + " " + fileLabel);
    }
}
