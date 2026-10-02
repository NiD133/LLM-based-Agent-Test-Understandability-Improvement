package org.apache.commons.io.monitor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test10 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that {@link FileAlterationObserver#addListener(FileAlterationListener)}
     * tolerates a {@code null} listener: per the implementation it simply ignores
     * {@code null} (no listener is registered) and completes without throwing.
     */
    @Test(timeout = 4000)
    public void addListener_withNullListener_isIgnoredAndDoesNotThrow() throws Throwable {
        FileAlterationObserver observer = new FileAlterationObserver("");

        observer.addListener((FileAlterationListener) null);
    }
}
