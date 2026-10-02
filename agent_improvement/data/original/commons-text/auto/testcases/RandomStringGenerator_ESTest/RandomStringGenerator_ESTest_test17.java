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
public class RandomStringGenerator_ESTest_test17 extends RandomStringGenerator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        RandomStringGenerator.Builder randomStringGenerator_Builder0 = RandomStringGenerator.builder();
        char[] charArray0 = new char[0];
        randomStringGenerator_Builder0.setAccumulate(true);
        RandomStringGenerator.Builder randomStringGenerator_Builder1 = randomStringGenerator_Builder0.selectFrom(charArray0);
        assertEquals(0, RandomStringGenerator.Builder.DEFAULT_MINIMUM_CODE_POINT);
    }
}
