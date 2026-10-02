package org.apache.commons.io.monitor;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.testdata.EvoSuiteFile;
import org.evosuite.runtime.testdata.FileSystemHandling;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test08 extends FileAlterationObserver_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        MockFile observedPath = new MockFile("", "");
        FileAlterationObserver observer = new FileAlterationObserver(observedPath);

        observer.initialize();
        observer.checkAndNotify();
    }
}
