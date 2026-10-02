package org.threeten.extra;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test27 extends Years_ESTest_scaffolding {

    /**
     * Verifies that calling hashCode() on the shared Years.ONE constant
     * executes without throwing.
     */
    @Test(timeout = 4000)
    public void hashCodeOnOneYear_doesNotThrow() throws Throwable {
        Years oneYear = Years.ONE;

        oneYear.hashCode();
    }
}
