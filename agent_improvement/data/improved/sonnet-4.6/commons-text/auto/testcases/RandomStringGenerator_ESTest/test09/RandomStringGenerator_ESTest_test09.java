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
public class RandomStringGenerator_ESTest_test09 extends RandomStringGenerator_ESTest_scaffolding {

    // withinRange(int, int) requires minimumCodePoint >= 0; a negative value must be rejected.
    private static final int NEGATIVE_CODE_POINT = -3302;

    @Test(timeout = 4000)
    public void test_withinRange_throwsIllegalArgumentException_whenMinimumCodePointIsNegative() throws Throwable {
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();

        try {
            builder.withinRange(NEGATIVE_CODE_POINT, NEGATIVE_CODE_POINT);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Minimum code point -3302 is negative
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
