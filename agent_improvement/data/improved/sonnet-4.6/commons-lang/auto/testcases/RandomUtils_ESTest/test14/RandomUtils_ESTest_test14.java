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
public class RandomUtils_ESTest_test14 extends RandomUtils_ESTest_scaffolding {

    // Verifies that secureStrong() returns a non-null singleton backed by SecureRandom.getInstanceStrong()
    @Test(timeout = 4000)
    public void test_secureStrong_returnsSingletonInstance() throws Throwable {
        RandomUtils secureStrongInstance = RandomUtils.secureStrong();
        assertNotNull(secureStrongInstance);
    }
}
