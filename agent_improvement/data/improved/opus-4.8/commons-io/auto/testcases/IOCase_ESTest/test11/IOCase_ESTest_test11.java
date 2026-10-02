package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test11 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that case-insensitive {@link IOCase#checkEquals} returns false
     * for two clearly different strings.
     */
    @Test(timeout = 4000)
    public void checkEqualsReturnsFalseForDifferentStrings() throws Throwable {
        IOCase insensitive = IOCase.INSENSITIVE;

        boolean areEqual = insensitive.checkEquals("%Te;M@?B_m,ru(g&", "$VALUES");

        assertFalse(areEqual);
    }
}
