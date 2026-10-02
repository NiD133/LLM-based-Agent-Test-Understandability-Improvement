package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test04 extends Months_ESTest_scaffolding {

    /**
     * Subtracting a zero-month amount from a temporal makes no change, so the
     * method simply returns its input unchanged. When the input temporal is
     * null, the returned temporal is therefore also null.
     */
    @Test(timeout = 4000)
    public void subtractFromNullTemporalWithZeroMonthsReturnsNull() throws Throwable {
        Months zeroMonths = Months.ZERO;

        Temporal result = zeroMonths.subtractFrom((Temporal) null);

        assertNull(result);
    }
}
