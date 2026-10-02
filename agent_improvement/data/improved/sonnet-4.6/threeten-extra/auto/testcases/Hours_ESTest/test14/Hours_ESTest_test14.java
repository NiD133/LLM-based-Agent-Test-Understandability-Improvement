package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test14 extends Hours_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_positiveHours_isPositiveReturnsTrue() throws Throwable {
        Hours twoHours = Hours.of(2);
        boolean isPositive = twoHours.isPositive();
        assertEquals(2, twoHours.getAmount());
        assertTrue(isPositive);
    }
}
