package org.apache.commons.text;

import org.junit.Test;
import static org.evosuite.shaded.org.mockito.Mockito.anyInt;
import static org.evosuite.shaded.org.mockito.Mockito.doReturn;
import static org.evosuite.shaded.org.mockito.Mockito.mock;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test01 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Generates a string using a custom (mocked) source of randomness whose
     * {@code applyAsInt} always returns {@code 0}, so the generator keeps
     * picking the lowest code point in the configured range.
     *
     * <p>The maximum requested length ({@link Character#MAX_CODE_POINT} = 1114111)
     * exercises the {@code generate(int)} loop; the call is expected to throw an
     * undeclared runtime exception, which EvoSuite captured as the test's outcome.</p>
     */
    @Test(timeout = 4000)
    public void generateWithAlwaysZeroRandomThrows() throws Throwable {
        // A random source that always yields index 0 for any input.
        TextRandomProvider alwaysZeroRandom = mock(TextRandomProvider.class, new ViolatedAssumptionAnswer());
        doReturn(0, 0, 0, 0, 0).when(alwaysZeroRandom).applyAsInt(anyInt());

        RandomStringGenerator generator = RandomStringGenerator.builder()
                .usingRandom(alwaysZeroRandom)
                .get();

        // Undeclared exception expected.
        generator.generate(1114111);
    }
}
