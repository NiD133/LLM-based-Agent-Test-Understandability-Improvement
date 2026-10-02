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
public class TaiInstant_ESTest_test02 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies two behaviors of TaiInstant:
     * 1. Negative nano adjustment is normalized via floor division/modulo:
     *    ofTaiSeconds(-1, -1) -> seconds=-2, nanos=999_999_999
     *    because floorDiv(-1, 1_000_000_000) = -1, so total seconds = -1 + (-1) = -2,
     *    and floorMod(-1, 1_000_000_000) = 999_999_999.
     * 2. equals() returns false when compared to a non-TaiInstant object.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Create an instant with negative nano adjustment; the factory normalizes it
        TaiInstant instantWithNegativeNanoAdjustment = TaiInstant.ofTaiSeconds((-1L), (-1L));

        // Compare against a plain Object, which is not a TaiInstant
        Object nonTaiInstantObject = new Object();
        boolean equalsNonTaiInstant = instantWithNegativeNanoAdjustment.equals(nonTaiInstantObject);

        // Negative nano adjustment borrows from the seconds field: -1 sec + floor(-1 ns / 1e9) = -2 sec
        assertEquals((-2L), instantWithNegativeNanoAdjustment.getTaiSeconds());
        // equals() must return false for a non-TaiInstant argument
        assertFalse(equalsNonTaiInstant);
        // Remainder after floor division: floorMod(-1, 1_000_000_000) = 999_999_999
        assertEquals(999999999, instantWithNegativeNanoAdjustment.getNano());
    }
}
