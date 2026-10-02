package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link FileAlterationObserver#checkAndNotify()} reports file
 * "change" events when previously observed files are modified.
 *
 * <p>The observer reports events relative to the snapshot taken by the previous
 * {@code checkAndNotify()} call, so each call below establishes the baseline for
 * the next one. To register as <em>changed</em>, a file's last-modified time must
 * advance, which is why {@code touch(...)} is used to update each file.</p>
 */
public class FileAlterationObserverTest_testFileUpdate extends AbstractMonitorTest {

    /**
     * Invokes {@link FileAlterationObserver#checkAndNotify()} on the observer
     * under test, capturing any create/change/delete events in the listener.
     */
    protected void checkAndNotify() {
        observer.checkAndNotify();
    }

    /**
     * Verifies that updating files in an observed directory produces "file change"
     * events, regardless of whether the updated file sorts first, in the middle, or
     * last among its siblings.
     *
     * <p>Note on {@code checkCollectionSizes}: the six integer arguments are the
     * expected event counts in this order &mdash; directories created, directories
     * changed, directories deleted, files created, files changed, files deleted.</p>
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testFileUpdate() throws IOException {
        // Baseline: nothing has happened yet, so no events are expected.
        checkAndNotify();
        checkCollectionsEmpty("A");

        // Create a sub-directory containing five ".java" files, then take a snapshot.
        File testDirA = new File(testDir, "test-dir-A");
        testDirA.mkdir();
        testDir = touch(testDir);
        testDirA = touch(testDirA);
        File testDirAFile1 = touch(new File(testDirA, "A-file1.java"));
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.java"));
        File testDirAFile3 = touch(new File(testDirA, "A-file3.java"));
        final File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        File testDirAFile5 = touch(new File(testDirA, "A-file5.java"));

        // Expect 1 directory created and 5 files created.
        checkAndNotify();
        checkCollectionSizes("B", 1, 0, 0, 5, 0, 0);
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1), "B testDirAFile1");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile2), "B testDirAFile2");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile3), "B testDirAFile3");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile4), "B testDirAFile4");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile5), "B testDirAFile5");
        assertTrue(testDirAFile1.exists(), "B testDirAFile1 exists");
        assertTrue(testDirAFile2.exists(), "B testDirAFile2 exists");
        assertTrue(testDirAFile3.exists(), "B testDirAFile3 exists");
        assertTrue(testDirAFile4.exists(), "B testDirAFile4 exists");
        assertTrue(testDirAFile5.exists(), "B testDirAFile5 exists");

        // Nothing changed since the last snapshot, so no events are expected.
        checkAndNotify();
        checkCollectionsEmpty("C");

        // Update the first file (sorts first among its siblings).
        testDirAFile1 = touch(testDirAFile1);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("D", 0, 1, 0, 0, 1, 0);
        assertTrue(listener.getChangedFiles().contains(testDirAFile1), "D testDirAFile1");

        // Update a file whose name sorts between two siblings.
        testDirAFile3 = touch(testDirAFile3);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("E", 0, 1, 0, 0, 1, 0);
        assertTrue(listener.getChangedFiles().contains(testDirAFile3), "E testDirAFile3");

        // Update the last file (sorts last among its siblings).
        testDirAFile5 = touch(testDirAFile5);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("F", 0, 1, 0, 0, 1, 0);
        assertTrue(listener.getChangedFiles().contains(testDirAFile5), "F testDirAFile5");
    }
}
