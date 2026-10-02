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

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        MockFile rootFile = new MockFile("", "");
        FileAlterationObserver.Builder observerBuilder = FileAlterationObserver.builder();
        IOCase caseSensitivity = IOCase.SENSITIVE;
        FileEntry rootEntry = new FileEntry(rootFile);
        observerBuilder.setRootEntry(rootEntry);

        MockDate cutoffDate = new MockDate((-2406), (-2406), (-2406), (-2406), (-2406), 2);
        AgeFileFilter ageFilter = new AgeFileFilter(cutoffDate);
        MockFile.createTempFile("^i[JH", "rootEntry");

        FileAlterationObserver initializedObserver = observerBuilder.get();
        initializedObserver.initialize();

        FileAlterationListenerAdaptor listener = new FileAlterationListenerAdaptor();
        IOFileFilter[] filters = new IOFileFilter[4];
        filters[0] = (IOFileFilter) ageFilter;
        filters[1] = (IOFileFilter) ageFilter;
        filters[2] = (IOFileFilter) ageFilter;
        filters[3] = (IOFileFilter) ageFilter;

        AndFileFilter combinedFilter = new AndFileFilter(filters);
        FileAlterationObserver notifyingObserver = new FileAlterationObserver(rootEntry, combinedFilter, caseSensitivity);
        notifyingObserver.addListener(listener);
        notifyingObserver.checkAndNotify();

        assertEquals("", rootEntry.getName());
    }
}
