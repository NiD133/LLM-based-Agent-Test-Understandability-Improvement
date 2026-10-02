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
     * Dividing a Minutes value by 1 should leave it unchanged: the method
     * returns the very same instance, and the minute count stays the same.
     */
    @Test(timeout = 4000)
    public void dividingByOneReturnsSameInstance() throws Throwable {
        // 13 hours == 780 minutes
        Minutes thirteenHours = Minutes.ofHours(13);

        Minutes result = thirteenHours.dividedBy(1);

        assertSame("dividing by 1 should return the same instance", result, thirteenHours);
        assertEquals("13 hours should equal 780 minutes", 780, result.getAmount());
    }
}
