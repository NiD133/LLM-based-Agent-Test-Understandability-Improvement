package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test21 extends Years_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        Years oneYearAmount = Years.ONE;

        try {
            oneYearAmount.ONE.get((TemporalUnit) null);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            verifyException("org.threeten.extra.Years", e);
        }
    }
}
