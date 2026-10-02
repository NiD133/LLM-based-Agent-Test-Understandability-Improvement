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
public class TaiInstant_ESTest_test11 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that ofTaiSeconds normalizes negative nano adjustments by borrowing from the
     * seconds field, and that adding Duration.ZERO returns the exact same instance.
     *
     * ofTaiSeconds(-10, -10ns):
     *   floorDiv(-10, 1_000_000_000) = -1  → seconds = -10 + (-1) = -11
     *   floorMod(-10, 1_000_000_000) = 999_999_990 → nanos = 999_999_990
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Create an instant with a negative nano adjustment; the factory normalizes it
        // so that nanos stay in [0, 999_999_999] by borrowing one second.
        TaiInstant instantWithNegativeNanoAdjustment = TaiInstant.ofTaiSeconds(-10L, -10L);

        // Adding zero duration must return the exact same instance (identity shortcut)
        TaiInstant resultAfterAddingZero = instantWithNegativeNanoAdjustment.plus(Duration.ZERO);
        assertSame(resultAfterAddingZero, instantWithNegativeNanoAdjustment);

        // Borrowing 1s from -10s gives -11s, and -10ns mod 1_000_000_000 gives 999_999_990ns
        assertEquals(-11L, resultAfterAddingZero.getTaiSeconds());
        assertEquals(999999990, resultAfterAddingZero.getNano());
    }
}
