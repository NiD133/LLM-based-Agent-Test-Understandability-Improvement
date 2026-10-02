package org.threeten.extra;

import static org.junit.Assert.assertSame;

import java.time.Instant;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test06 extends Months_ESTest_scaffolding {

    /**
     * Adding a zero-month amount to a temporal must leave it untouched:
     * {@link Months#addTo(Temporal)} skips the calculation when the amount is
     * zero and returns the very same instance that was passed in.
     */
    @Test(timeout = 4000)
    public void addToWithZeroMonthsReturnsSameTemporal() throws Throwable {
        Instant originalInstant = MockInstant.now();

        Temporal adjustedInstant = Months.ZERO.addTo(originalInstant);

        assertSame(originalInstant, adjustedInstant);
    }
}
