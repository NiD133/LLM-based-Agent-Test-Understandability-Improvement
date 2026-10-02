package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test02 extends RandomStringGenerator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // generate(minLength, maxLength) must reject inverted ranges where maxLength < minLength
        RandomStringGenerator generator = new RandomStringGenerator.Builder().get();

        try {
            generator.generate(Character.MAX_CODE_POINT, 0);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
