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
public class FileAlterationObserver_ESTest_test06 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that {@link FileAlterationObserver#checkAndNotify()} completes
     * without error when the observed root directory does not exist on the file
     * system and no file filter is supplied.
     */
    @Test(timeout = 4000)
    public void checkAndNotifyOnNonExistentDirectoryDoesNothing() throws Throwable {
        // A directory that does not exist on the (virtual) file system.
        MockFile nonExistentDirectory = new MockFile("", "");
        FileAlterationObserver observer =
                new FileAlterationObserver(nonExistentDirectory, (FileFilter) null);

        // The root never existed, so checkAndNotify() should simply return
        // without firing any events or throwing.
        observer.checkAndNotify();
    }
}
