package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link FileAlterationObserver#checkAndNotify()} reports newly created files,
 * regardless of where the new file's name sorts relative to the files already known to the
 * observer (before the first, between two existing, or after the last).
 *
 * <p>The observer keeps its children sorted by name and walks them in order, so the position of a
 * newly created file in that ordering exercises different branches of the create-detection logic.
 * The {@code A-fileN.java} names are chosen so their natural ordering matches their numeric
 * suffix.</p>
 */
public class FileAlterationObserverTest_testFileCreate extends AbstractMonitorTest {

    /**
     * Runs one observation cycle on the shared observer, firing any create/change/delete events
     * for files that appeared, changed, or vanished since the previous call.
     */
    protected void checkAndNotify() {
        observer.checkAndNotify();
    }

    /**
     * Verifies that creating files is detected as a "file created" event, including the edge cases
     * where the new file sorts before the first, between two, or after the last known file.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testFileCreate() throws IOException {
        // Baseline: nothing has changed since set-up, so no events are expected.
        checkAndNotify();
        checkCollectionsEmpty("A");

        // Create a sub-directory and bring both it and the root directory up to date so that the
        // following file creations are the only changes the observer can see.
        File testDirA = new File(testDir, "test-dir-A");
        testDirA.mkdir();
        testDir = touch(testDir);
        testDirA = touch(testDirA);

        // Within test-dir-A, only files 2 and 4 are physically created (via touch); files 1, 3 and
        // 5 are merely File handles that do not yet exist on disk. This leaves deliberate gaps in
        // the name ordering to be filled in later phases.
        final File testDirAFile1 = new File(testDirA, "A-file1.java");
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.java"));
        final File testDirAFile3 = new File(testDirA, "A-file3.java");
        final File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        final File testDirAFile5 = new File(testDirA, "A-file5.java");

        // Phase B: the observer should report exactly the two existing files (2 and 4) as created,
        // plus the one new directory; the non-existent files 1, 3 and 5 must not be reported.
        checkAndNotify();
        checkCollectionSizes("B", 1, 0, 0, 2, 0, 0);
        assertFalse(listener.getCreatedFiles().contains(testDirAFile1), "B testDirAFile1");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile2), "B testDirAFile2");
        assertFalse(listener.getCreatedFiles().contains(testDirAFile3), "B testDirAFile3");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile4), "B testDirAFile4");
        assertFalse(listener.getCreatedFiles().contains(testDirAFile5), "B testDirAFile5");
        assertFalse(testDirAFile1.exists(), "B testDirAFile1 exists");
        assertTrue(testDirAFile2.exists(), "B testDirAFile2 exists");
        assertFalse(testDirAFile3.exists(), "B testDirAFile3 exists");
        assertTrue(testDirAFile4.exists(), "B testDirAFile4 exists");
        assertFalse(testDirAFile5.exists(), "B testDirAFile5 exists");

        // Phase C: nothing changed since phase B, so no further events are expected.
        checkAndNotify();
        checkCollectionsEmpty("C");

        // Phase D: create file 1, whose name sorts BEFORE every file already known to the observer.
        // Touch the parent so its modification time changes too (1 dir change, 1 file create).
        final File createdFile1 = touch(testDirAFile1);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("D", 0, 1, 0, 1, 0, 0);
        assertTrue(createdFile1.exists(), "D testDirAFile1 exists");
        assertTrue(listener.getCreatedFiles().contains(createdFile1), "D testDirAFile1");

        // Phase E: create file 3, whose name sorts BETWEEN two existing files (2 and 4).
        final File createdFile3 = touch(testDirAFile3);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("E", 0, 1, 0, 1, 0, 0);
        assertTrue(createdFile3.exists(), "E testDirAFile3 exists");
        assertTrue(listener.getCreatedFiles().contains(createdFile3), "E testDirAFile3");

        // Phase F: create file 5, whose name sorts AFTER every file already known to the observer.
        final File createdFile5 = touch(testDirAFile5);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("F", 0, 1, 0, 1, 0, 0);
        assertTrue(createdFile5.exists(), "F testDirAFile5 exists");
        assertTrue(listener.getCreatedFiles().contains(createdFile5), "F testDirAFile5");
    }
}
