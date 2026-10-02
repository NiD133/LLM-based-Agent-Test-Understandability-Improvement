package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test08 extends Days_ESTest_scaffolding {

    /**
     * Verifies that abs() returns a Days holding the magnitude of a negative
     * amount, and that the original instance is left unchanged (Days is immutable).
     */
    @Test(timeout = 4000)
    public void abs_ofNegativeDays_returnsPositiveAmountAndLeavesOriginalUnchanged() throws Throwable {
        Days negativeDays = Days.of(-2129);

        Days absoluteDays = negativeDays.abs();

        assertEquals("abs() should return the positive magnitude", 2129, absoluteDays.getAmount());
        assertEquals("original Days should be unchanged", -2129, negativeDays.getAmount());
    }
}
