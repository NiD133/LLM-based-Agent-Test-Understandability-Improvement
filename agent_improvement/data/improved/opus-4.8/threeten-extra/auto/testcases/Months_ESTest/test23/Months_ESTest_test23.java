package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test23 extends Months_ESTest_scaffolding {

    /**
     * Verifies that {@link Months#toString()} formats a single month using the
     * ISO-8601 period pattern "PnM", so {@link Months#ONE} renders as "P1M".
     */
    @Test(timeout = 4000)
    public void toString_forOneMonth_returnsIso8601Format() throws Throwable {
        Months oneMonth = Months.ONE;

        String formatted = oneMonth.toString();

        assertEquals("P1M", formatted);
    }
}
