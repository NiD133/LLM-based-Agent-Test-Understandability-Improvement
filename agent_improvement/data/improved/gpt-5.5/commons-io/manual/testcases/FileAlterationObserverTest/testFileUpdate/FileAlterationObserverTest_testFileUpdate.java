package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testFileUpdate extends AbstractMonitorTest {

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
    void testFileUpdate() throws IOException {
        checkAndNotify();
        checkCollectionsEmpty("A");

        File testDirA = new File(testDir, "test-dir-A");
        testDirA.mkdir();
        testDir = touch(testDir);
        testDirA = touch(testDirA);

        File testDirAFile1 = touch(new File(testDirA, "A-file1.java"));
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.java"));
        File testDirAFile3 = touch(new File(testDirA, "A-file3.java"));
        final File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        File testDirAFile5 = touch(new File(testDirA, "A-file5.java"));

        checkAndNotify();
        checkCollectionSizes("B", 1, 0, 0, 5, 0, 0);
        assertCreatedAndExists(testDirAFile1, "B testDirAFile1");
        assertCreatedAndExists(testDirAFile2, "B testDirAFile2");
        assertCreatedAndExists(testDirAFile3, "B testDirAFile3");
        assertCreatedAndExists(testDirAFile4, "B testDirAFile4");
        assertCreatedAndExists(testDirAFile5, "B testDirAFile5");

        checkAndNotify();
        checkCollectionsEmpty("C");

        testDirAFile1 = touch(testDirAFile1);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("D", 0, 1, 0, 0, 1, 0);
        assertChangedFile(testDirAFile1, "D testDirAFile1");

        testDirAFile3 = touch(testDirAFile3);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("E", 0, 1, 0, 0, 1, 0);
        assertChangedFile(testDirAFile3, "E testDirAFile3");

        testDirAFile5 = touch(testDirAFile5);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("F", 0, 1, 0, 0, 1, 0);
        assertChangedFile(testDirAFile5, "F testDirAFile5");
    }

    private void assertCreatedAndExists(final File file, final String message) {
        assertTrue(listener.getCreatedFiles().contains(file), message);
        assertTrue(file.exists(), message + " exists");
    }

    private void assertChangedFile(final File file, final String message) {
        assertTrue(listener.getChangedFiles().contains(file), message);
    }
}
