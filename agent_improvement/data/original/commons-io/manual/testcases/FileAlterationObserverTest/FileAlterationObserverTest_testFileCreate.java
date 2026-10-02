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
     * Test checkAndNotify() creating
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testFileCreate() throws IOException {
        checkAndNotify();
        checkCollectionsEmpty("A");
        File testDirA = new File(testDir, "test-dir-A");
        testDirA.mkdir();
        testDir = touch(testDir);
        testDirA = touch(testDirA);
        File testDirAFile1 = new File(testDirA, "A-file1.java");
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.java"));
        File testDirAFile3 = new File(testDirA, "A-file3.java");
        final File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        File testDirAFile5 = new File(testDirA, "A-file5.java");
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
        checkAndNotify();
        checkCollectionsEmpty("C");
        // Create file with name < first entry
        testDirAFile1 = touch(testDirAFile1);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("D", 0, 1, 0, 1, 0, 0);
        assertTrue(testDirAFile1.exists(), "D testDirAFile1 exists");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1), "D testDirAFile1");
        // Create file with name between 2 entries
        testDirAFile3 = touch(testDirAFile3);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("E", 0, 1, 0, 1, 0, 0);
        assertTrue(testDirAFile3.exists(), "E testDirAFile3 exists");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile3), "E testDirAFile3");
        // Create file with name > last entry
        testDirAFile5 = touch(testDirAFile5);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("F", 0, 1, 0, 1, 0, 0);
        assertTrue(testDirAFile5.exists(), "F testDirAFile5 exists");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile5), "F testDirAFile5");
    }
}
