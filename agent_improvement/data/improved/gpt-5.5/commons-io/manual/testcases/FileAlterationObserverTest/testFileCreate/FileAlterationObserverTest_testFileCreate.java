package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testFileCreate extends AbstractMonitorTest {

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

        final File testDirA = touchCreatedDirectory("test-dir-A");
        File testDirAFile1 = new File(testDirA, "A-file1.java");
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.java"));
        File testDirAFile3 = new File(testDirA, "A-file3.java");
        final File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        File testDirAFile5 = new File(testDirA, "A-file5.java");

        checkAndNotify();
        checkCollectionSizes("B", 1, 0, 0, 2, 0, 0);
        assertFileNotCreated("B", testDirAFile1, "testDirAFile1");
        assertFileCreated("B", testDirAFile2, "testDirAFile2");
        assertFileNotCreated("B", testDirAFile3, "testDirAFile3");
        assertFileCreated("B", testDirAFile4, "testDirAFile4");
        assertFileNotCreated("B", testDirAFile5, "testDirAFile5");
        assertFileDoesNotExist("B", testDirAFile1, "testDirAFile1");
        assertFileExists("B", testDirAFile2, "testDirAFile2");
        assertFileDoesNotExist("B", testDirAFile3, "testDirAFile3");
        assertFileExists("B", testDirAFile4, "testDirAFile4");
        assertFileDoesNotExist("B", testDirAFile5, "testDirAFile5");

        checkAndNotify();
        checkCollectionsEmpty("C");

        // Create a file that sorts before the existing entries.
        testDirAFile1 = touch(testDirAFile1);
        touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("D", 0, 1, 0, 1, 0, 0);
        assertFileExists("D", testDirAFile1, "testDirAFile1");
        assertFileCreated("D", testDirAFile1, "testDirAFile1");

        // Create a file that sorts between the existing entries.
        testDirAFile3 = touch(testDirAFile3);
        touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("E", 0, 1, 0, 1, 0, 0);
        assertFileExists("E", testDirAFile3, "testDirAFile3");
        assertFileCreated("E", testDirAFile3, "testDirAFile3");

        // Create a file that sorts after the existing entries.
        testDirAFile5 = touch(testDirAFile5);
        touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("F", 0, 1, 0, 1, 0, 0);
        assertFileExists("F", testDirAFile5, "testDirAFile5");
        assertFileCreated("F", testDirAFile5, "testDirAFile5");
    }

    private void assertFileCreated(final String phase, final File file, final String fileName) {
        assertTrue(listener.getCreatedFiles().contains(file), phase + " " + fileName);
    }

    private void assertFileNotCreated(final String phase, final File file, final String fileName) {
        assertFalse(listener.getCreatedFiles().contains(file), phase + " " + fileName);
    }

    private void assertFileExists(final String phase, final File file, final String fileName) {
        assertTrue(file.exists(), phase + " " + fileName + " exists");
    }

    private void assertFileDoesNotExist(final String phase, final File file, final String fileName) {
        assertFalse(file.exists(), phase + " " + fileName + " exists");
    }

    private File touchCreatedDirectory(final String directoryName) throws IOException {
        final File directory = new File(testDir, directoryName);
        directory.mkdir();
        testDir = touch(testDir);
        return touch(directory);
    }
}
