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

    private static final int NEGATIVE_NANO = -377;

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        TaiInstant instant = TaiInstant.ofTaiSeconds(-377, NEGATIVE_NANO);
        try {
            instant.withNano(NEGATIVE_NANO);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // NanoOfSecond must be from 0 to 999,999,999
            //
            verifyException("org.threeten.extra.scale.TaiInstant", e);
        }
    }
}
