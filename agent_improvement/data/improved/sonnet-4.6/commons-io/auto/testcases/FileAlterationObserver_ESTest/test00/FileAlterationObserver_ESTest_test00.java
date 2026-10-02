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
public class FileAlterationObserver_ESTest_test00 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that two FileAlterationObserver instances watching different
     * directories are not considered equal to each other.
     */
    @Test(timeout = 4000)
    public void test00_observersWatchingDifferentDirectoriesAreNotEqual() throws Throwable {
        // Arrange: build an observer for directory "Y0" using the fluent Builder API
        FileAlterationObserver.Builder builder = FileAlterationObserver.builder();
        builder.setPath("Y0");
        FileAlterationObserver observerForY0 = builder.get();

        // Arrange: create a second observer for the empty-string directory path
        FileAlterationObserver observerForEmptyPath = new FileAlterationObserver("");

        // Assert: observers watching different directories must not be equal
        assertFalse(observerForEmptyPath.equals((Object) observerForY0));
    }
}
