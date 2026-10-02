package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testDirectory extends AbstractMonitorTest {

    /**
     * Delegates to {@link FileAlterationObserver#checkAndNotify()} on the shared observer.
     */
    protected void checkAndNotify() {
        observer.checkAndNotify();
    }

    /**
     * Tests that {@link FileAlterationObserver#checkAndNotify()} correctly detects
     * directory/file creation, modification, and deletion across multiple observation cycles.
     *
     * <p>The test proceeds through the following states:</p>
     * <ol>
     *   <li>Initial empty state — no events expected.</li>
     *   <li>Three subdirectories and five files are created (one .txt file is excluded by the
     *       ".java" filter) — creation events for 3 dirs and 4 .java files expected.</li>
     *   <li>No further changes — no events expected.</li>
     *   <li>One file is modified and one subdirectory (with its file) is deleted — change/delete
     *       events expected.</li>
     *   <li>Root test directory is deleted entirely — remaining dir/file deletes expected.</li>
     *   <li>Root directory is recreated (empty) — no events expected.</li>
     *   <li>Final steady state — no events expected.</li>
     * </ol>
     *
     * @throws Exception Thrown on test failure.
     */
    @Test
    void testDirectory() throws Exception {
        // Phase 1: initial observation — file system is empty, no events expected
        checkAndNotify();
        checkCollectionsEmpty("initial_empty_state");

        // Set up subdirectories and files under the observed root
        final File testDirA = new File(testDir, "test-dir-A");
        final File testDirB = new File(testDir, "test-dir-B");
        final File testDirC = new File(testDir, "test-dir-C");
        testDirA.mkdir();
        testDirB.mkdir();
        testDirC.mkdir();
        final File testDirAFile1 = touch(new File(testDirA, "A-file1.java"));
        // .txt extension is excluded by the observer's file filter — should never appear in events
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.txt"));
        final File testDirAFile3 = touch(new File(testDirA, "A-file3.java"));
        File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        final File testDirBFile1 = touch(new File(testDirB, "B-file1.java"));

        // Phase 2: after creating 3 dirs and 5 files — expect 3 dir creates and 4 file creates
        // (testDirAFile2 is filtered out because it has a .txt extension)
        checkAndNotify();
        checkCollectionSizes("after_dirs_and_files_created", 3, 0, 0, 4, 0, 0);
        assertTrue(listener.getCreatedDirectories().contains(testDirA), "testDirA should be reported as created");
        assertTrue(listener.getCreatedDirectories().contains(testDirB), "testDirB should be reported as created");
        assertTrue(listener.getCreatedDirectories().contains(testDirC), "testDirC should be reported as created");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1), "testDirAFile1 (.java) should be reported as created");
        assertFalse(listener.getCreatedFiles().contains(testDirAFile2), "testDirAFile2 (.txt) must NOT be reported (filtered out)");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile3), "testDirAFile3 (.java) should be reported as created");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile4), "testDirAFile4 (.java) should be reported as created");
        assertTrue(listener.getCreatedFiles().contains(testDirBFile1), "testDirBFile1 (.java) should be reported as created");

        // Phase 3: no changes since last check — no events expected
        checkAndNotify();
        checkCollectionsEmpty("no_changes_since_last_check");

        // Modify testDirAFile4 and delete entire testDirB (including testDirBFile1)
        testDirAFile4 = touch(testDirAFile4);
        FileUtils.deleteDirectory(testDirB);

        // Phase 4: after modifying one file and deleting one directory —
        // expect 1 dir delete, 1 file change, 1 file delete
        checkAndNotify();
        checkCollectionSizes("after_file_modified_and_dir_deleted", 0, 0, 1, 0, 1, 1);
        assertTrue(listener.getDeletedDirectories().contains(testDirB), "testDirB should be reported as deleted");
        assertTrue(listener.getChangedFiles().contains(testDirAFile4), "testDirAFile4 should be reported as changed");
        assertTrue(listener.getDeletedFiles().contains(testDirBFile1), "testDirBFile1 should be reported as deleted with its directory");

        // Delete the entire root test directory
        FileUtils.deleteDirectory(testDir);

        // Phase 5: after deleting the root directory — expect 2 dir deletes (testDirA + testDirC)
        // and 3 file deletes (testDirAFile1, testDirAFile3, testDirAFile4); testDirAFile2 (.txt) is excluded
        checkAndNotify();
        checkCollectionSizes("after_root_directory_deleted", 0, 0, 2, 0, 0, 3);
        assertTrue(listener.getDeletedDirectories().contains(testDirA), "testDirA should be reported as deleted");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile1), "testDirAFile1 should be reported as deleted");
        assertFalse(listener.getDeletedFiles().contains(testDirAFile2), "testDirAFile2 (.txt) must NOT be reported (filtered out)");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile3), "testDirAFile3 should be reported as deleted");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile4), "testDirAFile4 should be reported as deleted");

        // Recreate the (now empty) root directory
        testDir.mkdir();

        // Phase 6: after recreating empty root directory — no events expected (directory is empty)
        checkAndNotify();
        checkCollectionsEmpty("after_root_directory_recreated_empty");

        // Phase 7: final steady state — still no events
        checkAndNotify();
        checkCollectionsEmpty("final_steady_state");
    }
}
