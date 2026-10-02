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
     * Verifies that an observer can be initialized and run a check-and-notify
     * cycle against a non-existent directory without throwing.
     *
     * <p>The root directory passed in (an empty parent / empty child name) does
     * not exist on the virtual file system, so {@code checkAndNotify()} should
     * simply find nothing to do and complete normally.</p>
     */
    @Test(timeout = 4000)
    public void testInitializeAndCheckNotifyOnMissingDirectory() throws Throwable {
        // Root directory that does not exist on the virtual file system.
        MockFile nonExistentDirectory = new MockFile("", "");
        FileAlterationObserver observer = new FileAlterationObserver(nonExistentDirectory);

        // Capture the initial state, then check for create/change/delete events.
        observer.initialize();
        observer.checkAndNotify();
    }
}
