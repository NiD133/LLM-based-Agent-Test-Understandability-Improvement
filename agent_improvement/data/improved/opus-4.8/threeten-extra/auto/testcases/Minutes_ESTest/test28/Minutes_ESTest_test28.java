package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test28 extends Minutes_ESTest_scaffolding {

    /**
     * Adding a zero-minute amount to {@code Minutes.ZERO} should return the
     * {@code ZERO} singleton itself, not a new equal instance.
     */
    @Test(timeout = 4000)
    public void plusZeroMinutesToZeroReturnsZeroSingleton() throws Throwable {
        // The difference between an instant and itself is zero minutes.
        OffsetDateTime sameInstant = MockOffsetDateTime.now();
        Minutes zeroMinutes = Minutes.between(sameInstant, sameInstant);

        // ZERO + (zero minutes) keeps the immutable singleton unchanged.
        Minutes result = Minutes.ZERO.plus((TemporalAmount) zeroMinutes);

        assertSame(zeroMinutes, result);
    }
}
