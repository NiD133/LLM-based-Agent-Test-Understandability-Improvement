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
public class RandomStringGenerator_ESTest_test07 extends RandomStringGenerator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_generateWithZeroLength_returnsEmptyString() throws Throwable {
        // Arrange: build a default generator with no special configuration
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();
        RandomStringGenerator generator = builder.get();

        // Act: request a string of zero code points
        String result = generator.generate(0);

        // Assert: a zero-length request must produce an empty string
        assertEquals("", result);
    }
}
