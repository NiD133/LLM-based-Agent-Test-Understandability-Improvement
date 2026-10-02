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
public class RandomStringGenerator_ESTest_test12 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Verifies that withinRange(char[]...) throws IllegalArgumentException when the minimum
     * character in a pair has a higher code point than the maximum character.
     *
     * The pair {'L', '\0'} is invalid because 'L' (code point 76) > '\0' (code point 0).
     */
    @Test(timeout = 4000)
    public void test_withinRange_throwsWhenMinCodePointExceedsMax() throws Throwable {
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();

        // Construct a single char pair where min ('L' = 76) > max ('\0' = 0)
        char[] invalidPair = new char[2];
        invalidPair[0] = 'L'; // minimum code point: 76
        // invalidPair[1] left as default '\0' — maximum code point: 0

        char[][] pairs = new char[1][6];
        pairs[0] = invalidPair;

        // Undeclared exception!
        try {
            builder.withinRange(pairs);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Minimum code point 76 is larger than maximum code point 0
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
