package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test27 extends Minutes_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void toDuration_ofZeroMinutes_returnsNonNullDuration() throws Throwable {
        Minutes zeroMinutes = Minutes.ZERO;

        Duration duration = zeroMinutes.toDuration();

        assertNotNull(duration);
    }
}
