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
public class TaiInstant_ESTest_test00 extends TaiInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // ofTaiSeconds normalizes negative nanoAdjustment: -3113 ns is adjusted to
        // (seconds - 1, 1_000_000_000 - 3113) = (-3114s, 999_996_887 ns)
        TaiInstant firstInstant  = TaiInstant.ofTaiSeconds(-3113L, -3113L);
        TaiInstant secondInstant = TaiInstant.ofTaiSeconds(-3113L, -3113L);

        boolean areEqual = firstInstant.equals(secondInstant);

        assertEquals("Nano-of-second should be normalized to 999996887", 999996887, secondInstant.getNano());
        assertTrue("Two TaiInstants created with identical arguments must be equal", areEqual);
        assertEquals("TAI seconds should be adjusted by -1 due to negative nano borrow", -3114L, secondInstant.getTaiSeconds());
    }
}
