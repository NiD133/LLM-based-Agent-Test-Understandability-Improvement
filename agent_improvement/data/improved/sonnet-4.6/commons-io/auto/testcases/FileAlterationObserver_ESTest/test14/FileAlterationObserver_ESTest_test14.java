package org.apache.commons.io.monitor;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test14 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that a freshly created observer (with no listeners registered)
     * returns a non-null iterable from getListeners().
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        MockFile observedDirectory = new MockFile("", "");
        FileAlterationObserver observer = new FileAlterationObserver(observedDirectory);

        Iterable<FileAlterationListener> listeners = observer.getListeners();

        assertNotNull(listeners);
    }
}
