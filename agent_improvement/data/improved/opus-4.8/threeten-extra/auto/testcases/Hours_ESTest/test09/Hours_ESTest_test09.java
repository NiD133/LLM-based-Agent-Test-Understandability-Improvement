package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test09 extends Hours_ESTest_scaffolding {

    /**
     * The absolute value of an already-positive amount of hours
     * should leave the amount unchanged.
     */
    @Test(timeout = 4000)
    public void abs_ofPositiveHours_returnsSameAmount() throws Throwable {
        Hours oneHour = Hours.of(1);

        Hours absoluteValue = oneHour.abs();

        assertEquals(1, absoluteValue.getAmount());
    }
}
