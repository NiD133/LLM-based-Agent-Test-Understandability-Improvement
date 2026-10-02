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
public class RandomStringGenerator_ESTest_test00 extends RandomStringGenerator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        RandomStringGenerator.Builder randomStringGenerator_Builder0 = new RandomStringGenerator.Builder();
        IntUnaryOperator intUnaryOperator0 = IntUnaryOperator.identity();
        char[] charArray0 = new char[2];
        RandomStringGenerator.Builder randomStringGenerator_Builder1 = randomStringGenerator_Builder0.selectFrom(charArray0);
        RandomStringGenerator.Builder randomStringGenerator_Builder2 = randomStringGenerator_Builder1.usingRandom(intUnaryOperator0);
        RandomStringGenerator randomStringGenerator0 = randomStringGenerator_Builder2.get();
        // Undeclared exception!
        try {
            randomStringGenerator0.generate(1114111);
            fail("Expecting exception: IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
        }
    }
}
