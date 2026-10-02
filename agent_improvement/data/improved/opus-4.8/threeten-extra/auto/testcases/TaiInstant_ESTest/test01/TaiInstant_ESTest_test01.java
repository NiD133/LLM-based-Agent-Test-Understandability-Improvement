package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test01 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that {@code ofTaiSeconds} normalises a negative nanosecond adjustment by
     * borrowing one second, and that two instants built from swapped (seconds, nanos)
     * arguments are not considered equal.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Same pair of numbers, but passed in opposite order, so the two instants differ.
        TaiInstant firstInstant = TaiInstant.ofTaiSeconds(-3113L, -1192L);
        TaiInstant secondInstant = TaiInstant.ofTaiSeconds(-1192L, -3113L);

        assertFalse("Instants built from swapped arguments must not be equal",
                firstInstant.equals(secondInstant));

        // For secondInstant: a nano adjustment of -3113 borrows a whole second,
        // so the second drops from -1192 to -1193 and the nanos wrap to 1e9 - 3113.
        assertEquals(999996887, secondInstant.getNano());
        assertEquals(-1193L, secondInstant.getTaiSeconds());
    }
}
