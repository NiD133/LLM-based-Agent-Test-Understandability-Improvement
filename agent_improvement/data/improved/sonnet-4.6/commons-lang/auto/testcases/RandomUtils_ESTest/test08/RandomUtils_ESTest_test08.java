package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test08 extends RandomUtils_ESTest_scaffolding {

    // nextBytes rejects negative counts; the validation is performed by Validate inside randomBytes()
    private static final int NEGATIVE_BYTE_COUNT = -1862;

    @Test(timeout = 4000)
    public void test08_nextBytes_throwsIllegalArgumentException_whenCountIsNegative() throws Throwable {
        try {
            RandomUtils.nextBytes(NEGATIVE_BYTE_COUNT);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
