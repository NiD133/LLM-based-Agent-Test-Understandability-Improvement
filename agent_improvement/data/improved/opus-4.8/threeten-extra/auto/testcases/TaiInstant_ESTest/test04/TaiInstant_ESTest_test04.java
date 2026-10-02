package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test04 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that {@link TaiInstant#withNano(int)} only replaces the nano-of-second
     * (leaving the TAI seconds untouched) and that the resulting instant is therefore
     * not equal to the original one.
     */
    @Test(timeout = 4000)
    public void withNanoChangesOnlyNanosAndBreaksEquality() throws Throwable {
        // ofTaiSeconds normalises a negative nano adjustment: -1194 nanos rolls the
        // second back by one (to -3114) and leaves 999_998_806 nanos within that second.
        TaiInstant original = TaiInstant.ofTaiSeconds(-3113L, -1194L);
        assertEquals(-3114L, original.getTaiSeconds());
        assertEquals(999998806, original.getNano());

        // Replacing the nanos with 0 keeps the same seconds but resets the fraction.
        TaiInstant withZeroNanos = original.withNano(0);
        assertEquals(-3114L, withZeroNanos.getTaiSeconds());
        assertEquals(0, withZeroNanos.getNano());

        // The two instants differ only in their nanos, so they are not equal either way.
        assertFalse(withZeroNanos.equals(original));
        assertFalse(original.equals(withZeroNanos));
    }
}
