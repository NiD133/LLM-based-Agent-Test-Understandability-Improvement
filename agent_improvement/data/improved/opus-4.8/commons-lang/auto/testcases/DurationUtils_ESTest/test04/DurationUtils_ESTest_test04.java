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
public class DurationUtils_ESTest_test04 extends DurationUtils_ESTest_scaffolding {

    /**
     * Verifies that converting the {@link TimeUnit#SECONDS} time unit yields the
     * corresponding {@link ChronoUnit#SECONDS} chrono unit.
     */
    @Test(timeout = 4000)
    public void toChronoUnit_withSeconds_returnsChronoSeconds() throws Throwable {
        ChronoUnit result = DurationUtils.toChronoUnit(TimeUnit.SECONDS);

        assertEquals(ChronoUnit.SECONDS, result);
    }
}
