package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Period;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test29 extends Hours_ESTest_scaffolding {

    /**
     * Hours.from(Period.ZERO) should yield an amount of zero hours,
     * whose ISO-8601 string representation is "PT0H".
     */
    @Test(timeout = 4000)
    public void toString_ofZeroHours_returnsPT0H() throws Throwable {
        Hours zeroHours = Hours.from(Period.ZERO);

        String isoText = zeroHours.toString();

        assertEquals("PT0H", isoText);
    }
}
