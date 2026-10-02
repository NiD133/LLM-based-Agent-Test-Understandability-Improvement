package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.function.IntUnaryOperator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test02 extends RandomStringGenerator_ESTest_scaffolding {

    private static final int MINIMUM_LENGTH_GREATER_THAN_MAXIMUM = 1114111;
    private static final int SMALLER_MAXIMUM_LENGTH = 0;

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        RandomStringGenerator.Builder generatorBuilder = new RandomStringGenerator.Builder();
        RandomStringGenerator generator = generatorBuilder.get();

        try {
            generator.generate(MINIMUM_LENGTH_GREATER_THAN_MAXIMUM, SMALLER_MAXIMUM_LENGTH);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            verifyException("org.apache.commons.lang3.Validate", exception);
        }
    }
}
