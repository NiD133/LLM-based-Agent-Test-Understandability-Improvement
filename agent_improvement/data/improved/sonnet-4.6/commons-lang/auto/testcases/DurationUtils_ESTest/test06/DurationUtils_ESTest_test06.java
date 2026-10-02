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

    @Test(timeout = 4000)
    public void test_toChronoUnit_nanoseconds_mapsToChronoNanos() throws Throwable {
        ChronoUnit result = DurationUtils.toChronoUnit(TimeUnit.NANOSECONDS);
        assertEquals(ChronoUnit.NANOS, result);
    }
}
