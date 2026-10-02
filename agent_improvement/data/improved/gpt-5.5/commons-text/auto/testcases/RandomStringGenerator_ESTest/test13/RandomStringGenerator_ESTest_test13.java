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
public class RandomStringGenerator_ESTest_test13 extends RandomStringGenerator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();

        char[][] ranges = new char[1][6];
        char[] range = new char[2];
        ranges[0] = range;

        RandomStringGenerator.Builder builderAfterRangeConfiguration = builder.withinRange(ranges);

        assertEquals(1114111, RandomStringGenerator.Builder.DEFAULT_MAXIMUM_CODE_POINT);
    }
}
