package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link FileAlterationObserver#checkAndNotify()} for directory trees.
 *
 * <p>The observer used by this test (configured in {@link AbstractMonitorTest}) only
 * reports files whose name ends in {@code .java}; {@code .txt} files are filtered out
 * and must therefore never appear in any create/change/delete event.</p>
 *
 * <p>{@link #checkCollectionSizes} takes the expected event counts in this order:
 * directories created, directories changed, directories deleted,
 * files created, files changed, files deleted.</p>
 */
public class FileAlterationObserverTest_testDirectory extends AbstractMonitorTest {

    /**
     * Runs one observer scan, firing create/change/delete events for anything that
     * changed on disk since the previous scan.
     */
    protected void checkAndNotify() {
        observer.checkAndNotify();
    }

    @Test
    void testDirectory() throws Exception {
        // Stage A: nothing has happened yet, so the first scan reports no events.
        checkAndNotify();
        checkCollectionsEmpty("A");

        // Stage B: create three directories and a handful of files, then scan.
        // The .txt file is expected to be ignored by the observer's file filter.
        final File testDirA = new File(testDir, "test-dir-A");
        final File testDirB = new File(testDir, "test-dir-B");
        final File testDirC = new File(testDir, "test-dir-C");
        testDirA.mkdir();
        testDirB.mkdir();
        testDirC.mkdir();
        final File testDirAFile1 = touch(new File(testDirA, "A-file1.java"));
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.txt")); // filtered out
        final File testDirAFile3 = touch(new File(testDirA, "A-file3.java"));
        File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        final File testDirBFile1 = touch(new File(testDirB, "B-file1.java"));

        checkAndNotify();
        // Expect 3 directory creations and 4 file creations (the .txt file is excluded).
        checkCollectionSizes("B", 3, 0, 0, 4, 0, 0);
        assertTrue(listener.getCreatedDirectories().contains(testDirA), "B testDirA");
        assertTrue(listener.getCreatedDirectories().contains(testDirB), "B testDirB");
        assertTrue(listener.getCreatedDirectories().contains(testDirC), "B testDirC");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1), "B testDirAFile1");
        assertFalse(listener.getCreatedFiles().contains(testDirAFile2), "B testDirAFile2");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile3), "B testDirAFile3");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile4), "B testDirAFile4");
        assertTrue(listener.getCreatedFiles().contains(testDirBFile1), "B testDirBFile1");

        // Stage C: nothing changed since stage B, so this scan reports no events.
        checkAndNotify();
        checkCollectionsEmpty("C");

        // Stage D: modify one file and delete directory B (and its single file), then scan.
        testDirAFile4 = touch(testDirAFile4);
        FileUtils.deleteDirectory(testDirB);

        checkAndNotify();
        // Expect 1 directory deletion, 1 file change and 1 file deletion.
        checkCollectionSizes("D", 0, 0, 1, 0, 1, 1);
        assertTrue(listener.getDeletedDirectories().contains(testDirB), "D testDirB");
        assertTrue(listener.getChangedFiles().contains(testDirAFile4), "D testDirAFile4");
        assertTrue(listener.getDeletedFiles().contains(testDirBFile1), "D testDirBFile1");

        // Stage E: delete the whole observed tree, then scan.
        FileUtils.deleteDirectory(testDir);

        checkAndNotify();
        // Expect 2 directory deletions (testDir and testDirA) and 3 file deletions
        // (all of testDirA's .java files; the .txt file was never tracked).
        checkCollectionSizes("E", 0, 0, 2, 0, 0, 3);
        assertTrue(listener.getDeletedDirectories().contains(testDirA), "E testDirA");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile1), "E testDirAFile1");
        assertFalse(listener.getDeletedFiles().contains(testDirAFile2), "E testDirAFile2");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile3), "E testDirAFile3");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile4), "E testDirAFile4");

        // Stage F: recreate the (now empty) root directory and scan; no events expected.
        testDir.mkdir();
        checkAndNotify();
        checkCollectionsEmpty("F");

        // Stage G: a final scan with no changes still reports nothing.
        checkAndNotify();
        checkCollectionsEmpty("G");
    }
}
