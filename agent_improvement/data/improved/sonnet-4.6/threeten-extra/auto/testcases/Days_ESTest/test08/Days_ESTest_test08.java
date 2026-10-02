package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test08 extends Days_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_abs_returnsPositiveValue_andOriginalRemainsNegative() throws Throwable {
        Days negativeDays = Days.of(-2129);
        Days absoluteDays = negativeDays.abs();

        assertEquals(2129, absoluteDays.getAmount());
        assertEquals(-2129, negativeDays.getAmount());
    }
}
