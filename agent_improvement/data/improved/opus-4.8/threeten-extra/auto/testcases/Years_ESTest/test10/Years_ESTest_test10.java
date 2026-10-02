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
     * The absolute value of a positive amount (one year) is the same amount.
     */
    @Test(timeout = 4000)
    public void absOfOneYearIsOneYear() throws Throwable {
        Years oneYear = Years.ONE;

        Years absoluteValue = oneYear.abs();

        assertEquals(1, absoluteValue.getAmount());
    }
}
