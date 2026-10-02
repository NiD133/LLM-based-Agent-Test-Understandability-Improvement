package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.security.SecureRandom;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test06 extends RandomUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        RandomUtils randomUtils0 = new RandomUtils();
        // Undeclared exception!
        try {
            randomUtils0.randomDouble((-1.0), 4913.0790244406835);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Both range values must be non-negative.
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
