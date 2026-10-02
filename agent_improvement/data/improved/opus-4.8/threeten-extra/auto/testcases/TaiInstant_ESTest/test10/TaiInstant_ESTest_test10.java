package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test10 extends TaiInstant_ESTest_scaffolding {

    /**
     * Subtracting a duration only changes the nanosecond fraction when the
     * subtraction stays within the current second.
     *
     * <p>The starting instant is built from 3,600,000,000,000 TAI seconds with a
     * nano adjustment of -1194. {@code ofTaiSeconds} normalises that adjustment
     * into the range 0..999,999,999 by borrowing one second, giving:
     * <ul>
     *   <li>seconds = 3,599,999,999,999</li>
     *   <li>nanos   = 999,998,806</li>
     * </ul>
     *
     * <p>Subtracting 20 milliseconds (20,000,000 nanoseconds) removes only from
     * the nanosecond fraction, so the second count is unchanged and the nanos
     * become 999,998,806 - 20,000,000 = 979,998,806.
     */
    @Test(timeout = 4000)
    public void minus_smallDuration_subtractsOnlyFromNanoFraction() throws Throwable {
        TaiInstant startInstant = TaiInstant.ofTaiSeconds(3600000000000L, -1194L);
        Duration twentyMillis = Duration.ofMillis(20);

        TaiInstant resultInstant = startInstant.minus(twentyMillis);

        assertEquals(979998806, resultInstant.getNano());
        assertEquals(3599999999999L, resultInstant.getTaiSeconds());

        // Subtraction does not mutate the original instant.
        assertEquals(3599999999999L, startInstant.getTaiSeconds());
    }
}
