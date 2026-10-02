package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test23 extends Days_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23_fromDurationThrowsWhenNotConvertibleToWholeDays() throws Throwable {
        // 1927 seconds cannot be expressed as a whole number of days,
        // so Days.from() must reject it with DateTimeException.
        Duration durationInSeconds = Duration.ofSeconds(1927L);
        try {
            Days.from(durationInSeconds);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("org.threeten.extra.Days", e);
        }
    }
}
