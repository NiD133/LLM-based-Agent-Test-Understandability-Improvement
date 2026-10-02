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
public class RandomStringGenerator_ESTest_test14 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * withinRange(char[]...) requires each inner array to be a 2-element [min, max] pair.
     * Passing a pair with 6 elements instead of 2 must throw IllegalArgumentException
     * with the message "Each pair must contain minimum and maximum code point".
     */
    @Test(timeout = 4000)
    public void test14_withinRange_throwsWhenPairLengthIsNotTwo() throws Throwable {
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();

        // A pair with 6 characters instead of the required 2 is invalid input
        char[][] invalidPairs = new char[1][6];

        try {
            builder.withinRange(invalidPairs);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
