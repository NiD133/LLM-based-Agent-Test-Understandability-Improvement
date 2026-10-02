package org.apache.commons.io.monitor;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.testdata.EvoSuiteFile;
import org.evosuite.runtime.testdata.FileSystemHandling;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test15 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that checkAndNotify() is safe to call both before and after the
     * observed directory is created. On the first call the directory does not
     * exist, so the observer should silently skip file-system traversal. After
     * the directory is created in the virtual file system the second call
     * should traverse it without throwing.
     */
    @Test(timeout = 4000)
    public void test_checkAndNotify_doesNotThrow_whenObservedDirectoryTransitionsFromNonExistentToExisting() throws Throwable {
        // Observe a virtual directory that does not exist yet
        MockFile nonExistentDirectory = new MockFile("", "");
        FileAlterationObserver observer = new FileAlterationObserver(nonExistentDirectory);

        // First check: directory is absent — observer should handle this silently
        observer.checkAndNotify();

        // Create the directory in the virtual file system
        EvoSuiteFile virtualDirectory = new EvoSuiteFile("/Users/tenghaha/Desktop");
        FileSystemHandling.createFolder(virtualDirectory);

        // Second check: directory now exists — observer should traverse it without error
        observer.checkAndNotify();
    }
}
