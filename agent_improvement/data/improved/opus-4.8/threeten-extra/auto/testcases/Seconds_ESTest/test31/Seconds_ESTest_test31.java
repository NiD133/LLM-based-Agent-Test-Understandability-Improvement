package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test31 extends Seconds_ESTest_scaffolding {

    /**
     * Subtracting an equivalent {@link Duration} from a {@code Seconds} amount
     * yields zero seconds, and the original amount stays unchanged (immutability).
     */
    @Test(timeout = 4000)
    public void subtractingEquivalentDurationGivesZero() throws Throwable {
        // 31 hours expressed in seconds: 31 * 3600 = 111600
        Seconds thirtyOneHours = Seconds.ofHours(31);
        assertEquals(111600, thirtyOneHours.getAmount());

        // A Duration holding exactly the same number of seconds.
        Duration equivalentDuration = Duration.from(thirtyOneHours);

        // Subtracting the equivalent duration leaves nothing behind.
        Seconds remainder = thirtyOneHours.minus((TemporalAmount) equivalentDuration);
        assertTrue(remainder.isZero());

        // The original amount is immutable and therefore unaffected.
        assertEquals(111600, thirtyOneHours.getAmount());
    }
}
