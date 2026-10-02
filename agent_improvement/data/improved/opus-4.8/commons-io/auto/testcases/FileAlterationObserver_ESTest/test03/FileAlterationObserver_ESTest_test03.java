package org.apache.commons.io.monitor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test03 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Removing a {@code null} listener should be a no-op that completes without
     * throwing, since {@link FileAlterationObserver#removeListener} ignores null.
     */
    @Test(timeout = 4000)
    public void removeNullListenerIsNoOp() throws Throwable {
        FileAlterationObserver observer = new FileAlterationObserver("");

        observer.removeListener((FileAlterationListener) null);
    }
}
