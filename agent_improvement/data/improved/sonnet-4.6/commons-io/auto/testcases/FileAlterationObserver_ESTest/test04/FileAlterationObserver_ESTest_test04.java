package org.apache.commons.io.monitor;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.File;
import java.io.FileFilter;
import java.util.Comparator;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.EmptyFileFilter;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.evosuite.runtime.testdata.EvoSuiteFile;
import org.evosuite.runtime.testdata.FileSystemHandling;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test04 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that a FileAlterationObserver built via the Builder API (with a custom rootEntry)
     * can be successfully initialized, and that a second observer using an age-based AND-filter
     * can run checkAndNotify() without errors. The FileEntry's name must remain unchanged after
     * these operations.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Create a mock file with an empty name to use as the observed root
        MockFile rootFile = new MockFile("", "");
        FileEntry rootEntry = new FileEntry(rootFile);

        // Build an observer via the Builder API, providing the shared rootEntry directly
        FileAlterationObserver.Builder observerBuilder = FileAlterationObserver.builder();
        observerBuilder.setRootEntry(rootEntry);

        // Create a temp file in the virtual filesystem as a side effect required by the test setup
        MockFile.createTempFile("^i[JH", "rootEntry");

        // Build and initialize the builder-based observer
        FileAlterationObserver builderObserver = observerBuilder.get();
        builderObserver.initialize();

        // Build an age-based filter using a date far in the past (extreme negative field values)
        MockDate pastDate = new MockDate((-2406), (-2406), (-2406), (-2406), (-2406), 2);
        AgeFileFilter ageFilter = new AgeFileFilter(pastDate);

        // Compose four copies of the age filter into a single AND filter
        IOFileFilter[] ageFilters = new IOFileFilter[4];
        ageFilters[0] = ageFilter;
        ageFilters[1] = ageFilter;
        ageFilters[2] = ageFilter;
        ageFilters[3] = ageFilter;
        AndFileFilter combinedFilter = new AndFileFilter(ageFilters);

        // Create a second observer over the same rootEntry with the combined filter and case-sensitive comparison
        FileAlterationObserver filteredObserver = new FileAlterationObserver(rootEntry, combinedFilter, IOCase.SENSITIVE);

        // Register a listener and trigger change detection on the filtered observer
        FileAlterationListenerAdaptor listener = new FileAlterationListenerAdaptor();
        filteredObserver.addListener(listener);
        filteredObserver.checkAndNotify();

        // The rootEntry's name must remain the empty string that the MockFile was created with
        assertEquals("", rootEntry.getName());
    }
}
