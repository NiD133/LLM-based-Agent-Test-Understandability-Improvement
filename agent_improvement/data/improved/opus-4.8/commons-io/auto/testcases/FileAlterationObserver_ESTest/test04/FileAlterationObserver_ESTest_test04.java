package org.apache.commons.io.monitor;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test04 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that {@link FileAlterationObserver#checkAndNotify()} runs without error
     * when the observed root entry wraps an empty-named, non-existent file, and that the
     * root entry's name is left unchanged ("") by the check.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Root entry to observe: a file with an empty name (does not exist on the VFS).
        MockFile rootFile = new MockFile("", "");
        FileEntry rootEntry = new FileEntry(rootFile);

        // Build an observer for that root entry and initialize its baseline state.
        FileAlterationObserver.Builder builder = FileAlterationObserver.builder();
        builder.setRootEntry(rootEntry);
        FileAlterationObserver builtObserver = builder.get();
        builtObserver.initialize();

        // Create an unrelated temp file (not the observed root) to exercise the VFS.
        MockFile.createTempFile("^i[JH", "rootEntry");

        // Build a file filter that requires all four (identical) age conditions to match.
        MockDate cutoffDate = new MockDate(-2406, -2406, -2406, -2406, -2406, 2);
        AgeFileFilter ageFilter = new AgeFileFilter(cutoffDate);
        IOFileFilter[] ageFilters = new IOFileFilter[4];
        ageFilters[0] = ageFilter;
        ageFilters[1] = ageFilter;
        ageFilters[2] = ageFilter;
        ageFilters[3] = ageFilter;
        AndFileFilter combinedFilter = new AndFileFilter(ageFilters);

        // A second observer over the same root entry, using the combined filter and
        // case-sensitive name comparison, with a no-op listener registered.
        FileAlterationObserver filteredObserver =
                new FileAlterationObserver(rootEntry, combinedFilter, IOCase.SENSITIVE);
        filteredObserver.addListener(new FileAlterationListenerAdaptor());

        // The root file does not exist, so the check completes without firing events.
        filteredObserver.checkAndNotify();

        assertEquals("", rootEntry.getName());
    }
}
