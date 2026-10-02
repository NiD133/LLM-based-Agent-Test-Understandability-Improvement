package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test18 extends TaiInstant_ESTest_scaffolding {

    /**
     * Converting a TAI instant to a UTC instant should preserve the
     * nano-of-day, accounting for the fixed TAI-to-UTC offset.
     */
    @Test(timeout = 4000)
    public void toUtcInstant_preservesNanoOfDay() throws Throwable {
        TaiInstant taiInstant = TaiInstant.ofTaiSeconds(25L, 25L);

        UtcInstant utcInstant = taiInstant.toUtcInstant();

        assertEquals(15000000025L, utcInstant.getNanoOfDay());
    }
}
