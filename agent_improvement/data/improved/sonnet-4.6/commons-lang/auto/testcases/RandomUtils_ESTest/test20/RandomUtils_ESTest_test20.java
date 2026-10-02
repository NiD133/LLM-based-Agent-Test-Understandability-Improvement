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
public class RandomUtils_ESTest_test20 extends RandomUtils_ESTest_scaffolding {

    private static final int BYTE_COUNT = 1335;

    @Test(timeout = 4000)
    public void test_nextBytes_returnsArrayOfRequestedLength() throws Throwable {
        // nextBytes must return exactly as many bytes as requested
        byte[] result = RandomUtils.nextBytes(BYTE_COUNT);
        assertEquals(BYTE_COUNT, result.length);
    }
}
