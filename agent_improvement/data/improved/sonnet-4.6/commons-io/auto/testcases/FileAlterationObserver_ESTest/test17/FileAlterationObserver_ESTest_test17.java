package org.apache.commons.io.monitor;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test17 extends FileAlterationObserver_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_destroyDoesNotThrow_whenObserverCreatedWithDirectoryName() throws Throwable {
        FileAlterationObserver observer = new FileAlterationObserver(" to a subdirectory of itself: ");
        observer.destroy();
    }
}
