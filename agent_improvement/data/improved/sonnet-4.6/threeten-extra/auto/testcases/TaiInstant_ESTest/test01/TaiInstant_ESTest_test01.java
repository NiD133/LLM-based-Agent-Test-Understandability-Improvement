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
public class TaiInstant_ESTest_test01 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that two TaiInstants with swapped seconds/nanoAdjustment arguments are not equal,
     * and that a negative nanoAdjustment is normalized: the excess nanos borrow one second,
     * leaving a positive nano-of-second in [0, 999_999_999].
     *
     * ofTaiSeconds(-1192, -3113):
     *   floorDiv(-3113, 1_000_000_000) = -1  → seconds = -1192 + (-1) = -1193
     *   floorMod(-3113, 1_000_000_000) = 999_996_887
     */
    @Test(timeout = 4000)
    public void test01_inequalityAndNegativeNanoNormalization() throws Throwable {
        TaiInstant instantA = TaiInstant.ofTaiSeconds((-3113L), (-1192L));
        TaiInstant instantB = TaiInstant.ofTaiSeconds((-1192L), (-3113L));

        boolean areEqual = instantA.equals(instantB);
        assertFalse(areEqual);

        // Negative nanoAdjustment borrows one whole second, leaving a positive nano-of-second.
        assertEquals(999996887, instantB.getNano());
        assertEquals((-1193L), instantB.getTaiSeconds());
    }
}
