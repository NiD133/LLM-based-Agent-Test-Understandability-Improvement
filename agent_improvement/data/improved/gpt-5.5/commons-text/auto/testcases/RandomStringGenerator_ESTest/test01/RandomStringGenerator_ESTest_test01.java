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

    private static final int GENERATED_CODE_POINT_COUNT = 1114111;
    private static final int RANDOM_OFFSET = 0;

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        RandomStringGenerator.Builder generatorBuilder = RandomStringGenerator.builder();
        TextRandomProvider randomProvider = mock(TextRandomProvider.class, new ViolatedAssumptionAnswer());
        doReturn(RANDOM_OFFSET, RANDOM_OFFSET, RANDOM_OFFSET, RANDOM_OFFSET, RANDOM_OFFSET).when(randomProvider).applyAsInt(anyInt());

        generatorBuilder.usingRandom(randomProvider);
        RandomStringGenerator generator = generatorBuilder.get();

        generator.generate(GENERATED_CODE_POINT_COUNT);
    }
}
