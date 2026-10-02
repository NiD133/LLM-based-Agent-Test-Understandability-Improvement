package org.apache.commons.lang3.time;

import static org.junit.Assert.assertEquals;

import java.time.Duration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test00 extends DurationUtils_ESTest_scaffolding {

    /**
     * When the given system property key does not exist, {@link DurationUtils#getMillis(String, long)}
     * falls back to the supplied default (here {@link Long#MIN_VALUE}) and builds a Duration from it.
     * {@link DurationUtils#toMillisLong(Duration)} then converts that Duration back to the same
     * millisecond value without overflowing.
     */
    @Test(timeout = 4000)
    public void getMillisFallsBackToDefaultAndConvertsBackToMillis() throws Throwable {
        final String missingPropertyKey = "g\"*8?I";
        final long defaultMillis = Long.MIN_VALUE;

        final Duration duration = DurationUtils.getMillis(missingPropertyKey, defaultMillis);
        final long actualMillis = DurationUtils.toMillisLong(duration);

        assertEquals(defaultMillis, actualMillis);
    }
}
