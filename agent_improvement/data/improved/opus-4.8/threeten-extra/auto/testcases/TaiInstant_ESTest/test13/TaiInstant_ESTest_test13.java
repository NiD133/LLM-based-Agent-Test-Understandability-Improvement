package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test13 extends TaiInstant_ESTest_scaffolding {

    /**
     * The nano-of-second must lie in the range 0 to 999,999,999 (one full second
     * is 1,000,000,000 nanoseconds). Calling {@code withNano} with a value equal
     * to one whole second is out of range and must throw IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void withNano_rejectsValueEqualToOneSecond() throws Throwable {
        int oneSecondInNanos = 1_000_000_000;
        TaiInstant instant = TaiInstant.ofTaiSeconds(1_000_000_000L, 1_000_000_000L);

        try {
            instant.withNano(oneSecondInNanos);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "NanoOfSecond must be from 0 to 999,999,999"
            verifyException("org.threeten.extra.scale.TaiInstant", e);
        }
    }
}
