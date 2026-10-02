package org.apache.commons.lang3;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test6 extends CharSet_ESTest_scaffolding {

    /**
     * Passing a null String array to getInstance yields the shared EMPTY CharSet,
     * and calling hashCode() on that empty set must not throw.
     */
    @Test(timeout = 4000)
    public void getInstanceWithNullArrayThenHashCodeDoesNotThrow() throws Throwable {
        CharSet emptyCharSet = CharSet.getInstance((String[]) null);
        emptyCharSet.hashCode();
    }
}
