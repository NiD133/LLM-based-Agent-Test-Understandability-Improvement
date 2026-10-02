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
     * Two distinct {@link FileAlterationObserver} instances built for different
     * directories are not considered equal. Since the class does not override
     * {@link Object#equals(Object)}, equality is reference identity, so two
     * separately created observers never compare equal.
     */
    @Test(timeout = 4000)
    public void twoDistinctObserversAreNotEqual() throws Throwable {
        // Build one observer for directory "Y0" using the builder API.
        FileAlterationObserver.Builder builder = FileAlterationObserver.builder();
        builder.setPath("Y0");
        FileAlterationObserver observerForY0 = builder.get();

        // Create a second, independent observer for the empty-name directory.
        FileAlterationObserver observerForEmptyDir = new FileAlterationObserver("");

        // The two observers are different objects, hence not equal.
        assertFalse(observerForEmptyDir.equals((Object) observerForY0));
    }
}
