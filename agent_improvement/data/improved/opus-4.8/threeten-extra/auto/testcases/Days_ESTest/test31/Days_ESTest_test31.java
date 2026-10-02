package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test31 extends Days_ESTest_scaffolding {

    /**
     * The number of days between an instant and itself is zero, and adding that
     * zero-day amount to itself still yields zero days.
     */
    @Test(timeout = 4000)
    public void plusZeroDaysToItselfStaysZero() throws Throwable {
        Instant sameInstant = MockInstant.ofEpochSecond(-2086L, -2086L);

        Days zeroDays = Days.between(sameInstant, sameInstant);
        Days result = zeroDays.plus((TemporalAmount) zeroDays);

        assertTrue(result.isZero());
    }
}
