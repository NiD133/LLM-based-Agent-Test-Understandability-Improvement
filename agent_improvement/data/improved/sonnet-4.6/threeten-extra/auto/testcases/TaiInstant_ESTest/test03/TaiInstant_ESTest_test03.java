package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test03 extends TaiInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // A negative nanoAdjustment causes the factory to borrow one second and wrap
        // the nanoseconds: floorDiv(-1194, 1e9) = -1, floorMod(-1194, 1e9) = 999_998_806
        TaiInstant instant = TaiInstant.ofTaiSeconds(3600000000000L, -1194L);

        // Reflexive equality: an instance must equal itself
        boolean isEqualToItself = instant.equals(instant);
        assertTrue(isEqualToItself);

        // Seconds decremented by 1 due to the negative nano adjustment
        assertEquals(3599999999999L, instant.getTaiSeconds());

        // Nanos wrapped to 1_000_000_000 - 1194 = 999_998_806
        assertEquals(999998806, instant.getNano());
    }
}
