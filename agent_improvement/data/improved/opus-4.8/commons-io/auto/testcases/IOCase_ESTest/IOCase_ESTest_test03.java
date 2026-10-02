package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test03 extends IOCase_ESTest_scaffolding {

    /**
     * checkStartsWith is null-safe: when both the string to check and the
     * prefix are null, it returns false rather than throwing.
     */
    @Test(timeout = 4000)
    public void checkStartsWith_returnsFalse_whenBothArgumentsAreNull() throws Throwable {
        IOCase caseSensitive = IOCase.SENSITIVE;

        boolean startsWith = caseSensitive.checkStartsWith((String) null, (String) null);

        assertFalse(startsWith);
    }
}
