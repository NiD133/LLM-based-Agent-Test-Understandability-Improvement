package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test14 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that {@link TaiInstant#withNano(int)} rejects a negative
     * nano-of-second. The valid range is 0 to 999,999,999, so a negative
     * value must trigger an IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void withNano_negativeValue_throwsIllegalArgumentException() throws Throwable {
        TaiInstant instant = TaiInstant.ofTaiSeconds(-377L, -377L);
        int negativeNanoOfSecond = -377;

        try {
            instant.withNano(negativeNanoOfSecond);
            fail("Expected IllegalArgumentException for negative nano-of-second");
        } catch (IllegalArgumentException e) {
            // Message: "NanoOfSecond must be from 0 to 999,999,999"
            verifyException("org.threeten.extra.scale.TaiInstant", e);
        }
    }
}
