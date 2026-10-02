package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test22 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that subtracting a day-based {@link Period} from a {@link Minutes}
     * amount converts the period to minutes (1 day = 1440 minutes) before subtracting,
     * and leaves the original instance unchanged because {@code Minutes} is immutable.
     */
    @Test(timeout = 4000)
    public void minus_periodOfDays_convertsDaysToMinutesAndSubtracts() throws Throwable {
        Minutes initialMinutes = Minutes.of(-946);
        Period minusNineHundredFortySixDays = Period.ofDays(-946);

        Minutes result = initialMinutes.minus((TemporalAmount) minusNineHundredFortySixDays);

        // Original instance is unaffected by the subtraction (immutability).
        assertEquals(-946, initialMinutes.getAmount());
        // -946 minutes - (-946 days * 1440 minutes/day) = -946 - (-1362240) = 1361294 minutes.
        assertEquals(1361294, result.getAmount());
    }
}
