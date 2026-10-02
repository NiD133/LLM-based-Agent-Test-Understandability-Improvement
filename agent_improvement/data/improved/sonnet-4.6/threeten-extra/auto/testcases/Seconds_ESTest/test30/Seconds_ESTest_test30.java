package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test30 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test30() throws Throwable {
        // Adding a negative Duration of -1032 days to ZERO should yield -1032 * 86400 = -89,164,800 seconds
        Duration negativeDuration = Duration.ofDays(-1032L);
        Seconds result = Seconds.ZERO.plus(negativeDuration);
        assertEquals(-89164800, result.getAmount());
    }
}
