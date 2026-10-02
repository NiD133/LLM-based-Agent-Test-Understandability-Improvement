package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test06 extends Years_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Integer division: 1 / 498 = 0, so zeroYears holds Years.ZERO
        Years zeroYears = Years.ONE.dividedBy(498);

        // subtractFrom skips the temporal.minus() call when the amount is zero,
        // returning the input temporal unchanged — so null in, null out
        Temporal result = zeroYears.subtractFrom((Temporal) null);
        assertNull(result);
    }
}
