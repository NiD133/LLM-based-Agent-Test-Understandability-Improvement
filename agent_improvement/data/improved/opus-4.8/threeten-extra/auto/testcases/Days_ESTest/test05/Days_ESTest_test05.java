package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.chrono.MinguoDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test05 extends Days_ESTest_scaffolding {

    /**
     * The number of days between a date and itself is zero.
     * Subtracting the resulting zero-day amount from that date is a no-op,
     * and the amount reports itself as zero.
     */
    @Test(timeout = 4000)
    public void betweenSameDateIsZeroDays() throws Throwable {
        MinguoDate today = MockMinguoDate.now();

        Days daysBetweenSameDate = Days.between(today, today);
        Days.ZERO.subtractFrom(today);

        assertEquals(0, daysBetweenSameDate.getAmount());
        assertTrue(daysBetweenSameDate.isZero());
    }
}
