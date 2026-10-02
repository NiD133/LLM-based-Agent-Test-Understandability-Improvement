package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test26 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        Temporal startInclusive = null;
        Temporal endExclusive = null;

        try {
            Seconds.between(startInclusive, endExclusive);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException expected) {
            verifyException("java.time.temporal.ChronoUnit", expected);
        }
    }
}
