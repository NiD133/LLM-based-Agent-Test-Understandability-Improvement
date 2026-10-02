package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Period;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test22 extends Months_ESTest_scaffolding {

    /**
     * Verifies that converting a {@link Months} to a {@link Period} and back
     * via {@link Months#from(java.time.temporal.TemporalAmount)} preserves the
     * original value, so the round-tripped instance equals the original.
     */
    @Test(timeout = 4000)
    public void roundTripThroughPeriodPreservesEquality() throws Throwable {
        // 83 years equals 83 * 12 = 996 months.
        Months eightyThreeYears = Months.ofYears(83);
        assertEquals(996, eightyThreeYears.getAmount());

        // Convert to a Period and back to Months.
        Period asPeriod = Period.from(eightyThreeYears);
        Months roundTripped = Months.from(asPeriod);

        // The round trip must yield an equal amount.
        assertTrue(eightyThreeYears.equals(roundTripped));
    }
}
