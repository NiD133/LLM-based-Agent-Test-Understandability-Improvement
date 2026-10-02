package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test30 extends Hours_ESTest_scaffolding {

    /**
     * Converting the zero-hours amount to a Duration should yield a non-null Duration.
     */
    @Test(timeout = 4000)
    public void toDuration_onZeroHours_returnsNonNullDuration() throws Throwable {
        Hours zeroHours = Hours.ZERO;

        Duration duration = zeroHours.toDuration();

        assertNotNull(duration);
    }
}
