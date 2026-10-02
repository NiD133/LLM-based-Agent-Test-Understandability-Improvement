package org.apache.commons.lang3.time;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test07 extends DurationUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_isPositive_returnsFalse_whenDurationIsNegative() throws Throwable {
        Duration negativeDuration = Duration.ofSeconds(-105L, -105L);
        boolean result = DurationUtils.isPositive(negativeDuration);
        assertFalse(result);
    }
}
