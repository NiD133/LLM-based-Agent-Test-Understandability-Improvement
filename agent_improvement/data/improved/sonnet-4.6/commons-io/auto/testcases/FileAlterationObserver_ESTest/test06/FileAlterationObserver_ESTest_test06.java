package org.apache.commons.io.monitor;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.File;
import java.io.FileFilter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test06 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that checkAndNotify() completes without error when the observed
     * directory does not exist and no file filter is provided.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // A MockFile with empty parent and empty name represents a non-existent path
        MockFile nonExistentDirectory = new MockFile("", "");
        // null filter is accepted by the constructor (treated as TrueFileFilter internally)
        FileAlterationObserver observer = new FileAlterationObserver(nonExistentDirectory, (FileFilter) null);
        // checkAndNotify() should handle a non-existent root directory gracefully
        observer.checkAndNotify();
    }
}
