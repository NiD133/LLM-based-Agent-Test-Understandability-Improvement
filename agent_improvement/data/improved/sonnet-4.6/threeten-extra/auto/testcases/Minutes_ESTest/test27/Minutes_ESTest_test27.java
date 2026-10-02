package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test27 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that converting the ZERO minutes constant to a Duration produces a valid (non-null) Duration object.
     */
    @Test(timeout = 4000)
    public void test_toDuration_withZeroMinutes_returnsNonNullDuration() throws Throwable {
        Minutes zeroMinutes = Minutes.ZERO;
        Duration duration = zeroMinutes.toDuration();
        assertNotNull(duration);
    }
}
