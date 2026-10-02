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
 * Tests for {@link FileAlterationObserver}.
 *
 * <p>Covers:
 * <ul>
 *   <li>Listener registration and removal ({@link #testAddRemoveListeners()})</li>
 *   <li>Builder API with every combination of File/String path, FileFilter, and IOCase
 *       ({@code testBuilder_*} methods)</li>
 *   <li>Deprecated constructors with the same combinations
 *       ({@code testConstructor_*} methods)</li>
 *   <li>Directory and file change detection via {@link FileAlterationObserver#checkAndNotify()}:
 *       creation, modification, and deletion for directories and files
 *       ({@link #testDirectory()}, {@link #testFileCreate()}, {@link #testFileDelete()},
 *        {@link #testFileUpdate()}, {@link #testObserveSingleFile()})</li>
 *   <li>{@link FileAlterationObserver#toString()} formatting ({@link #testToString()})</li>
 * </ul>
 *
 * <p>The base class {@link AbstractMonitorTest} wires up an observer that accepts only
 * visible directories and {@code .java} files.  Tests that need a different filter call
 * {@code createObserver()} explicitly.
 */
class FileAlterationObserverTest extends AbstractMonitorTest {

    /** Path constant used for observer construction tests that do not touch the filesystem. */
    private static final String PATH_STRING_FIXTURE = "/foo";

    /**
     * Constructs a new instance.
     */
    FileAlterationObserverTest() {
        listener = new CollectionFileListener(true);
    }

    /**
     * Triggers one observation cycle on the shared observer, causing the listener
     * to be notified of any filesystem changes since the last call.
     */
    protected void checkAndNotify() {
        observer.checkAndNotify();
    }

    /**
     * Returns the observed directory path with Unix-style separators, making assertions
     * platform-independent.
     */
    private String directoryToUnixString(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    /**
     * Verifies that {@link FileAlterationObserver#addListener} and
     * {@link FileAlterationObserver#removeListener} handle null gracefully and
     * maintain accurate listener counts.
     */
    @Test
    void testAddRemoveListeners() {
        final FileAlterationObserver observer = FileAlterationObserver.builder().setFile(PATH_STRING_FIXTURE).getUnchecked();

        // Null listener — observer should silently ignore it
        observer.addListener(null);
        assertFalse(observer.getListeners().iterator().hasNext(), "Listeners[1]");
        observer.removeListener(null);
        assertFalse(observer.getListeners().iterator().hasNext(), "Listeners[2]");

        // Add a real listener — it should appear exactly once
        final FileAlterationListenerAdaptor listener = new FileAlterationListenerAdaptor();
        observer.addListener(listener);
        final Iterator<FileAlterationListener> it = observer.getListeners().iterator();
        assertTrue(it.hasNext(), "Listeners[3]");
        assertEquals(listener, it.next(), "Added");
        assertFalse(it.hasNext(), "Listeners[4]");

        // Remove the listener — collection should be empty again
        observer.removeListener(listener);
        assertFalse(observer.getListeners().iterator().hasNext(), "Listeners[5]");
    }

    /**
     * Builder with a {@link File} argument — {@code getDirectory()} must return the same file.
     */
    @Test
    void testBuilder_File() {
        final File file = new File(PATH_STRING_FIXTURE);
        final FileAlterationObserver observer = FileAlterationObserver.builder().setFile(file).getUnchecked();
        assertEquals(file, observer.getDirectory());
    }

    /**
     * Builder with a {@link File} and a {@link FileFilter} — both must be stored correctly.
     */
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

    /**
     * Builder with a {@link File}, a {@link FileFilter}, and {@link IOCase#INSENSITIVE} —
     * the comparator must be the case-insensitive name comparator.
     */
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
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }

    /**
     * Builder with a {@link String} path — the Unix-normalised path must round-trip correctly.
     */
    @Test
    void testBuilder_String() {
        final String file = PATH_STRING_FIXTURE;
        final FileAlterationObserver observer = FileAlterationObserver.builder().setFile(file).getUnchecked();
        assertEquals(file, directoryToUnixString(observer));
    }

    /**
     * Builder with a {@link String} path and a {@link FileFilter} — both must be stored correctly.
     */
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

    /**
     * Builder with a {@link String} path, a {@link FileFilter}, and {@link IOCase#INSENSITIVE} —
     * the comparator must be the case-insensitive name comparator.
     */
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
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }

    /**
     * Deprecated constructor with a {@link File} — {@code getDirectory()} must return the same file.
     */
    @Test
    void testConstructor_File() {
        final File file = new File(PATH_STRING_FIXTURE);
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(file);
        assertEquals(file, observer.getDirectory());
    }

    /**
     * Deprecated constructor with a {@link File} and a {@link FileFilter} —
     * both must be stored correctly.
     */
    @Test
    void testConstructor_File_FileFilter() {
        final File file = new File(PATH_STRING_FIXTURE);
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(file, CanReadFileFilter.CAN_READ);
        assertEquals(file, observer.getDirectory());
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
    }

    /**
     * Deprecated constructor with a {@link File}, a {@link FileFilter}, and
     * {@link IOCase#INSENSITIVE} — the comparator must be the case-insensitive name comparator.
     */
    @Test
    void testConstructor_File_FileFilter_IOCase() {
        final File file = new File(PATH_STRING_FIXTURE);
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(file, CanReadFileFilter.CAN_READ, IOCase.INSENSITIVE);
        assertEquals(file, observer.getDirectory());
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }

    /**
     * Deprecated constructor with a {@link String} path — the Unix-normalised path must
     * round-trip correctly.
     */
    @Test
    void testConstructor_String() {
        final String file = PATH_STRING_FIXTURE;
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(file);
        assertEquals(file, directoryToUnixString(observer));
    }

    /**
     * Deprecated constructor with a {@link String} path and a {@link FileFilter} —
     * both must be stored correctly.
     */
    @Test
    void testConstructor_String_FileFilter() {
        final String file = PATH_STRING_FIXTURE;
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(file, CanReadFileFilter.CAN_READ);
        assertEquals(file, directoryToUnixString(observer));
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
    }

    /**
     * Deprecated constructor with a {@link String} path, a {@link FileFilter}, and
     * {@link IOCase#INSENSITIVE} — the comparator must be the case-insensitive name comparator.
     */
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
     * Tests {@link FileAlterationObserver#checkAndNotify()} for directory-level events:
     * creation, modification (implicitly via child changes), and deletion.
     *
     * <p>Scenario outline:
     * <ol>
     *   <li><b>Phase A</b> – baseline: no changes yet.</li>
     *   <li><b>Phase B</b> – create three subdirectories and five files (one {@code .txt} file
     *       that the observer's filter must exclude).</li>
     *   <li><b>Phase C</b> – no filesystem changes; collections should be empty again.</li>
     *   <li><b>Phase D</b> – touch one file (→ changed) and delete an entire subdirectory
     *       (→ directory deleted, contained file deleted).</li>
     *   <li><b>Phase E</b> – delete the root test directory; remaining subdirectory and its
     *       accepted files are reported as deleted (the excluded {@code .txt} file is not).</li>
     *   <li><b>Phase F</b> – recreate the empty root directory; no events expected.</li>
     *   <li><b>Phase G</b> – idle check; collections stay empty.</li>
     * </ol>
     *
     * @throws Exception Thrown on test failure.
     */
    @Test
    void testDirectory() throws Exception {
        // Phase A: baseline — no events before any filesystem changes
        checkAndNotify();
        checkCollectionsEmpty("A");

        // Set up three subdirectories and files; testDirAFile2 (.txt) is excluded by the filter
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

        // Phase B: 3 dirs created, 4 accepted java files created (txt file excluded)
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

        // Phase C: no changes — all collections must be empty
        checkAndNotify();
        checkCollectionsEmpty("C");

        // Touch file4 (→ changed) and delete dirB with its file (→ 1 dir deleted, 1 file deleted)
        testDirAFile4 = touch(testDirAFile4);
        FileUtils.deleteDirectory(testDirB);

        // Phase D: 1 dir deleted (testDirB), 1 file changed (file4), 1 file deleted (B-file1)
        checkAndNotify();
        checkCollectionSizes("D", 0, 0, 1, 0, 1, 1);
        assertTrue(listener.getDeletedDirectories().contains(testDirB), "D testDirB");
        assertTrue(listener.getChangedFiles().contains(testDirAFile4), "D testDirAFile4");
        assertTrue(listener.getDeletedFiles().contains(testDirBFile1), "D testDirBFile1");

        // Delete the entire root test directory
        FileUtils.deleteDirectory(testDir);

        // Phase E: 2 dirs deleted (testDirA, testDirC), 3 accepted files deleted;
        //          testDirAFile2 (.txt) must NOT appear as deleted because it was never tracked
        checkAndNotify();
        checkCollectionSizes("E", 0, 0, 2, 0, 0, 3);
        assertTrue(listener.getDeletedDirectories().contains(testDirA), "E testDirA");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile1), "E testDirAFile1");
        assertFalse(listener.getDeletedFiles().contains(testDirAFile2), "E testDirAFile2");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile3), "E testDirAFile3");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile4), "E testDirAFile4");

        // Recreate the now-empty root directory
        testDir.mkdir();

        // Phase F: empty directory recreated — no create/change/delete events
        checkAndNotify();
        checkCollectionsEmpty("F");

        // Phase G: idle — collections remain empty
        checkAndNotify();
        checkCollectionsEmpty("G");
    }

    /**
     * Tests {@link FileAlterationObserver#checkAndNotify()} for file creation events,
     * including files created before the first observation and files created at positions
     * that are lexicographically before, between, and after existing entries.
     *
     * <p>Scenario outline:
     * <ol>
     *   <li><b>Phase A</b> – baseline: no events.</li>
     *   <li><b>Phase B</b> – only the files that exist on disk (file2, file4) are reported
     *       as created; file1, file3, file5 do not exist yet.</li>
     *   <li><b>Phase C</b> – no changes.</li>
     *   <li><b>Phase D</b> – create file1 (name &lt; first existing entry); observer detects it.</li>
     *   <li><b>Phase E</b> – create file3 (name between two existing entries); observer detects it.</li>
     *   <li><b>Phase F</b> – create file5 (name &gt; last existing entry); observer detects it.</li>
     * </ol>
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testFileCreate() throws IOException {
        // Phase A: baseline — no events before any filesystem changes
        checkAndNotify();
        checkCollectionsEmpty("A");

        // Create a subdirectory; only touch files that should actually exist on disk
        File testDirA = new File(testDir, "test-dir-A");
        testDirA.mkdir();
        testDir = touch(testDir);
        testDirA = touch(testDirA);
        File testDirAFile1 = new File(testDirA, "A-file1.java");   // will be created later (phase D)
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.java"));
        File testDirAFile3 = new File(testDirA, "A-file3.java");   // will be created later (phase E)
        final File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        File testDirAFile5 = new File(testDirA, "A-file5.java");   // will be created later (phase F)

        // Phase B: 1 dir created (testDirA), 2 files created (file2, file4);
        //          file1/file3/file5 don't exist yet and must not appear
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

        // Phase C: no changes
        checkAndNotify();
        checkCollectionsEmpty("C");

        // Create file with name < first entry (file1 sorts before file2)
        testDirAFile1 = touch(testDirAFile1);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("D", 0, 1, 0, 1, 0, 0);
        assertTrue(testDirAFile1.exists(), "D testDirAFile1 exists");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1), "D testDirAFile1");

        // Create file with name between 2 entries (file3 sorts between file2 and file4)
        testDirAFile3 = touch(testDirAFile3);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("E", 0, 1, 0, 1, 0, 0);
        assertTrue(testDirAFile3.exists(), "E testDirAFile3 exists");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile3), "E testDirAFile3");

        // Create file with name > last entry (file5 sorts after file4)
        testDirAFile5 = touch(testDirAFile5);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("F", 0, 1, 0, 1, 0, 0);
        assertTrue(testDirAFile5.exists(), "F testDirAFile5 exists");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile5), "F testDirAFile5");
    }

    /**
     * Tests {@link FileAlterationObserver#checkAndNotify()} for file deletion events,
     * verifying that deleting the first, a middle, and the last entry in the tracked list
     * is each detected correctly.
     *
     * <p>Scenario outline:
     * <ol>
     *   <li><b>Phase A</b> – baseline: no events.</li>
     *   <li><b>Phase B</b> – all five files created.</li>
     *   <li><b>Phase C</b> – no changes.</li>
     *   <li><b>Phase D</b> – delete file1 (first entry); observer reports it deleted.</li>
     *   <li><b>Phase E</b> – delete file3 (middle entry); observer reports it deleted.</li>
     *   <li><b>Phase F</b> – delete file5 (last entry); observer reports it deleted.</li>
     * </ol>
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testFileDelete() throws IOException {
        // Phase A: baseline — no events before any filesystem changes
        checkAndNotify();
        checkCollectionsEmpty("A");

        // Create a subdirectory and all five files
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

        // Phase B: 1 dir created, 5 files created
        checkAndNotify();
        checkCollectionSizes("B", 1, 0, 0, 5, 0, 0);
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1), "B testDirAFile1");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile2), "B testDirAFile2");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile3), "B testDirAFile3");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile4), "B testDirAFile4");
        assertTrue(listener.getCreatedFiles().contains(testDirAFile5), "B testDirAFile5");

        // Phase C: no changes
        checkAndNotify();
        checkCollectionsEmpty("C");

        // Delete first entry (file1 sorts before all remaining files)
        FileUtils.deleteQuietly(testDirAFile1);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("D", 0, 1, 0, 0, 0, 1);
        assertFalse(testDirAFile1.exists(), "D testDirAFile1 exists");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile1), "D testDirAFile1");

        // Delete file with name between 2 entries (file3 is between remaining file2 and file4)
        FileUtils.deleteQuietly(testDirAFile3);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("E", 0, 1, 0, 0, 0, 1);
        assertFalse(testDirAFile3.exists(), "E testDirAFile3 exists");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile3), "E testDirAFile3");

        // Delete last entry (file5 sorts after remaining file2 and file4)
        FileUtils.deleteQuietly(testDirAFile5);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("F", 0, 1, 0, 0, 0, 1);
        assertFalse(testDirAFile5.exists(), "F testDirAFile5 exists");
        assertTrue(listener.getDeletedFiles().contains(testDirAFile5), "F testDirAFile5");
    }

    /**
     * Tests {@link FileAlterationObserver#checkAndNotify()} for file modification events,
     * verifying that touching the first, a middle, and the last entry in the tracked list
     * is each detected correctly.
     *
     * <p>Scenario outline:
     * <ol>
     *   <li><b>Phase A</b> – baseline: no events.</li>
     *   <li><b>Phase B</b> – all five files created.</li>
     *   <li><b>Phase C</b> – no changes.</li>
     *   <li><b>Phase D</b> – touch file1 (first entry); observer reports it changed.</li>
     *   <li><b>Phase E</b> – touch file3 (middle entry); observer reports it changed.</li>
     *   <li><b>Phase F</b> – touch file5 (last entry); observer reports it changed.</li>
     * </ol>
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testFileUpdate() throws IOException {
        // Phase A: baseline — no events before any filesystem changes
        checkAndNotify();
        checkCollectionsEmpty("A");

        // Create a subdirectory and all five files
        File testDirA = new File(testDir, "test-dir-A");
        testDirA.mkdir();
        testDir = touch(testDir);
        testDirA = touch(testDirA);
        File testDirAFile1 = touch(new File(testDirA, "A-file1.java"));
        final File testDirAFile2 = touch(new File(testDirA, "A-file2.java"));
        File testDirAFile3 = touch(new File(testDirA, "A-file3.java"));
        final File testDirAFile4 = touch(new File(testDirA, "A-file4.java"));
        File testDirAFile5 = touch(new File(testDirA, "A-file5.java"));

        // Phase B: 1 dir created, 5 files created
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

        // Phase C: no changes
        checkAndNotify();
        checkCollectionsEmpty("C");

        // Update first entry (file1 sorts before all other files)
        testDirAFile1 = touch(testDirAFile1);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("D", 0, 1, 0, 0, 1, 0);
        assertTrue(listener.getChangedFiles().contains(testDirAFile1), "D testDirAFile1");

        // Update file with name between 2 entries (file3 is between file2 and file4)
        testDirAFile3 = touch(testDirAFile3);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("E", 0, 1, 0, 0, 1, 0);
        assertTrue(listener.getChangedFiles().contains(testDirAFile3), "E testDirAFile3");

        // Update last entry (file5 sorts after all other files)
        testDirAFile5 = touch(testDirAFile5);
        testDirA = touch(testDirA);
        checkAndNotify();
        checkCollectionSizes("F", 0, 1, 0, 0, 1, 0);
        assertTrue(listener.getChangedFiles().contains(testDirAFile5), "F testDirAFile5");
    }

    /**
     * Tests that an observer configured with a name-based {@link FileFilter} tracks
     * only the single matching file, ignoring other files in the same directory.
     *
     * <p>Scenario outline:
     * <ol>
     *   <li><b>Phase A</b> – baseline: file does not exist yet; no events.</li>
     *   <li><b>Phase C</b> – create all three files; only {@code A-file1.java} (the filtered
     *       one) is reported as created; {@code A-file2.txt} and {@code A-file3.java}
     *       are ignored because they do not match the name filter.</li>
     *   <li><b>Phase D</b> – touch all three files; only {@code A-file1.java} is reported
     *       as changed.</li>
     *   <li><b>Phase E</b> – delete all three files; only {@code A-file1.java} is reported
     *       as deleted.</li>
     * </ol>
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testObserveSingleFile() throws IOException {
        // Set up: observe testDirA with a filter that matches only "A-file1.java"
        final File testDirA = new File(testDir, "test-dir-A");
        File testDirAFile1 = new File(testDirA, "A-file1.java");
        testDirA.mkdir();

        final FileFilter nameFilter = FileFilterUtils.nameFileFilter(testDirAFile1.getName());
        createObserver(testDirA, nameFilter);

        // Phase A: file doesn't exist yet — no events
        checkAndNotify();
        checkCollectionsEmpty("A");
        assertFalse(testDirAFile1.exists(), "A testDirAFile1 exists");

        // Create all three files; only the name-matched file should be tracked
        testDirAFile1 = touch(testDirAFile1);
        File testDirAFile2 = touch(new File(testDirA, "A-file2.txt")); /* filter should ignore */
        File testDirAFile3 = touch(new File(testDirA, "A-file3.java")); /* filter should ignore */
        assertTrue(testDirAFile1.exists(), "B testDirAFile1 exists");
        assertTrue(testDirAFile2.exists(), "B testDirAFile2 exists");
        assertTrue(testDirAFile3.exists(), "B testDirAFile3 exists");

        // Phase C: only file1 created (file2 and file3 ignored by name filter)
        checkAndNotify();
        checkCollectionSizes("C", 0, 0, 0, 1, 0, 0);
        assertTrue(listener.getCreatedFiles().contains(testDirAFile1), "C created");
        assertFalse(listener.getCreatedFiles().contains(testDirAFile2), "C created");
        assertFalse(listener.getCreatedFiles().contains(testDirAFile3), "C created");

        // Modify all three files; only the name-matched file should be reported as changed
        testDirAFile1 = touch(testDirAFile1);
        testDirAFile2 = touch(testDirAFile2);
        testDirAFile3 = touch(testDirAFile3);

        // Phase D: only file1 changed (file2 and file3 ignored by name filter)
        checkAndNotify();
        checkCollectionSizes("D", 0, 0, 0, 0, 1, 0);
        assertTrue(listener.getChangedFiles().contains(testDirAFile1), "D changed");
        assertFalse(listener.getChangedFiles().contains(testDirAFile2), "D changed");
        assertFalse(listener.getChangedFiles().contains(testDirAFile3), "D changed");

        // Delete all three files; only the name-matched file should be reported as deleted
        FileUtils.deleteQuietly(testDirAFile1);
        FileUtils.deleteQuietly(testDirAFile2);
        FileUtils.deleteQuietly(testDirAFile3);
        assertFalse(testDirAFile1.exists(), "E testDirAFile1 exists");
        assertFalse(testDirAFile2.exists(), "E testDirAFile2 exists");
        assertFalse(testDirAFile3.exists(), "E testDirAFile3 exists");

        // Phase E: only file1 deleted (file2 and file3 ignored by name filter)
        checkAndNotify();
        checkCollectionSizes("E", 0, 0, 0, 0, 0, 1);
        assertTrue(listener.getDeletedFiles().contains(testDirAFile1), "E deleted");
        assertFalse(listener.getDeletedFiles().contains(testDirAFile2), "E deleted");
        assertFalse(listener.getDeletedFiles().contains(testDirAFile3), "E deleted");
    }

    /**
     * Tests {@link FileAlterationObserver#toString()} formatting.
     *
     * <p>Without a file filter the output contains {@code "true"} (the string representation of
     * {@link org.apache.commons.io.filefilter.TrueFileFilter}).  With a filter the filter's own
     * {@code toString()} is embedded instead.
     */
    @Test
    void testToString() {
        final File file = new File(PATH_STRING_FIXTURE);
        final Builder builder = FileAlterationObserver.builder();

        // No filter: TrueFileFilter renders as "true"
        FileAlterationObserver observer = builder.setFile(file).getUnchecked();
        assertEquals("FileAlterationObserver[file='" + file.getPath() + "', true, listeners=0]", observer.toString());

        // With CanReadFileFilter: filter name is embedded in the output
        observer = builder.setFileFilter(CanReadFileFilter.CAN_READ).getUnchecked();
        assertEquals("FileAlterationObserver[file='" + file.getPath() + "', CanReadFileFilter, listeners=0]", observer.toString());
        assertEquals(file, observer.getDirectory());
    }
}
