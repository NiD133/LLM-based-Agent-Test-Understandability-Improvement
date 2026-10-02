package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test08 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that dividedBy(1) is an identity operation: it returns the exact
     * same Minutes instance and preserves the correct minute count.
     * 13 hours * 60 minutes/hour = 780 minutes.
     */
    @Test(timeout = 4000)
    public void test_dividedByOne_returnsIdenticalInstance() throws Throwable {
        Minutes thirteenHours = Minutes.ofHours(13);

        // Dividing by 1 should be a no-op and return the same object
        Minutes result = thirteenHours.dividedBy(1);

        assertSame(result, thirteenHours);
        assertEquals(780, result.getAmount());
    }
}
