package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test06 extends TaiInstant_ESTest_scaffolding {

    /**
     * An instant is never before itself, so {@code isBefore} comparing an
     * instant to itself returns {@code false}. The factory values are also
     * preserved unchanged on the created instant.
     */
    @Test(timeout = 4000)
    public void isBefore_sameInstant_returnsFalse() throws Throwable {
        TaiInstant instant = TaiInstant.ofTaiSeconds(20, 20);

        boolean isBeforeItself = instant.isBefore(instant);

        assertFalse(isBeforeItself);
        assertEquals(20L, instant.getTaiSeconds());
        assertEquals(20, instant.getNano());
    }
}
