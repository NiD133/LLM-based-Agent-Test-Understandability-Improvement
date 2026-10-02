package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test22 extends TaiInstant_ESTest_scaffolding {

    /**
     * When the nanosecond adjustment is already within the valid range
     * (0 to 999,999,999), {@code ofTaiSeconds} stores the seconds and
     * nanoseconds unchanged, and {@code hashCode} can be computed without error.
     */
    @Test(timeout = 4000)
    public void ofTaiSeconds_keepsInRangeValues_andComputesHashCode() throws Throwable {
        long taiSeconds = 634L;
        long nanoAdjustment = 634L;

        TaiInstant instant = TaiInstant.ofTaiSeconds(taiSeconds, nanoAdjustment);

        // hashCode must execute without throwing
        instant.hashCode();

        assertEquals(634, instant.getNano());
        assertEquals(634L, instant.getTaiSeconds());
    }
}
