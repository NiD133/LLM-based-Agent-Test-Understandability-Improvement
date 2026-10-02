package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testFileUpdate extends AbstractMonitorTest {

    /** Triggers the observer to scan the directory tree and fire listener events. */
    protected void checkAndNotify() {
        observer.checkAndNotify();
    }

    /**
     * Verifies that {@link FileAlterationObserver#checkAndNotify()} correctly reports file
     * modification events. After an initial scan and creation of five .java files inside a
     * subdirectory, the test sequentially updates the first, a middle, and the last file by
     * position in directory order, asserting that each scan produces exactly one changed
     * directory and one changed file with no spurious events.
     */
    @Test
    void testFileUpdate() throws IOException {
        // Initial scan on an empty directory: no events expected
        checkAndNotify();
        checkCollectionsEmpty("initial-scan");

        // Create subdirectory and five .java files inside it
        File testDirA = new File(testDir, "test-dir-A");
        testDirA.mkdir();
        testDir = touch(testDir);
        testDirA = touch(testDirA);
        File testDirAFile1 = touch(new File(testDirA, "A-file1.java"));
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.java"));
        File testDirAFile3 = touch(new File(testDirA, "A-file3.java"));
        final File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        File testDirAFile5 = touch(new File(testDirA, "A-file5.java"));

        // Scan after creation: expect 1 directory created and all 5 files created
        checkAndNotify();
        checkCollectionSizes("after-create", 1, 0, 0, 5, 0, 0);
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1), "after-create testDirAFile1");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile2), "after-create testDirAFile2");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile3), "after-create testDirAFile3");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile4), "after-create testDirAFile4");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile5), "after-create testDirAFile5");
        assertTrue(testDirAFile1.exists(), "after-create testDirAFile1 exists");
        assertTrue(testDirAFile2.exists(), "after-create testDirAFile2 exists");
        assertTrue(testDirAFile3.exists(), "after-create testDirAFile3 exists");
        assertTrue(testDirAFile4.exists(), "after-create testDirAFile4 exists");
        assertTrue(testDirAFile5.exists(), "after-create testDirAFile5 exists");

        // Scan with no changes: all event collections must be empty
        checkAndNotify();
        checkCollectionsEmpty("no-changes");

        // Update the first file (A-file1, lowest sort order): expect 1 dir changed + 1 file changed
        testDirAFile1 = touch(testDirAFile1);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("after-update-first", 0, 1, 0, 0, 1, 0);
        assertTrue(listener.getChangedFiles().contains(testDirAFile1), "after-update-first testDirAFile1");

        // Update a middle file (A-file3): expect 1 dir changed + 1 file changed
        testDirAFile3 = touch(testDirAFile3);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("after-update-middle", 0, 1, 0, 0, 1, 0);
        assertTrue(listener.getChangedFiles().contains(testDirAFile3), "after-update-middle testDirAFile3");

        // Update the last file (A-file5, highest sort order): expect 1 dir changed + 1 file changed
        testDirAFile5 = touch(testDirAFile5);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("after-update-last", 0, 1, 0, 0, 1, 0);
        assertTrue(listener.getChangedFiles().contains(testDirAFile5), "after-update-last testDirAFile5");
    }
}
