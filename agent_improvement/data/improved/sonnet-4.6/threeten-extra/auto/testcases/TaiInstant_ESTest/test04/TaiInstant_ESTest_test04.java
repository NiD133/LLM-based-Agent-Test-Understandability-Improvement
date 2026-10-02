package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test04 extends TaiInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // ofTaiSeconds normalizes the negative nano-adjustment -1194:
        //   floorDiv(-1194, 1_000_000_000) = -1  →  borrows 1 second: -3113 + (-1) = -3114
        //   floorMod(-1194, 1_000_000_000) = 999_998_806  →  stored nano value
        TaiInstant original = TaiInstant.ofTaiSeconds(-3113L, -1194L);

        // withNano(0) keeps the same TAI second (-3114) but replaces the nanosecond with 0
        TaiInstant withNanoZero = original.withNano(0);

        // withNanoZero and original share the same second but differ in nanoseconds, so they are not equal
        boolean areEqual = withNanoZero.equals(original);

        assertEquals(-3114L, withNanoZero.getTaiSeconds());
        assertFalse(original.equals((Object) withNanoZero));
        assertFalse(areEqual);
        assertEquals(-3114L, original.getTaiSeconds());
        assertEquals(0, withNanoZero.getNano());
        assertEquals(999998806, original.getNano());
    }
}
