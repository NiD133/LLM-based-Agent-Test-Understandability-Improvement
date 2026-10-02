package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test17 extends TaiInstant_ESTest_scaffolding {

    private static final long SECONDS_PER_DAY = 86400L;

    @Test(timeout = 4000)
    public void test_durationUntilSelf_leavesInstantUnchanged() throws Throwable {
        TaiInstant instant = TaiInstant.ofTaiSeconds(SECONDS_PER_DAY, 0L);

        // Calling durationUntil with the same instant as argument; the instant is immutable
        instant.durationUntil(instant);

        assertEquals(SECONDS_PER_DAY, instant.getTaiSeconds());
        assertEquals(0, instant.getNano());
    }
}
