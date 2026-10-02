package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test30 extends Minutes_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_abs_ofNegativeHours_returnsPositiveEquivalent() throws Throwable {
        // -8 hours = -480 minutes; abs() should return +480 without changing the original
        Minutes negativeMinutes = Minutes.ofHours(-8);
        Minutes absoluteMinutes = negativeMinutes.abs();

        assertEquals(480, absoluteMinutes.getAmount());
        assertEquals(-480, negativeMinutes.getAmount());
    }
}
