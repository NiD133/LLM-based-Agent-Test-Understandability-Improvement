package org.apache.commons.io.monitor;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test10 extends FileAlterationObserver_ESTest_scaffolding {

    // addListener silently ignores null — no exception should be thrown
    @Test(timeout = 4000)
    public void test_addNullListener_doesNotThrow() throws Throwable {
        FileAlterationObserver observer = new FileAlterationObserver("");
        observer.addListener((FileAlterationListener) null);
    }
}
