package org.apache.commons.io.monitor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test17 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that {@link FileAlterationObserver#destroy()} completes without
     * error on a freshly constructed observer. Because {@code destroy()} is a
     * no-op by default, calling it before the observer has been initialized
     * should not throw.
     */
    @Test(timeout = 4000)
    public void destroyOnUninitializedObserverDoesNotThrow() throws Throwable {
        String directoryName = " to a subdirectory of itself: ";
        FileAlterationObserver observer = new FileAlterationObserver(directoryName);

        observer.destroy();
    }
}
