package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test08 extends AmPm_ESTest_scaffolding {

    /**
     * AmPm.from should return the same AmPm instance when given an AmPm,
     * because from() short-circuits when its argument is already an AmPm.
     */
    @Test(timeout = 4000)
    public void from_givenAmPm_returnsSameInstance() throws Throwable {
        AmPm result = AmPm.from(AmPm.AM);

        assertSame(AmPm.AM, result);
    }
}
