package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test17 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that computing the duration from an instant to itself does not
     * mutate that instant (TaiInstant is immutable), so its seconds and nanos
     * remain unchanged afterwards.
     */
    @Test(timeout = 4000)
    public void durationUntilSelf_leavesInstantUnchanged() throws Throwable {
        long taiSeconds = 86400L; // one day's worth of TAI seconds
        TaiInstant instant = TaiInstant.ofTaiSeconds(taiSeconds, 0L);

        instant.durationUntil(instant);

        assertEquals(taiSeconds, instant.getTaiSeconds());
        assertEquals(0, instant.getNano());
    }
}
