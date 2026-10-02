package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test10 extends Hours_ESTest_scaffolding {

    /**
     * Dividing a zero-hour amount by 1 should yield zero hours.
     */
    @Test(timeout = 4000)
    public void dividingZeroHoursByOneReturnsZeroHours() throws Throwable {
        Hours zeroHours = Hours.from(Duration.ZERO);

        Hours result = zeroHours.dividedBy(1);

        assertEquals(0, result.getAmount());
    }
}
