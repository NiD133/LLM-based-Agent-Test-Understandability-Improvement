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
public class DurationUtils_ESTest_test05 extends DurationUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05_toChronoUnit_convertsMicrosecondsToMicros() throws Throwable {
        TimeUnit microseconds = TimeUnit.MICROSECONDS;
        ChronoUnit result = DurationUtils.toChronoUnit(microseconds);
        assertEquals(ChronoUnit.MICROS, result);
    }
}
