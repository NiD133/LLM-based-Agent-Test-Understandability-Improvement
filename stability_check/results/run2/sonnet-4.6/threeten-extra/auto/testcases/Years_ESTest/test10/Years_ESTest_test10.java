package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test10 extends Years_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // abs() on a positive value (Years.ONE = 1) should return the same positive amount
        Years oneYear = Years.ONE;
        Years absResult = oneYear.abs();
        assertEquals(1, absResult.getAmount());
    }
}
