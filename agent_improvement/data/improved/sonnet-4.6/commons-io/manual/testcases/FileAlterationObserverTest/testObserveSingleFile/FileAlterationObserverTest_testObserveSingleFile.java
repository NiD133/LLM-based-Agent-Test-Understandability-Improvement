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

    /**
     * Call {@link FileAlterationObserver#checkAndNotify()}.
     */
    protected void checkAndNotify() {
        observer.checkAndNotify();
    }

    /**
     * Verifies that a name-based file filter causes the observer to track only the matching file
     * (testDirAFile1) while ignoring other files in the same directory (testDirAFile2, testDirAFile3),
     * across create, modify, and delete lifecycle events.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testObserveSingleFile() throws IOException {
        // Set up: create directory and define observer scoped to one specific file by name
        final File testDirA = new File(testDir, "test-dir-A");
        File testDirAFile1 = new File(testDirA, "A-file1.java");
        testDirA.mkdir();
        final FileFilter nameFilter = FileFilterUtils.nameFileFilter(testDirAFile1.getName());
        createObserver(testDirA, nameFilter);

        // Baseline: no files exist yet, observer should report nothing
        checkAndNotify();
        checkCollectionsEmpty("baseline-before-any-files-exist");
        assertFalse(testDirAFile1.exists(), "testDirAFile1 should not exist before creation");

        // --- Phase: Create ---
        // Create the watched file and two additional files the filter should ignore
        testDirAFile1 = touch(testDirAFile1);
        File testDirAFile2 = touch(new File(testDirA, "A-file2.txt"));  // filter should ignore (different name)
        File testDirAFile3 = touch(new File(testDirA, "A-file3.java")); // filter should ignore (different name)

        assertTrue(testDirAFile1.exists(), "testDirAFile1 should exist after touch");
        assertTrue(testDirAFile2.exists(), "testDirAFile2 should exist after touch");
        assertTrue(testDirAFile3.exists(), "testDirAFile3 should exist after touch");

        checkAndNotify();
        // Only testDirAFile1 matches the name filter, so exactly 1 file-create event expected
        checkCollectionSizes("after-create", 0, 0, 0, 1, 0, 0);
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1),
                "testDirAFile1 should be reported as created (it matches the name filter)");
        assertFalse(listener.getCreatedFiles().contains(testDirAFile2),
                "testDirAFile2 should NOT be reported as created (name filter excludes it)");
        assertFalse(listener.getCreatedFiles().contains(testDirAFile3),
                "testDirAFile3 should NOT be reported as created (name filter excludes it)");

        // --- Phase: Modify ---
        // Touch all three files; only the watched file should trigger a change event
        testDirAFile1 = touch(testDirAFile1);
        testDirAFile2 = touch(testDirAFile2);
        testDirAFile3 = touch(testDirAFile3);

        checkAndNotify();
        // Only testDirAFile1 matches the name filter, so exactly 1 file-change event expected
        checkCollectionSizes("after-modify", 0, 0, 0, 0, 1, 0);
        assertTrue(listener.getChangedFiles().contains(testDirAFile1),
                "testDirAFile1 should be reported as changed (it matches the name filter)");
        assertFalse(listener.getChangedFiles().contains(testDirAFile2),
                "testDirAFile2 should NOT be reported as changed (name filter excludes it)");
        assertFalse(listener.getChangedFiles().contains(testDirAFile3),
                "testDirAFile3 should NOT be reported as changed (name filter excludes it)");

        // --- Phase: Delete ---
        // Delete all three files; only the watched file should trigger a delete event
        FileUtils.deleteQuietly(testDirAFile1);
        FileUtils.deleteQuietly(testDirAFile2);
        FileUtils.deleteQuietly(testDirAFile3);

        assertFalse(testDirAFile1.exists(), "testDirAFile1 should not exist after deletion");
        assertFalse(testDirAFile2.exists(), "testDirAFile2 should not exist after deletion");
        assertFalse(testDirAFile3.exists(), "testDirAFile3 should not exist after deletion");

        checkAndNotify();
        // Only testDirAFile1 matches the name filter, so exactly 1 file-delete event expected
        checkCollectionSizes("after-delete", 0, 0, 0, 0, 0, 1);
        assertTrue(listener.getDeletedFiles().contains(testDirAFile1),
                "testDirAFile1 should be reported as deleted (it matches the name filter)");
        assertFalse(listener.getDeletedFiles().contains(testDirAFile2),
                "testDirAFile2 should NOT be reported as deleted (name filter excludes it)");
        assertFalse(listener.getDeletedFiles().contains(testDirAFile3),
                "testDirAFile3 should NOT be reported as deleted (name filter excludes it)");
    }
}
