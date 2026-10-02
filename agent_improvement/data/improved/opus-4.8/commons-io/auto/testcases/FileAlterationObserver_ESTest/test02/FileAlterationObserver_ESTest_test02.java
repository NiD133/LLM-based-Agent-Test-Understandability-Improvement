package org.apache.commons.io.monitor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test02 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Removing a listener that was never registered should be a harmless no-op
     * and must not throw.
     */
    @Test(timeout = 4000)
    public void removeListenerThatWasNeverAddedDoesNothing() throws Throwable {
        MockFile rootDirectory = new MockFile("", "");
        FileAlterationObserver observer = new FileAlterationObserver(rootDirectory);
        FileAlterationListenerAdaptor unregisteredListener = new FileAlterationListenerAdaptor();

        observer.removeListener(unregisteredListener);
    }
}
