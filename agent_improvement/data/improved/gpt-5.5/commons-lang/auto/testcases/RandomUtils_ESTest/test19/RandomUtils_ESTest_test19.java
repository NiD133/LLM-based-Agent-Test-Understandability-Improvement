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
        RandomUtils insecureRandomUtils = RandomUtils.insecure();

        insecureRandomUtils.toString();
        float randomFloat = insecureRandomUtils.randomFloat();
        //  // Unstable assertion: assertEquals(2.9443491E38F, randomFloat, 0.01F);

        long secureRandomLong = RandomUtils.nextLong();

        insecureRandomUtils.toString();
        long lowerBound = 3927L;
        RandomUtils.nextLong(3927L, 8693331374357696304L);
        insecureRandomUtils.randomFloat(0.0F, (float) 8693331374357696304L);

        long equalBoundsResult = RandomUtils.nextLong(0L, 0L);
        //  // Unstable assertion: assertFalse(equalBoundsResult == secureRandomLong);
    }
}
