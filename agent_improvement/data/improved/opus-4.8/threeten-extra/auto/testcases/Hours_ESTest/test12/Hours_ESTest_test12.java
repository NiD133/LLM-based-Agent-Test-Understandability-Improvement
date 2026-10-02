package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test12 extends Hours_ESTest_scaffolding {

    /**
     * The number of hours between an instant and itself is zero, and
     * multiplying that zero-hour amount by a scalar of 1 leaves it unchanged.
     */
    @Test(timeout = 4000)
    public void multiplyingZeroHoursByOneStaysZero() throws Throwable {
        Instant sameMoment = MockInstant.ofEpochSecond(1137L);

        Hours zeroHours = Hours.between(sameMoment, sameMoment);
        Hours result = zeroHours.multipliedBy(1);

        assertEquals(0, result.getAmount());
    }
}
