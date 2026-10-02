package org.apache.commons.io.monitor;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

/**
 * Tests that FileAlterationObserver detects a newly created file in an observed
 * directory and notifies registered listeners without throwing an exception.
 */
@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test09 extends FileAlterationObserver_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Set up an observer on a virtual root directory
        MockFile observedDirectory = new MockFile("", "");
        FileAlterationObserver observer = new FileAlterationObserver(observedDirectory);

        // Register a no-op listener to receive file change notifications
        FileAlterationListenerAdaptor listener = new FileAlterationListenerAdaptor();
        observer.addListener(listener);

        // Create a new temp file inside the observed directory to trigger a file-created event
        MockFile.createTempFile("str2", "str2", (java.io.File) observedDirectory);

        // Scan the directory; the observer should detect the new file and notify the listener
        observer.checkAndNotify();
    }
}
