package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Duration;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockThaiBuddhistDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test20 extends Years_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        Years oneYear = Years.ONE;

        try {
            Duration.from(oneYear);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            verifyException("java.time.Duration", e);
        }
    }
}
