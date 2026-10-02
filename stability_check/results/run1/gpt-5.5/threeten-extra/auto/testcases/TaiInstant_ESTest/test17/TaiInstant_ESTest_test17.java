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
public class TaiInstant_ESTest_test17 extends TaiInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        TaiInstant oneDayAfterTaiEpoch = TaiInstant.ofTaiSeconds(86400L, 0L);

        oneDayAfterTaiEpoch.durationUntil(oneDayAfterTaiEpoch);

        assertEquals(86400L, oneDayAfterTaiEpoch.getTaiSeconds());
        assertEquals(0, oneDayAfterTaiEpoch.getNano());
    }
}
