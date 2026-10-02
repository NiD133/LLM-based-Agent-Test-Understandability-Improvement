package org.apache.commons.lang3.time;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test13 extends DurationUtils_ESTest_scaffolding {

    /**
     * When the given system property key does not exist, getMillis falls back to the
     * default value (3831 ms). The resulting Duration must be positive (> 0).
     */
    @Test(timeout = 4000)
    public void test_getMillis_withAbsentPropertyKey_returnsPositiveDurationFromDefault() throws Throwable {
        Duration durationFromDefault = DurationUtils.getMillis("ctJ#Rib]z0+G8", 3831L);
        boolean isDurationPositive = DurationUtils.isPositive(durationFromDefault);
        assertTrue(isDurationPositive);
    }
}
