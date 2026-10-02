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

    /**
     * Verifies that subtracting a duration from a TaiInstant correctly adjusts
     * both the nanosecond field and the seconds field.
     *
     * The base instant is created with a negative nano adjustment (-1194 ns), which
     * causes normalization: seconds become 3599999999999 and nanos become 999998806.
     * Subtracting 20 ms (20,000,000 ns) reduces only the nano field, yielding 979998806 ns
     * while the seconds component remains unchanged at 3599999999999.
     */
    @Test(timeout = 4000)
    public void testMinusReducesNanoFieldAfterNegativeNanoNormalization() throws Throwable {
        // -1194 ns normalizes: seconds = 3600000000000 - 1 = 3599999999999, nanos = 999998806
        TaiInstant baseInstant = TaiInstant.ofTaiSeconds(3600000000000L, (-1194L));
        Duration twentyMillis = Duration.ofMillis(20);

        TaiInstant resultInstant = baseInstant.minus(twentyMillis);

        // Subtracting 20 ms (20,000,000 ns) from 999,998,806 ns stays within the same second
        assertEquals(979998806, resultInstant.getNano());
        assertEquals(3599999999999L, baseInstant.getTaiSeconds());
        assertEquals(3599999999999L, resultInstant.getTaiSeconds());
    }
}
