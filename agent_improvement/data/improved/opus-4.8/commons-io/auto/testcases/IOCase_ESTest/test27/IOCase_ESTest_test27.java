package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test27 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that the null-safe {@link IOCase#isCaseSensitive(IOCase)} helper
     * reports {@code false} for the INSENSITIVE constant, which is defined as
     * case-insensitive.
     */
    @Test(timeout = 4000)
    public void isCaseSensitiveReturnsFalseForInsensitiveConstant() throws Throwable {
        boolean caseSensitive = IOCase.isCaseSensitive(IOCase.INSENSITIVE);

        assertFalse(caseSensitive);
    }
}
