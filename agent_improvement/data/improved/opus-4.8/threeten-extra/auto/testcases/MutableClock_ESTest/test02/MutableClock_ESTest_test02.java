package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test02 extends MutableClock_ESTest_scaffolding {

    /**
     * Verifies that a negative amount can be added to a MutableClock,
     * effectively moving the clock backwards by the given number of units.
     */
    @Test(timeout = 4000)
    public void addNegativeAmountInDecadesMovesClockBackwards() throws Throwable {
        MutableClock clock = MutableClock.epochUTC();

        long amountToAdd = -1L;
        TemporalUnit unit = ChronoUnit.DECADES;
        clock.add(amountToAdd, unit);
    }
}
