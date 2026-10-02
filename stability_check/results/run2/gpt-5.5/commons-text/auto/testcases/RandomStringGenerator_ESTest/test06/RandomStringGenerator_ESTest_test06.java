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
public class RandomStringGenerator_ESTest_test06 extends RandomStringGenerator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        final RandomStringGenerator.Builder defaultGeneratorBuilder = RandomStringGenerator.builder();
        final RandomStringGenerator defaultGenerator = defaultGeneratorBuilder.get();
        final int negativeLength = -882;

        try {
            defaultGenerator.generate(negativeLength);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            // RandomStringGenerator delegates negative length validation to Validate.
            verifyException("org.apache.commons.lang3.Validate", exception);
        }
    }
}
