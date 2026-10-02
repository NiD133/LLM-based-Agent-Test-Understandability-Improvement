package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link FileAlterationObserver#checkAndNotify()} only reports
 * create / change / delete events for the single file accepted by its
 * {@link FileFilter}, ignoring every other file in the observed directory.
 */
public class FileAlterationObserverTest_testObserveSingleFile extends AbstractMonitorTest {

    /**
     * The observer is configured with a name filter that matches exactly one
     * file ({@code observedFile}). The two sibling files created alongside it
     * must therefore never appear in any of the listener's event collections.
     */
    @Test
    void testObserveSingleFile() throws IOException {
        final File observedDir = new File(testDir, "test-dir-A");
        observedDir.mkdir();

        // The only file the observer should ever react to.
        File observedFile = new File(observedDir, "A-file1.java");
        // Two siblings the name filter must ignore (different names).
        File ignoredTxtFile = new File(observedDir, "A-file2.txt");
        File ignoredJavaFile = new File(observedDir, "A-file3.java");

        // Observe the directory, but only for the exact name of observedFile.
        final FileFilter onlyObservedFileName = FileFilterUtils.nameFileFilter(observedFile.getName());
        createObserver(observedDir, onlyObservedFileName);

        // Phase A: nothing exists yet, so the first check fires no events.
        observer.checkAndNotify();
        checkCollectionsEmpty("A");
        assertFalse(observedFile.exists(), "A testDirAFile1 exists");

        // Phase B: create all three files on disk.
        observedFile = touch(observedFile);
        ignoredTxtFile = touch(ignoredTxtFile);
        ignoredJavaFile = touch(ignoredJavaFile);
        assertTrue(observedFile.exists(), "B testDirAFile1 exists");
        assertTrue(ignoredTxtFile.exists(), "B testDirAFile2 exists");
        assertTrue(ignoredJavaFile.exists(), "B testDirAFile3 exists");

        // Phase C: exactly one file-created event, for observedFile only.
        // checkCollectionSizes args: dirCreate, dirChange, dirDelete, fileCreate, fileChange, fileDelete.
        observer.checkAndNotify();
        checkCollectionSizes("C", 0, 0, 0, 1, 0, 0);
        assertTrue(listener.getCreatedFiles().contains(observedFile), "C created");
        assertFalse(listener.getCreatedFiles().contains(ignoredTxtFile), "C created");
        assertFalse(listener.getCreatedFiles().contains(ignoredJavaFile), "C created");

        // Phase D: re-touch all three; only observedFile reports a change.
        observedFile = touch(observedFile);
        ignoredTxtFile = touch(ignoredTxtFile);
        ignoredJavaFile = touch(ignoredJavaFile);
        observer.checkAndNotify();
        checkCollectionSizes("D", 0, 0, 0, 0, 1, 0);
        assertTrue(listener.getChangedFiles().contains(observedFile), "D changed");
        assertFalse(listener.getChangedFiles().contains(ignoredTxtFile), "D changed");
        assertFalse(listener.getChangedFiles().contains(ignoredJavaFile), "D changed");

        // Phase E: delete all three; only observedFile reports a delete.
        FileUtils.deleteQuietly(observedFile);
        FileUtils.deleteQuietly(ignoredTxtFile);
        FileUtils.deleteQuietly(ignoredJavaFile);
        assertFalse(observedFile.exists(), "E testDirAFile1 exists");
        assertFalse(ignoredTxtFile.exists(), "E testDirAFile2 exists");
        assertFalse(ignoredJavaFile.exists(), "E testDirAFile3 exists");
        observer.checkAndNotify();
        checkCollectionSizes("E", 0, 0, 0, 0, 0, 1);
        assertTrue(listener.getDeletedFiles().contains(observedFile), "E deleted");
        assertFalse(listener.getDeletedFiles().contains(ignoredTxtFile), "E deleted");
        assertFalse(listener.getDeletedFiles().contains(ignoredJavaFile), "E deleted");
    }
}
