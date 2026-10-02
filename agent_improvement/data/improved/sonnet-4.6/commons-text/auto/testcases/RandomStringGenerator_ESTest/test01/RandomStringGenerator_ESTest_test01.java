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
public class RandomStringGenerator_ESTest_test01 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Verifies that calling generate() with a mocked random provider that always returns 0
     * causes an undeclared exception. Code point 0 is UNASSIGNED and is skipped by the
     * generator, so the mock's limited stub responses are exhausted and an exception is thrown.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();

        // Mock a random provider that returns 0 for each call (code point 0 is UNASSIGNED
        // and always skipped, so the generator never makes progress toward the requested length)
        TextRandomProvider mockedRandom = mock(TextRandomProvider.class, new ViolatedAssumptionAnswer());
        doReturn(0, 0, 0, 0, 0).when(mockedRandom).applyAsInt(anyInt());

        builder.usingRandom(mockedRandom);
        RandomStringGenerator generator = builder.get();

        // Undeclared exception!
        generator.generate(1114111);
    }
}
