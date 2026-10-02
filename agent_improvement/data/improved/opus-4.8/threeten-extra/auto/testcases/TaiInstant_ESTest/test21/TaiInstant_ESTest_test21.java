package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;
import java.time.Instant;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test21 extends TaiInstant_ESTest_scaffolding {

    /**
     * Converting the mocked "current" instant into a {@link TaiInstant} should
     * split it into the expected TAI seconds and nanosecond-of-second values.
     * The mocked clock is deterministic, so these values are fixed.
     */
    @Test(timeout = 4000)
    public void convertsCurrentInstantToTaiSecondsAndNanos() throws Throwable {
        Instant currentInstant = MockInstant.now();

        TaiInstant taiInstant = TaiInstant.of(currentInstant);

        assertEquals("nanosecond-of-second", 320000000, taiInstant.getNano());
        assertEquals("TAI seconds since 1958 epoch", 1771100516L, taiInstant.getTaiSeconds());
    }
}
