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
public class FileAlterationObserver_ESTest_test07 extends FileAlterationObserver_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        EmptyFileFilter emptyFileFilter = (EmptyFileFilter) EmptyFileFilter.EMPTY;
        MockFile observedDirectory = new MockFile("Q<", "Q<");
        FileEntry rootEntry = new FileEntry(observedDirectory);

        MockFile.createTempFile("org.apache.commons.io.output.WriterOutputStream", "Q<", (File) observedDirectory);

        IOCase systemCaseSensitivity = IOCase.SYSTEM;
        FileEntry[] recursiveChildren = new FileEntry[6];
        recursiveChildren[0] = rootEntry;
        rootEntry.setChildren(recursiveChildren);

        FileAlterationObserver observer = new FileAlterationObserver(rootEntry, emptyFileFilter, systemCaseSensitivity);

        try {
            observer.checkAndNotify();
            fail("Expecting exception: StackOverflowError");
        } catch (StackOverflowError e) {
            // Expected because the root entry is also its own first child.
        }
    }
}
