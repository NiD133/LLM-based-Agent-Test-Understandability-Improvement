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

    /**
     * Verifies that {@link RandomUtils#toString()} returns a non-null
     * representation for a default-constructed instance.
     */
    @Test(timeout = 4000)
    public void toString_onDefaultInstance_returnsNonNullText() throws Throwable {
        RandomUtils randomUtils = new RandomUtils();

        String description = randomUtils.toString();

        assertNotNull(description);
    }
}
