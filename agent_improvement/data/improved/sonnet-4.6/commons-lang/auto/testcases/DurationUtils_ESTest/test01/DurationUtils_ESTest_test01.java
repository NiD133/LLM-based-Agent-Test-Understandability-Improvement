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

    // ChronoUnit.FOREVER has a duration that overflows long milliseconds,
    // so toMillisLong should return Long.MAX_VALUE instead of throwing.
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Duration foreverDuration = ChronoUnit.FOREVER.getDuration();
        long millis = DurationUtils.toMillisLong(foreverDuration);
        assertEquals(Long.MAX_VALUE, millis);
    }
}
