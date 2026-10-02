package org.apache.commons.lang3.time;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test06 extends DurationUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link DurationUtils#toChronoUnit(TimeUnit)} maps the
     * {@link TimeUnit#NANOSECONDS} time unit to the equivalent
     * {@link ChronoUnit#NANOS} chrono unit.
     */
    @Test(timeout = 4000)
    public void toChronoUnit_givenNanoseconds_returnsNanos() throws Throwable {
        ChronoUnit result = DurationUtils.toChronoUnit(TimeUnit.NANOSECONDS);

        assertEquals(ChronoUnit.NANOS, result);
    }
}
