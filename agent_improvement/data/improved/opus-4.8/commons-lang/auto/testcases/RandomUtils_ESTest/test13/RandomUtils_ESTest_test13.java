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
public class RandomUtils_ESTest_test13 extends RandomUtils_ESTest_scaffolding {

    /**
     * Verifies that the package-private {@link RandomUtils#secureRandom()} factory
     * returns a usable {@link SecureRandom} instance rather than {@code null}.
     */
    @Test(timeout = 4000)
    public void secureRandom_returnsNonNullInstance() throws Throwable {
        SecureRandom secureRandom = RandomUtils.secureRandom();

        assertNotNull("secureRandom() should always provide a SecureRandom instance", secureRandom);
    }
}
