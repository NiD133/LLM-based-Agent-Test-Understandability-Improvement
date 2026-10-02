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
public class RandomUtils_ESTest_test17 extends RandomUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        // Verify that a default-constructed RandomUtils instance produces a non-null toString() representation
        RandomUtils randomUtils = new RandomUtils();
        String stringRepresentation = randomUtils.toString();
        assertNotNull(stringRepresentation);
    }
}
