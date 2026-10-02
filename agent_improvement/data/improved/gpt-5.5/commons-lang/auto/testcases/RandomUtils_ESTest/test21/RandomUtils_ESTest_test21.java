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
public class RandomUtils_ESTest_test21 extends RandomUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        final float generatedFloat = RandomUtils.nextFloat();
        // The generated value is intentionally not asserted because nextFloat() is non-deterministic.
    }
}
