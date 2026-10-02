package org.apache.commons.lang3.time;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test00 extends DurationUtils_ESTest_scaffolding {

    /**
     * Verifies that getMillis with an absent system property key falls back to the given default (Long.MIN_VALUE),
     * and that toMillisLong round-trips that Duration back to Long.MIN_VALUE without overflow.
     */
    @Test(timeout = 4000)
    public void test_getMillisWithAbsentKeyUsesDefault_andToMillisLongRoundTrips() throws Throwable {
        // "g\"*8?I" is not a real system property, so getMillis falls back to Long.MIN_VALUE
        Duration durationFromMinLong = DurationUtils.getMillis("g\"*8?I", Long.MIN_VALUE);

        long actualMillis = DurationUtils.toMillisLong(durationFromMinLong);

        assertEquals(Long.MIN_VALUE, actualMillis);
    }
}
