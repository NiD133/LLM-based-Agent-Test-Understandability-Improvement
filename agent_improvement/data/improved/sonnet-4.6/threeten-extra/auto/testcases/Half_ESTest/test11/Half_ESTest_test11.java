package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test11 extends Half_ESTest_scaffolding {

    /**
     * Verifies that querying the value range for an unsupported ChronoField
     * on a Half instance throws UnsupportedTemporalTypeException.
     * Half only supports HALF_OF_YEAR; all ChronoField values are unsupported.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        try {
            Half.H2.range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            verifyException("org.threeten.extra.Half", e);
        }
    }
}
