package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test03 extends Weeks_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_equals_returnsFalse_whenComparingToNonWeeksObject() throws Throwable {
        Weeks zeroWeeks = Weeks.ZERO;
        Object plainObject = new Object();
        boolean areEqual = zeroWeeks.equals(plainObject);
        assertFalse(areEqual);
    }
}
