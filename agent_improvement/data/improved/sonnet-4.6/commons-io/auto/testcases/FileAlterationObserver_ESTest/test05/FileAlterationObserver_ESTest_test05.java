package org.apache.commons.io.monitor;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test05 extends FileAlterationObserver_ESTest_scaffolding {

    // Verifies that an observer targeting the current directory can be initialized
    // and perform a change-check without throwing any exception.
    @Test(timeout = 4000)
    public void test_initializeAndCheckAndNotify_onCurrentDirectory_doesNotThrow() throws Throwable {
        MockFile currentDirectory = new MockFile(".", ".");
        FileAlterationObserver observer = new FileAlterationObserver(currentDirectory);
        observer.initialize();
        observer.checkAndNotify();
    }
}
