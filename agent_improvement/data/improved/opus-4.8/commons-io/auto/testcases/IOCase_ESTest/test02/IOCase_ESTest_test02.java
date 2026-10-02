package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test02 extends IOCase_ESTest_scaffolding {

    /**
     * checkStartsWith returns false when the start argument is null,
     * since the method is null-safe and a null start cannot be matched.
     */
    @Test(timeout = 4000)
    public void checkStartsWithNullStartReturnsFalse() throws Throwable {
        IOCase caseSensitive = IOCase.SENSITIVE;

        boolean matches = caseSensitive.checkStartsWith("", (String) null);

        assertFalse(matches);
    }
}
