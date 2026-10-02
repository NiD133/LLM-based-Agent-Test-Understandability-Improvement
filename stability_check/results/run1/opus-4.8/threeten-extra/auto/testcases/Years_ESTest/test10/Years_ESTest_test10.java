package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test10 extends Years_ESTest_scaffolding {

    /**
     * The absolute value of a positive amount (ONE = 1 year) is the amount itself.
     */
    @Test(timeout = 4000)
    public void absOfOneYearReturnsOneYear() throws Throwable {
        Years absoluteValue = Years.ONE.abs();

        assertEquals(1, absoluteValue.getAmount());
    }
}
