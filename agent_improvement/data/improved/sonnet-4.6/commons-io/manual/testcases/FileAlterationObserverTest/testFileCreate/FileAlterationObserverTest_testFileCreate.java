package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Iterator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationObserver.Builder;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testFileCreate extends AbstractMonitorTest {

    private static final String PATH_STRING_FIXTURE = "/foo";

    /**
     * Call {@link FileAlterationObserver#checkAndNotify()}.
     */
    protected void checkAndNotify() {
        observer.checkAndNotify();
    }

    /**
     * Verifies that {@link FileAlterationObserver#checkAndNotify()} correctly detects newly
     * created files regardless of where their names fall in alphabetical order relative to
     * existing entries.
     *
     * <p>The test proceeds in several phases:
     * <ol>
     *   <li>Baseline scan: no changes expected.</li>
     *   <li>Create a subdirectory and two files (file2, file4); verify only the physically
     *       created files are reported.</li>
     *   <li>Idle scan: listener collections should be empty again after reset.</li>
     *   <li>Add file1 whose name sorts <em>before</em> every existing entry; verify detection.</li>
     *   <li>Add file3 whose name sorts <em>between</em> two existing entries; verify detection.</li>
     *   <li>Add file5 whose name sorts <em>after</em> every existing entry; verify detection.</li>
     * </ol>
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testFileCreate() throws IOException {

        // --- Phase 1: Baseline – no files exist yet, listener must be empty ---
        checkAndNotify();
        checkCollectionsEmpty("initial-baseline");

        // --- Phase 2: Create subdirectory and a subset of files, then scan ---
        File testDirA = new File(testDir, "test-dir-A");
        testDirA.mkdir();
        testDir = touch(testDir);
        testDirA = touch(testDirA);

        // Only file2 and file4 are physically created (touched); file1, file3, file5 are declared but not created.
        File testDirAFile1 = new File(testDirA, "A-file1.java");
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.java"));
        File testDirAFile3 = new File(testDirA, "A-file3.java");
        final File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        File testDirAFile5 = new File(testDirA, "A-file5.java");

        checkAndNotify();
        // Expect: 1 directory created (testDirA), 2 files created (file2, file4)
        checkCollectionSizes("after-initial-file-creation", 1, 0, 0, 2, 0, 0);

        // Only the files that were physically created should appear in the listener
        assertFalse(listener.getCreatedFiles().contains(testDirAFile1), "file1 was not created, must not be reported");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile2),  "file2 was created, must be reported");
        assertFalse(listener.getCreatedFiles().contains(testDirAFile3), "file3 was not created, must not be reported");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile4),  "file4 was created, must be reported");
        assertFalse(listener.getCreatedFiles().contains(testDirAFile5), "file5 was not created, must not be reported");

        // Confirm physical existence matches expectations
        assertFalse(testDirAFile1.exists(), "file1 must not exist on disk");
        assertTrue(testDirAFile2.exists(),  "file2 must exist on disk");
        assertFalse(testDirAFile3.exists(), "file3 must not exist on disk");
        assertTrue(testDirAFile4.exists(),  "file4 must exist on disk");
        assertFalse(testDirAFile5.exists(), "file5 must not exist on disk");

        // --- Phase 3: Idle scan – no further changes, listener must be empty ---
        checkAndNotify();
        checkCollectionsEmpty("idle-after-initial-creation");

        // --- Phase 4: Create file1 (name sorts BEFORE all existing entries) ---
        testDirAFile1 = touch(testDirAFile1);
        testDirA = touch(testDirA);
        checkAndNotify();
        // Expect: 1 directory changed (testDirA modified time updated), 1 file created (file1)
        checkCollectionSizes("after-creating-file-before-first-entry", 0, 1, 0, 1, 0, 0);
        assertTrue(testDirAFile1.exists(), "file1 must now exist on disk");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1), "file1 creation must be reported");

        // --- Phase 5: Create file3 (name sorts BETWEEN two existing entries) ---
        testDirAFile3 = touch(testDirAFile3);
        testDirA = touch(testDirA);
        checkAndNotify();
        // Expect: 1 directory changed (testDirA modified time updated), 1 file created (file3)
        checkCollectionSizes("after-creating-file-between-existing-entries", 0, 1, 0, 1, 0, 0);
        assertTrue(testDirAFile3.exists(), "file3 must now exist on disk");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile3), "file3 creation must be reported");

        // --- Phase 6: Create file5 (name sorts AFTER all existing entries) ---
        testDirAFile5 = touch(testDirAFile5);
        testDirA = touch(testDirA);
        checkAndNotify();
        // Expect: 1 directory changed (testDirA modified time updated), 1 file created (file5)
        checkCollectionSizes("after-creating-file-after-last-entry", 0, 1, 0, 1, 0, 0);
        assertTrue(testDirAFile5.exists(), "file5 must now exist on disk");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile5), "file5 creation must be reported");
    }
}
