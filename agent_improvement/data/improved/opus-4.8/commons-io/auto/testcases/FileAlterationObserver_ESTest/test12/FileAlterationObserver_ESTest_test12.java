package org.apache.commons.io.monitor;

import static org.junit.Assert.assertSame;

import org.apache.commons.io.IOCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test12 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that {@link FileAlterationObserver.Builder#setIOCase(IOCase)} follows the
     * fluent builder contract by returning the same builder instance it was invoked on,
     * allowing calls to be chained.
     */
    @Test(timeout = 4000)
    public void setIOCaseReturnsSameBuilderInstance() throws Throwable {
        FileAlterationObserver.Builder builder = FileAlterationObserver.builder();

        FileAlterationObserver.Builder returnedBuilder = builder.setIOCase(IOCase.SENSITIVE);

        assertSame(builder, returnedBuilder);
    }
}
