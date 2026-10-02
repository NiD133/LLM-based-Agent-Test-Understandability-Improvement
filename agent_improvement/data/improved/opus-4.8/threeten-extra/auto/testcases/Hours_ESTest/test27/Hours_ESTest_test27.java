package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test27 extends Hours_ESTest_scaffolding {

    /**
     * Adding a Hours amount to itself doubles the amount:
     * 2 hours plus 2 hours yields 4 hours, and the original 2 hours stays positive.
     */
    @Test(timeout = 4000)
    public void plus_twoHoursToItself_yieldsFourHours() throws Throwable {
        Hours twoHours = Hours.of(2);

        Hours fourHours = twoHours.plus((TemporalAmount) twoHours);

        assertTrue(twoHours.isPositive());
        assertEquals(4, fourHours.getAmount());
    }
}
