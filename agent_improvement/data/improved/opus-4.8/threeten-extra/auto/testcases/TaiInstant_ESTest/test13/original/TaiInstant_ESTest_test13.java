package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test13 extends TaiInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        TaiInstant taiInstant0 = TaiInstant.ofTaiSeconds(1000000000, 1000000000);
        // Undeclared exception!
        try {
            taiInstant0.withNano(1000000000);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // NanoOfSecond must be from 0 to 999,999,999
            //
            verifyException("org.threeten.extra.scale.TaiInstant", e);
        }
    }
}
