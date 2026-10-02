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
public class TaiInstant_ESTest_test19 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that converting a {@link TaiInstant} to an {@link Instant} does not
     * mutate the source instant: the TAI seconds and nano-of-second remain unchanged.
     */
    @Test(timeout = 4000)
    public void toInstantLeavesTaiInstantUnchanged() throws Throwable {
        long taiSeconds = 20L;
        int nanoOfSecond = 20;
        TaiInstant taiInstant = TaiInstant.ofTaiSeconds(taiSeconds, nanoOfSecond);

        taiInstant.toInstant();

        assertEquals("TAI seconds should be unchanged", taiSeconds, taiInstant.getTaiSeconds());
        assertEquals("nano-of-second should be unchanged", nanoOfSecond, taiInstant.getNano());
    }
}
