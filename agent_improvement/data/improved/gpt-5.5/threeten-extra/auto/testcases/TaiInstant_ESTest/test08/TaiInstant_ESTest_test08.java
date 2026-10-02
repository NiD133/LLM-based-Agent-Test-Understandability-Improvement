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
public class TaiInstant_ESTest_test08 extends TaiInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        long laterTaiSecond = 86400L;
        long zeroNanoAdjustment = 0L;
        TaiInstant laterInstant = TaiInstant.ofTaiSeconds(laterTaiSecond, zeroNanoAdjustment);

        long baseTaiSecond = 883L;
        long negativeNanoAdjustment = (-784L);
        TaiInstant normalizedInstant = TaiInstant.ofTaiSeconds(baseTaiSecond, negativeNanoAdjustment);

        boolean normalizedInstantIsAfterLaterInstant = normalizedInstant.isAfter(laterInstant);

        assertEquals(999999216, normalizedInstant.getNano());
        assertEquals(882L, normalizedInstant.getTaiSeconds());
        assertFalse(normalizedInstantIsAfterLaterInstant);
    }
}
