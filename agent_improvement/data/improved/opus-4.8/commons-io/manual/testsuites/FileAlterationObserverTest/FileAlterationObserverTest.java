/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

/**
 * {@link FileAlterationObserver} Test Case.
 *
 * <p>
 * Several tests below assert against the {@code checkCollectionSizes(label, ...)} helper inherited from
 * {@link AbstractMonitorTest}. That helper verifies how many events the listener recorded during the last
 * {@link #checkAndNotify()} call. Its six numeric arguments are, in order:
 * </p>
 * <ol>
 * <li>directories created</li>
 * <li>directories changed</li>
 * <li>directories deleted</li>
 * <li>files created</li>
 * <li>files changed</li>
 * <li>files deleted</li>
 * </ol>
 * <p>
 * The single-letter labels ("A", "B", ...) passed to the helpers identify the stage of a scenario, so a
 * failure message points at the exact step that broke.
 * </p>
 */
class FileAlterationObserverTest extends AbstractMonitorTest {

    /** Path used by the construction tests; it does not need to exist on disk. */
    private static final String PATH_STRING_FIXTURE = "/foo";

    /**
     * Constructs a new instance.
     */
    FileAlterationObserverTest() {
        listener = new CollectionFileListener(true);
    }

    /**
     * Call {@link FileAlterationObserver#checkAndNotify()}.
     */
    protected void checkAndNotify() {
        observer.checkAndNotify();
    }

    private String directoryToUnixString(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    /**
     * Test add/remove listeners.
     */
    @Test
    void testAddRemoveListeners() {
        final FileAlterationObserver observer = FileAlterationObserver.builder().setFile(PATH_STRING_FIXTURE).getUnchecked();

        // Adding or removing a null listener is a no-op: the observer stays empty.
        observer.addListener(null);
        assertFalse(observer.getListeners().iterator().hasNext(), "Listeners[1]");
        observer.removeListener(null);
        assertFalse(observer.getListeners().iterator().hasNext(), "Listeners[2]");

        // Adding a real listener makes it the sole registered listener.
        final FileAlterationListenerAdaptor listener = new FileAlterationListenerAdaptor();
        observer.addListener(listener);
        final Iterator<FileAlterationListener> it = observer.getListeners().iterator();
        assertTrue(it.hasNext(), "Listeners[3]");
        assertEquals(listener, it.next(), "Added");
        assertFalse(it.hasNext(), "Listeners[4]");

        // Removing it leaves the observer empty again.
        observer.removeListener(listener);
        assertFalse(observer.getListeners().iterator().hasNext(), "Listeners[5]");
    }

    // -----------------------------------------------------------------------
    // Construction via the builder: each test adds one more configured option.
    // -----------------------------------------------------------------------

    @Test
    void testBuilder_File() {
        final File file = new File(PATH_STRING_FIXTURE);
        final FileAlterationObserver observer = FileAlterationObserver.builder().setFile(file).getUnchecked();
        assertEquals(file, observer.getDirectory());
    }

    @Test
    void testBuilder_File_FileFilter() {
        final File file = new File(PATH_STRING_FIXTURE);
        // @formatter:off
        final FileAlterationObserver observer = FileAlterationObserver.builder()
                .setFile(file)
                .setFileFilter(CanReadFileFilter.CAN_READ)
                .getUnchecked();
        // @formatter:on
        assertEquals(file, observer.getDirectory());
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
    }

    @Test
    void testBuilder_File_FileFilter_IOCase() {
        final File file = new File(PATH_STRING_FIXTURE);
        // @formatter:off
        final FileAlterationObserver observer = FileAlterationObserver.builder()
                .setFile(file)
                .setFileFilter(CanReadFileFilter.CAN_READ)
                .setIOCase(IOCase.INSENSITIVE)
                .getUnchecked();
        // @formatter:on
        assertEquals(file, observer.getDirectory());
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
        // INSENSITIVE case sensitivity maps to the case-insensitive name comparator.
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }

    @Test
    void testBuilder_String() {
        final String file = PATH_STRING_FIXTURE;
        final FileAlterationObserver observer = FileAlterationObserver.builder().setFile(file).getUnchecked();
        assertEquals(file, directoryToUnixString(observer));
    }

    @Test
    void testBuilder_String_FileFilter() {
        final String file = PATH_STRING_FIXTURE;
        // @formatter:off
        final FileAlterationObserver observer = FileAlterationObserver.builder()
                .setFile(file)
                .setFileFilter(CanReadFileFilter.CAN_READ)
                .getUnchecked();
        // @formatter:on
        assertEquals(file, directoryToUnixString(observer));
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
    }

    @Test
    void testBuilder_String_FileFilter_IOCase() {
        final String file = PATH_STRING_FIXTURE;
        // @formatter:off
        final FileAlterationObserver observer = FileAlterationObserver.builder()
                .setFile(file)
                .setFileFilter(CanReadFileFilter.CAN_READ)
                .setIOCase(IOCase.INSENSITIVE)
                .getUnchecked();
        // @formatter:on
        assertEquals(file, directoryToUnixString(observer));
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
        // INSENSITIVE case sensitivity maps to the case-insensitive name comparator.
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }

    // -----------------------------------------------------------------------
    // Construction via the deprecated constructors: mirror the builder tests
    // above to confirm both APIs produce equivalent observers.
    // -----------------------------------------------------------------------

    @Test
    void testConstructor_File() {
        final File file = new File(PATH_STRING_FIXTURE);
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(file);
        assertEquals(file, observer.getDirectory());
    }

    @Test
    void testConstructor_File_FileFilter() {
        final File file = new File(PATH_STRING_FIXTURE);
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(file, CanReadFileFilter.CAN_READ);
        assertEquals(file, observer.getDirectory());
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
    }

    @Test
    void testConstructor_File_FileFilter_IOCase() {
        final File file = new File(PATH_STRING_FIXTURE);
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(file, CanReadFileFilter.CAN_READ, IOCase.INSENSITIVE);
        assertEquals(file, observer.getDirectory());
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }

    @Test
    void testConstructor_String() {
        final String file = PATH_STRING_FIXTURE;
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(file);
        assertEquals(file, directoryToUnixString(observer));
    }

    @Test
    void testConstructor_String_FileFilter() {
        final String file = PATH_STRING_FIXTURE;
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(file, CanReadFileFilter.CAN_READ);
        assertEquals(file, directoryToUnixString(observer));
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
    }

    @Test
    void testConstructor_String_FileFilter_IOCase() {
        final String file = PATH_STRING_FIXTURE;
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(file, CanReadFileFilter.CAN_READ, IOCase.INSENSITIVE);
        assertEquals(file, directoryToUnixString(observer));
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }

    /**
     * Tests that creating and deleting directories (and the files within them) fire the expected events.
     * <p>
     * The observer is configured (in {@link AbstractMonitorTest}) with a ".java" suffix filter, so ".txt"
     * files are deliberately ignored throughout this scenario.
     * </p>
     *
     * @throws Exception Thrown on test failure.
     */
    @Test
    void testDirectory() throws Exception {
        // A: nothing has changed yet, so no events fire.
        checkAndNotify();
        checkCollectionsEmpty("A");

        // Create three directories and populate one of them with files.
        final File testDirA = new File(testDir, "test-dir-A");
        final File testDirB = new File(testDir, "test-dir-B");
        final File testDirC = new File(testDir, "test-dir-C");
        testDirA.mkdir();
        testDirB.mkdir();
        testDirC.mkdir();
        final File testDirAFile1 = touch(new File(testDirA, "A-file1.java"));
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.txt")); // filter should ignore this
        final File testDirAFile3 = touch(new File(testDirA, "A-file3.java"));
        File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        final File testDirBFile1 = touch(new File(testDirB, "B-file1.java"));

        // B: 3 directories and 4 ".java" files were created (the ".txt" file is filtered out).
        checkAndNotify();
        checkCollectionSizes("B", 3, 0, 0, 4, 0, 0);
        assertTrue(listener.getCreatedDirectories().contains(testDirA), "B testDirA");
        assertTrue(listener.getCreatedDirectories().contains(testDirB), "B testDirB");
        assertTrue(listener.getCreatedDirectories().contains(testDirC), "B testDirC");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1), "B testDirAFile1");
        assertFalse(listener.getCreatedFiles().contains(testDirAFile2), "B testDirAFile2");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile3), "B testDirAFile3");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile4), "B testDirAFile4");
        assertTrue(listener.getCreatedFiles().contains(testDirBFile1), "B testDirBFile1");

        // C: a re-check with no file system changes fires nothing.
        checkAndNotify();
        checkCollectionsEmpty("C");

        // Touch a file (change) and delete a whole directory.
        testDirAFile4 = touch(testDirAFile4);
        FileUtils.deleteDirectory(testDirB);
        // D: testDirB deleted, testDirAFile4 changed, testDirBFile1 (inside testDirB) deleted.
        checkAndNotify();
        checkCollectionSizes("D", 0, 0, 1, 0, 1, 1);
        assertTrue(listener.getDeletedDirectories().contains(testDirB), "D testDirB");
        assertTrue(listener.getChangedFiles().contains(testDirAFile4), "D testDirAFile4");
        assertTrue(listener.getDeletedFiles().contains(testDirBFile1), "D testDirBFile1");

        // Delete the entire observed tree.
        FileUtils.deleteDirectory(testDir);
        // E: testDirA deleted plus its 3 remaining ".java" files (the ".txt" file was never tracked).
        checkAndNotify();
        checkCollectionSizes("E", 0, 0, 2, 0, 0, 3);
        assertTrue(listener.getDeletedDirectories().contains(testDirA), "E testDirA");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile1), "E testDirAFile1");
        assertFalse(listener.getDeletedFiles().contains(testDirAFile2), "E testDirAFile2");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile3), "E testDirAFile3");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile4), "E testDirAFile4");

        // F and G: recreate the (empty) root directory; no child events fire.
        testDir.mkdir();
        checkAndNotify();
        checkCollectionsEmpty("F");

        checkAndNotify();
        checkCollectionsEmpty("G");
    }

    /**
     * Tests that newly created files fire create events, including files inserted before, between and after
     * existing entries (which exercises the observer's sorted-comparison logic).
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testFileCreate() throws IOException {
        // A: baseline, nothing tracked yet.
        checkAndNotify();
        checkCollectionsEmpty("A");

        File testDirA = new File(testDir, "test-dir-A");
        testDirA.mkdir();
        testDir = touch(testDir);
        testDirA = touch(testDirA);
        // Only files 2 and 4 exist on disk now; 1, 3 and 5 are created later, in the gaps between them.
        File testDirAFile1 = new File(testDirA, "A-file1.java");
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.java"));
        File testDirAFile3 = new File(testDirA, "A-file3.java");
        final File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        File testDirAFile5 = new File(testDirA, "A-file5.java");

        // B: directory test-dir-A plus the 2 existing files are reported as created.
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

        // C: no changes since the last check.
        checkAndNotify();
        checkCollectionsEmpty("C");

        // D: create a file whose name sorts before the first existing entry.
        testDirAFile1 = touch(testDirAFile1);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("D", 0, 1, 0, 1, 0, 0);
        assertTrue(testDirAFile1.exists(), "D testDirAFile1 exists");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1), "D testDirAFile1");

        // E: create a file whose name sorts between two existing entries.
        testDirAFile3 = touch(testDirAFile3);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("E", 0, 1, 0, 1, 0, 0);
        assertTrue(testDirAFile3.exists(), "E testDirAFile3 exists");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile3), "E testDirAFile3");

        // F: create a file whose name sorts after the last existing entry.
        testDirAFile5 = touch(testDirAFile5);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("F", 0, 1, 0, 1, 0, 0);
        assertTrue(testDirAFile5.exists(), "F testDirAFile5 exists");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile5), "F testDirAFile5");
    }

    /**
     * Tests that deleting files fires delete events, removing the first, a middle and the last entry to
     * exercise the observer's sorted-comparison logic.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testFileDelete() throws IOException {
        // A: baseline, nothing tracked yet.
        checkAndNotify();
        checkCollectionsEmpty("A");

        File testDirA = new File(testDir, "test-dir-A");
        testDirA.mkdir();
        testDir = touch(testDir);
        testDirA = touch(testDirA);
        final File testDirAFile1 = touch(new File(testDirA, "A-file1.java"));
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.java"));
        final File testDirAFile3 = touch(new File(testDirA, "A-file3.java"));
        final File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        final File testDirAFile5 = touch(new File(testDirA, "A-file5.java"));

        assertTrue(testDirAFile1.exists(), "B testDirAFile1 exists");
        assertTrue(testDirAFile2.exists(), "B testDirAFile2 exists");
        assertTrue(testDirAFile3.exists(), "B testDirAFile3 exists");
        assertTrue(testDirAFile4.exists(), "B testDirAFile4 exists");
        assertTrue(testDirAFile5.exists(), "B testDirAFile5 exists");

        // B: directory test-dir-A plus all 5 files are reported as created.
        checkAndNotify();
        checkCollectionSizes("B", 1, 0, 0, 5, 0, 0);
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1), "B testDirAFile1");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile2), "B testDirAFile2");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile3), "B testDirAFile3");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile4), "B testDirAFile4");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile5), "B testDirAFile5");

        // C: no changes since the last check.
        checkAndNotify();
        checkCollectionsEmpty("C");

        // D: delete the first entry.
        FileUtils.deleteQuietly(testDirAFile1);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("D", 0, 1, 0, 0, 0, 1);
        assertFalse(testDirAFile1.exists(), "D testDirAFile1 exists");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile1), "D testDirAFile1");

        // E: delete a middle entry.
        FileUtils.deleteQuietly(testDirAFile3);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("E", 0, 1, 0, 0, 0, 1);
        assertFalse(testDirAFile3.exists(), "E testDirAFile3 exists");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile3), "E testDirAFile3");

        // F: delete the last entry.
        FileUtils.deleteQuietly(testDirAFile5);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("F", 0, 1, 0, 0, 0, 1);
        assertFalse(testDirAFile5.exists(), "F testDirAFile5 exists");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile5), "F testDirAFile5");
    }

    /**
     * Tests that modifying files fires change events, updating the first, a middle and the last entry.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testFileUpdate() throws IOException {
        // A: baseline, nothing tracked yet.
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

        // B: directory test-dir-A plus all 5 files are reported as created.
        checkAndNotify();
        checkCollectionSizes("B", 1, 0, 0, 5, 0, 0);
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1), "B testDirAFile1");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile2), "B testDirAFile2");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile3), "B testDirAFile3");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile4), "B testDirAFile4");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile5), "B testDirAFile5");

        assertTrue(testDirAFile1.exists(), "B testDirAFile1 exists");
        assertTrue(testDirAFile2.exists(), "B testDirAFile2 exists");
        assertTrue(testDirAFile3.exists(), "B testDirAFile3 exists");
        assertTrue(testDirAFile4.exists(), "B testDirAFile4 exists");
        assertTrue(testDirAFile5.exists(), "B testDirAFile5 exists");

        // C: no changes since the last check.
        checkAndNotify();
        checkCollectionsEmpty("C");

        // D: update the first entry.
        testDirAFile1 = touch(testDirAFile1);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("D", 0, 1, 0, 0, 1, 0);
        assertTrue(listener.getChangedFiles().contains(testDirAFile1), "D testDirAFile1");

        // E: update a middle entry.
        testDirAFile3 = touch(testDirAFile3);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("E", 0, 1, 0, 0, 1, 0);
        assertTrue(listener.getChangedFiles().contains(testDirAFile3), "E testDirAFile3");

        // F: update the last entry.
        testDirAFile5 = touch(testDirAFile5);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("F", 0, 1, 0, 0, 1, 0);
        assertTrue(listener.getChangedFiles().contains(testDirAFile5), "F testDirAFile5");
    }

    /**
     * Tests that an observer scoped to a single file (via a name filter) reports create, change and delete
     * events only for that file and ignores its siblings.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testObserveSingleFile() throws IOException {
        final File testDirA = new File(testDir, "test-dir-A");
        File testDirAFile1 = new File(testDirA, "A-file1.java");
        testDirA.mkdir();

        // Observe only the file named "A-file1.java"; every other file must be ignored.
        final FileFilter nameFilter = FileFilterUtils.nameFileFilter(testDirAFile1.getName());
        createObserver(testDirA, nameFilter);
        checkAndNotify();
        checkCollectionsEmpty("A");
        assertFalse(testDirAFile1.exists(), "A testDirAFile1 exists");

        // Create the observed file plus two siblings that the filter excludes.
        testDirAFile1 = touch(testDirAFile1);
        File testDirAFile2 = touch(new File(testDirA, "A-file2.txt")); /* filter should ignore */
        File testDirAFile3 = touch(new File(testDirA, "A-file3.java")); /* filter should ignore */
        assertTrue(testDirAFile1.exists(), "B testDirAFile1 exists");
        assertTrue(testDirAFile2.exists(), "B testDirAFile2 exists");
        assertTrue(testDirAFile3.exists(), "B testDirAFile3 exists");
        // C: only the observed file is reported as created.
        checkAndNotify();
        checkCollectionSizes("C", 0, 0, 0, 1, 0, 0);
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1), "C created");
        assertFalse(listener.getCreatedFiles().contains(testDirAFile2), "C created");
        assertFalse(listener.getCreatedFiles().contains(testDirAFile3), "C created");

        // Modify all three files; only the observed file is reported as changed.
        testDirAFile1 = touch(testDirAFile1);
        testDirAFile2 = touch(testDirAFile2);
        testDirAFile3 = touch(testDirAFile3);
        checkAndNotify();
        checkCollectionSizes("D", 0, 0, 0, 0, 1, 0);
        assertTrue(listener.getChangedFiles().contains(testDirAFile1), "D changed");
        assertFalse(listener.getChangedFiles().contains(testDirAFile2), "D changed");
        assertFalse(listener.getChangedFiles().contains(testDirAFile3), "D changed");

        // Delete all three files; only the observed file is reported as deleted.
        FileUtils.deleteQuietly(testDirAFile1);
        FileUtils.deleteQuietly(testDirAFile2);
        FileUtils.deleteQuietly(testDirAFile3);
        assertFalse(testDirAFile1.exists(), "E testDirAFile1 exists");
        assertFalse(testDirAFile2.exists(), "E testDirAFile2 exists");
        assertFalse(testDirAFile3.exists(), "E testDirAFile3 exists");
        checkAndNotify();
        checkCollectionSizes("E", 0, 0, 0, 0, 0, 1);
        assertTrue(listener.getDeletedFiles().contains(testDirAFile1), "E deleted");
        assertFalse(listener.getDeletedFiles().contains(testDirAFile2), "E deleted");
        assertFalse(listener.getDeletedFiles().contains(testDirAFile3), "E deleted");
    }

    /**
     * Test toString().
     */
    @Test
    void testToString() {
        final File file = new File(PATH_STRING_FIXTURE);
        final Builder builder = FileAlterationObserver.builder();

        // Without a custom filter, toString() shows the default "true" filter.
        FileAlterationObserver observer = builder.setFile(file).getUnchecked();
        assertEquals("FileAlterationObserver[file='" + file.getPath() + "', true, listeners=0]", observer.toString());

        // With a custom filter, toString() shows that filter's name instead.
        observer = builder.setFileFilter(CanReadFileFilter.CAN_READ).getUnchecked();
        assertEquals("FileAlterationObserver[file='" + file.getPath() + "', CanReadFileFilter, listeners=0]", observer.toString());
        assertEquals(file, observer.getDirectory());
    }
}
