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
     * Verifies that deleting files from a watched directory fires the correct delete events,
     * covering deletion of the first entry, a middle entry, and the last entry in the directory.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testFileDelete() throws IOException {
        // Phase 1: initial check — no files exist yet, all event collections should be empty
        checkAndNotify();
        checkCollectionsEmpty("initial");

        // Phase 2: create a subdirectory with five files and trigger an observer check
        File testDirA = new File(testDir, "test-dir-A");
        testDirA.mkdir();
        testDir = touch(testDir);
        testDirA = touch(testDirA);
        final File testDirAFile1 = touch(new File(testDirA, "A-file1.java"));
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.java"));
        final File testDirAFile3 = touch(new File(testDirA, "A-file3.java"));
        final File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        final File testDirAFile5 = touch(new File(testDirA, "A-file5.java"));
        assertTrue(testDirAFile1.exists(), "testDirAFile1 should exist after creation");
        assertTrue(testDirAFile2.exists(), "testDirAFile2 should exist after creation");
        assertTrue(testDirAFile3.exists(), "testDirAFile3 should exist after creation");
        assertTrue(testDirAFile4.exists(), "testDirAFile4 should exist after creation");
        assertTrue(testDirAFile5.exists(), "testDirAFile5 should exist after creation");
        checkAndNotify();
        // expect: 1 directory created (test-dir-A), 5 files created, nothing changed or deleted
        checkCollectionSizes("afterCreatingFiles", 1, 0, 0, 5, 0, 0);
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1), "testDirAFile1 should be in created files");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile2), "testDirAFile2 should be in created files");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile3), "testDirAFile3 should be in created files");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile4), "testDirAFile4 should be in created files");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile5), "testDirAFile5 should be in created files");

        // Phase 3: second check with no changes — all event collections should be empty again
        checkAndNotify();
        checkCollectionsEmpty("afterSecondCheckWithNoChanges");

        // Phase 4: delete the first file (A-file1) — expect 1 directory changed, 1 file deleted
        FileUtils.deleteQuietly(testDirAFile1);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("afterDeletingFirstFile", 0, 1, 0, 0, 0, 1);
        assertFalse(testDirAFile1.exists(), "testDirAFile1 should not exist after deletion");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile1), "testDirAFile1 should be in deleted files");

        // Phase 5: delete a middle file (A-file3) — expect 1 directory changed, 1 file deleted
        FileUtils.deleteQuietly(testDirAFile3);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("afterDeletingMiddleFile", 0, 1, 0, 0, 0, 1);
        assertFalse(testDirAFile3.exists(), "testDirAFile3 should not exist after deletion");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile3), "testDirAFile3 should be in deleted files");

        // Phase 6: delete the last file (A-file5) — expect 1 directory changed, 1 file deleted
        FileUtils.deleteQuietly(testDirAFile5);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("afterDeletingLastFile", 0, 1, 0, 0, 0, 1);
        assertFalse(testDirAFile5.exists(), "testDirAFile5 should not exist after deletion");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile5), "testDirAFile5 should be in deleted files");
    }
}
