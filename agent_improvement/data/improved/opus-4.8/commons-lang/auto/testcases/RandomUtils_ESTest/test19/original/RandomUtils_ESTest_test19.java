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
public class RandomUtils_ESTest_test19 extends RandomUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        RandomUtils randomUtils0 = RandomUtils.insecure();
        randomUtils0.toString();
        float float0 = randomUtils0.randomFloat();
        //  // Unstable assertion: assertEquals(2.9443491E38F, float0, 0.01F);
        long long0 = RandomUtils.nextLong();
        randomUtils0.toString();
        long long1 = 3927L;
        RandomUtils.nextLong(3927L, 8693331374357696304L);
        randomUtils0.randomFloat(0.0F, (float) 8693331374357696304L);
        long long2 = RandomUtils.nextLong(0L, 0L);
        //  // Unstable assertion: assertFalse(long2 == long0);
    }
}
