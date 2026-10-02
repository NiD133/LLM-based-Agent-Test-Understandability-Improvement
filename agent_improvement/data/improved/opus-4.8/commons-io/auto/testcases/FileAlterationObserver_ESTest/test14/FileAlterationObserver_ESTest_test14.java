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
     * A freshly constructed observer (with no listeners registered) should still
     * return a non-null, empty-but-iterable collection from {@link FileAlterationObserver#getListeners()}.
     */
    @Test(timeout = 4000)
    public void getListenersReturnsNonNullIterableWhenNoListenersRegistered() throws Throwable {
        MockFile directoryToObserve = new MockFile("", "");
        FileAlterationObserver observer = new FileAlterationObserver(directoryToObserve);

        Iterable<FileAlterationListener> listeners = observer.getListeners();

        assertNotNull(listeners);
    }
}
