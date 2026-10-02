package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.OffsetDateTime;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test05 extends Hours_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_subtractFrom_withZeroHours_returnsSameTemporalObject() throws Throwable {
        // Subtracting zero hours from a temporal is a no-op: the same object is returned unchanged.
        Hours zeroHours = Hours.ZERO;
        OffsetDateTime now = MockOffsetDateTime.now();

        Temporal result = zeroHours.subtractFrom(now);

        assertSame(now, result);
    }
}
