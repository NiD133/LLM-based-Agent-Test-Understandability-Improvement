package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertSame;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test19 extends Quarter_ESTest_scaffolding {

    /**
     * Quarter.from(TemporalAccessor) is documented to return its argument unchanged
     * when that argument is already a Quarter. This verifies it returns the very same
     * instance (not just an equal one) for Q4.
     */
    @Test(timeout = 4000)
    public void from_returnsSameInstance_whenArgumentIsAlreadyAQuarter() throws Throwable {
        Quarter result = Quarter.from(Quarter.Q4);

        assertSame(Quarter.Q4, result);
    }
}
