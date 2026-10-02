package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test17 extends Years_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_yearsOne_isNotZero_andHasAmountOfOne() throws Throwable {
        Years oneYear = Years.ONE;
        assertFalse("Years.ONE should not be zero", oneYear.isZero());
        assertEquals("Years.ONE should have an amount of 1", 1, oneYear.getAmount());
    }
}
