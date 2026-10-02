package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertSame;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test16 extends Half_ESTest_scaffolding {

    /**
     * Half.from() applied to a Half should return that same instance unchanged,
     * since Half.from() short-circuits when the temporal is already a Half.
     */
    @Test(timeout = 4000)
    public void from_givenHalf_returnsSameInstance() throws Throwable {
        Half secondHalf = Half.H2;

        Half result = Half.from(secondHalf);

        assertSame(secondHalf, result);
    }
}
