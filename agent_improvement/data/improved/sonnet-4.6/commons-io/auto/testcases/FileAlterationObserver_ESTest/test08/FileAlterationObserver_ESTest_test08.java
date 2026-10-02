package org.apache.commons.io.monitor;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test08 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that an observer initialized on an empty-path MockFile can call
     * checkAndNotify() without throwing an exception — exercising the path where
     * the observed directory does not exist on the virtual file system.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        MockFile emptyPathFile = new MockFile("", "");
        FileAlterationObserver observer = new FileAlterationObserver(emptyPathFile);
        observer.initialize();
        observer.checkAndNotify();
    }
}
