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
public class TaiInstant_ESTest_test06 extends TaiInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        long taiSeconds = 20L;
        int nanosOfSecond = 20;

        TaiInstant instant = TaiInstant.ofTaiSeconds(taiSeconds, nanosOfSecond);
        boolean isBeforeItself = instant.isBefore(instant);

        assertEquals(20L, instant.getTaiSeconds());
        assertFalse(isBeforeItself);
        assertEquals(20, instant.getNano());
    }
}
