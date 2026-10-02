package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test19 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that {@link IOCase#checkEndsWith(String, String)} returns
     * {@code false} when both the string and the suffix are {@code null},
     * since the method is documented to return {@code false} for any null input.
     */
    @Test(timeout = 4000)
    public void checkEndsWithReturnsFalseForNullInputs() throws Throwable {
        IOCase caseSensitive = IOCase.SENSITIVE;

        boolean endsWith = caseSensitive.checkEndsWith((String) null, (String) null);

        assertFalse(endsWith);
    }
}
