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

    @Test(timeout = 4000)
    public void test04_toChronoUnit_convertsSecondsTimeUnitToChronoSeconds() throws Throwable {
        TimeUnit timeUnit0 = TimeUnit.SECONDS;
        ChronoUnit chronoUnit0 = DurationUtils.toChronoUnit(timeUnit0);
        assertEquals(ChronoUnit.SECONDS, chronoUnit0);
    }
}
