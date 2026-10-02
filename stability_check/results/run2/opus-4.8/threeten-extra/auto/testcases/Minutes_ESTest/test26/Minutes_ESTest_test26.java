package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test26 extends Minutes_ESTest_scaffolding {

    /**
     * The zero-minutes constant should render in ISO-8601 form as "PT0M".
     */
    @Test(timeout = 4000)
    public void zeroMinutesToStringIsPT0M() throws Throwable {
        Minutes zeroMinutes = Minutes.ZERO;

        String isoText = zeroMinutes.toString();

        assertEquals("PT0M", isoText);
    }
}
