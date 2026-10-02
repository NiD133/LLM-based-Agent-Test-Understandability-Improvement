package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test17 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that checkEndsWith returns false when the "end" argument is null,
     * as documented: the method is null-safe and reports no match for null input.
     */
    @Test(timeout = 4000)
    public void checkEndsWithNullSuffixReturnsFalse() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;

        boolean endsWith = systemCase.checkEndsWith("%pe;MQhIB_m,ru(g&", (String) null);

        assertFalse(endsWith);
    }
}
