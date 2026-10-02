package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link FileAlterationObserver#checkAndNotify()} reports file
 * deletions to the registered listener.
 *
 * <p>The collection-size checks use the column order:
 * {@code (dirsCreated, dirsChanged, dirsDeleted, filesCreated, filesChanged, filesDeleted)}.</p>
 */
public class FileAlterationObserverTest_testFileDelete extends AbstractMonitorTest {

    /**
     * Triggers an observation pass on the watched directory.
     */
    protected void checkAndNotify() {
        observer.checkAndNotify();
    }

    /**
     * Deletes files in different positions of a sorted directory listing (first,
     * middle and last) and verifies each deletion is detected on the next check.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testFileDelete() throws IOException {
        // A: nothing has happened yet, so the observer reports no events.
        checkAndNotify();
        checkCollectionsEmpty("A");

        // Create a sub-directory containing five ".java" files.
        File testDirA = new File(testDir, "test-dir-A");
        testDirA.mkdir();
        testDir = touch(testDir);
        testDirA = touch(testDirA);
        final File testDirAFile1 = touch(new File(testDirA, "A-file1.java"));
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.java"));
        final File testDirAFile3 = touch(new File(testDirA, "A-file3.java"));
        final File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        final File testDirAFile5 = touch(new File(testDirA, "A-file5.java"));
        assertTrue(testDirAFile1.exists(), "B testDirAFile1 exists");
        assertTrue(testDirAFile2.exists(), "B testDirAFile2 exists");
        assertTrue(testDirAFile3.exists(), "B testDirAFile3 exists");
        assertTrue(testDirAFile4.exists(), "B testDirAFile4 exists");
        assertTrue(testDirAFile5.exists(), "B testDirAFile5 exists");

        // B: the observer reports the new sub-directory and its five files.
        checkAndNotify();
        checkCollectionSizes("B", 1, 0, 0, 5, 0, 0);
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1), "B testDirAFile1");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile2), "B testDirAFile2");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile3), "B testDirAFile3");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile4), "B testDirAFile4");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile5), "B testDirAFile5");

        // C: a second check with no changes reports nothing.
        checkAndNotify();
        checkCollectionsEmpty("C");

        // D: delete the first file in the listing -> one dir change, one file delete.
        FileUtils.deleteQuietly(testDirAFile1);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("D", 0, 1, 0, 0, 0, 1);
        assertFalse(testDirAFile1.exists(), "D testDirAFile1 exists");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile1), "D testDirAFile1");

        // E: delete a file in the middle of the listing.
        FileUtils.deleteQuietly(testDirAFile3);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("E", 0, 1, 0, 0, 0, 1);
        assertFalse(testDirAFile3.exists(), "E testDirAFile3 exists");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile3), "E testDirAFile3");

        // F: delete the last file in the listing.
        FileUtils.deleteQuietly(testDirAFile5);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("F", 0, 1, 0, 0, 0, 1);
        assertFalse(testDirAFile5.exists(), "F testDirAFile5 exists");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile5), "F testDirAFile5");
    }
}
