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
public class RandomUtils_ESTest_test10 extends RandomUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        float float0 = RandomUtils.nextFloat(3.9726779E18F, 3.9726779E18F);
        assertEquals(3.9726779E18F, float0, 0.01F);
    }
}
