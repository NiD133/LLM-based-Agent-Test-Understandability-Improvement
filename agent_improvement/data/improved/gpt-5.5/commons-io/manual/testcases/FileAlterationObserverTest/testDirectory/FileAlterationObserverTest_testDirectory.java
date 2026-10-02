package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testDirectory extends AbstractMonitorTest {

    /**
     * Call {@link FileAlterationObserver#checkAndNotify()}.
     */
    protected void checkAndNotify() {
        observer.checkAndNotify();
    }

    /**
     * Tests checkAndNotify() method.
     *
     * @throws Exception Thrown on test failure.
     */
    @Test
    void testDirectory() throws Exception {
        checkAndNotify();
        checkCollectionsEmpty("A");

        final File testDirA = new File(testDir, "test-dir-A");
        final File testDirB = new File(testDir, "test-dir-B");
        final File testDirC = new File(testDir, "test-dir-C");
        testDirA.mkdir();
        testDirB.mkdir();
        testDirC.mkdir();

        final File testDirAFile1 = touch(new File(testDirA, "A-file1.java"));
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.txt"));
        final File testDirAFile3 = touch(new File(testDirA, "A-file3.java"));
        File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        final File testDirBFile1 = touch(new File(testDirB, "B-file1.java"));

        checkAndNotify();
        assertInitialCreations(testDirA, testDirB, testDirC, testDirAFile1, testDirAFile2, testDirAFile3, testDirAFile4, testDirBFile1);

        checkAndNotify();
        checkCollectionsEmpty("C");

        testDirAFile4 = touch(testDirAFile4);
        FileUtils.deleteDirectory(testDirB);

        checkAndNotify();
        assertDirectoryBDeletedAndFileA4Changed(testDirB, testDirAFile4, testDirBFile1);

        FileUtils.deleteDirectory(testDir);

        checkAndNotify();
        assertRemainingObservedFilesDeleted(testDirA, testDirAFile1, testDirAFile2, testDirAFile3, testDirAFile4);

        testDir.mkdir();

        checkAndNotify();
        checkCollectionsEmpty("F");

        checkAndNotify();
        checkCollectionsEmpty("G");
    }

    private void assertDirectoryBDeletedAndFileA4Changed(final File testDirB, final File testDirAFile4, final File testDirBFile1) {
        checkCollectionSizes("D", 0, 0, 1, 0, 1, 1);
        assertTrue(listener.getDeletedDirectories().contains(testDirB), "D testDirB");
        assertTrue(listener.getChangedFiles().contains(testDirAFile4), "D testDirAFile4");
        assertTrue(listener.getDeletedFiles().contains(testDirBFile1), "D testDirBFile1");
    }

    private void assertInitialCreations(
            final File testDirA,
            final File testDirB,
            final File testDirC,
            final File testDirAFile1,
            final File testDirAFile2,
            final File testDirAFile3,
            final File testDirAFile4,
            final File testDirBFile1) {
        checkCollectionSizes("B", 3, 0, 0, 4, 0, 0);
        assertTrue(listener.getCreatedDirectories().contains(testDirA), "B testDirA");
        assertTrue(listener.getCreatedDirectories().contains(testDirB), "B testDirB");
        assertTrue(listener.getCreatedDirectories().contains(testDirC), "B testDirC");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1), "B testDirAFile1");
        assertFalse(listener.getCreatedFiles().contains(testDirAFile2), "B testDirAFile2");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile3), "B testDirAFile3");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile4), "B testDirAFile4");
        assertTrue(listener.getCreatedFiles().contains(testDirBFile1), "B testDirBFile1");
    }

    private void assertRemainingObservedFilesDeleted(
            final File testDirA,
            final File testDirAFile1,
            final File testDirAFile2,
            final File testDirAFile3,
            final File testDirAFile4) {
        checkCollectionSizes("E", 0, 0, 2, 0, 0, 3);
        assertTrue(listener.getDeletedDirectories().contains(testDirA), "E testDirA");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile1), "E testDirAFile1");
        assertFalse(listener.getDeletedFiles().contains(testDirAFile2), "E testDirAFile2");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile3), "E testDirAFile3");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile4), "E testDirAFile4");
    }
}
