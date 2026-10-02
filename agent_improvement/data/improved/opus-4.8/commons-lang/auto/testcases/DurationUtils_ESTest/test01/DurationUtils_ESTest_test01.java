package org.apache.commons.lang3.time;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test01 extends DurationUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link DurationUtils#toMillisLong(Duration)} saturates to
     * {@link Long#MAX_VALUE} instead of throwing when the duration is too large
     * to be expressed in milliseconds.
     *
     * <p>{@code ChronoUnit.FOREVER} has an effectively unbounded duration, so
     * converting it to milliseconds would overflow a {@code long}. The method is
     * expected to clamp the (positive) result to {@code Long.MAX_VALUE}.</p>
     */
    @Test(timeout = 4000)
    public void toMillisLong_overflowingPositiveDuration_clampsToLongMaxValue() throws Throwable {
        Duration unboundedDuration = ChronoUnit.FOREVER.getDuration();

        long millis = DurationUtils.toMillisLong(unboundedDuration);

        assertEquals(Long.MAX_VALUE, millis);
    }
}
