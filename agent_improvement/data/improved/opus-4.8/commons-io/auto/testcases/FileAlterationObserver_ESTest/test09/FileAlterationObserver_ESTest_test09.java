package org.apache.commons.io.monitor;

import org.junit.Test;
import java.io.File;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test09 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that checkAndNotify() runs without error when a newly created
     * temporary file exists under the observed root directory and a listener
     * is registered.
     */
    @Test(timeout = 4000)
    public void checkAndNotifyWithRegisteredListenerSucceeds() throws Throwable {
        MockFile rootDirectory = new MockFile("", "");
        FileAlterationObserver observer = new FileAlterationObserver(rootDirectory);

        FileAlterationListenerAdaptor listener = new FileAlterationListenerAdaptor();
        observer.addListener(listener);

        MockFile.createTempFile("str2", "str2", (File) rootDirectory);

        observer.checkAndNotify();
    }
}
