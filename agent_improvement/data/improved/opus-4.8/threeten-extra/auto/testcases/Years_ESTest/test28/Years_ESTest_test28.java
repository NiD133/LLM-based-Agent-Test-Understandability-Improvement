package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test28 extends Years_ESTest_scaffolding {

    /**
     * Verifies that the ZERO constant renders as the ISO-8601 period "P0Y".
     */
    @Test(timeout = 4000)
    public void zeroToStringReturnsP0Y() throws Throwable {
        String text = Years.ZERO.toString();

        assertEquals("P0Y", text);
    }
}
