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
public class TaiInstant_ESTest_test10 extends TaiInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        long taiSeconds = 3600000000000L;
        long negativeNanoAdjustment = (-1194L);
        TaiInstant originalInstant = TaiInstant.ofTaiSeconds(taiSeconds, negativeNanoAdjustment);

        Duration durationToSubtract = Duration.ofMillis(20);
        TaiInstant adjustedInstant = originalInstant.minus(durationToSubtract);

        assertEquals(979998806, adjustedInstant.getNano());
        assertEquals(3599999999999L, originalInstant.getTaiSeconds());
        assertEquals(3599999999999L, adjustedInstant.getTaiSeconds());
    }
}
