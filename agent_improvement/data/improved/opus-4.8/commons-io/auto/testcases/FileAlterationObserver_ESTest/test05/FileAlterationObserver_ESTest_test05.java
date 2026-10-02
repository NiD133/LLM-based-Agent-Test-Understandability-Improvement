package org.apache.commons.io.monitor;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test05 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that an observer created for the current directory (".") can be
     * initialized and then checked without throwing. Since no listeners are
     * registered, checkAndNotify() simply walks the directory tree and fires no
     * events, completing normally.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // Observe the current directory.
        MockFile currentDirectory = new MockFile(".", ".");
        FileAlterationObserver observer = new FileAlterationObserver(currentDirectory);

        // Capture the initial state of the directory tree.
        observer.initialize();

        // Re-scan and notify listeners; with no listeners this is a no-op scan.
        observer.checkAndNotify();
    }
}
