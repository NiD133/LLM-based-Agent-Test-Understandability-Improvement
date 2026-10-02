package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test19 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that converting a TaiInstant to an Instant does not change the
     * source instant's own fields, since TaiInstant is immutable.
     */
    @Test(timeout = 4000)
    public void toInstant_leavesSourceTaiInstantUnchanged() throws Throwable {
        TaiInstant taiInstant = TaiInstant.ofTaiSeconds(20, 20);

        taiInstant.toInstant();

        assertEquals("TAI seconds should be unchanged after toInstant()",
                20L, taiInstant.getTaiSeconds());
        assertEquals("nanos should be unchanged after toInstant()",
                20, taiInstant.getNano());
    }
}
