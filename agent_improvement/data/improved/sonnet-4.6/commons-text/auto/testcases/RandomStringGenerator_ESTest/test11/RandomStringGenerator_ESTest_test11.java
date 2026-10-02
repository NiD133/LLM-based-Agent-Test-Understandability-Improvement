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
public class RandomStringGenerator_ESTest_test11 extends RandomStringGenerator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void withinRange_throwsWhenMinCodePointExceedsMax() throws Throwable {
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();

        // withinRange requires min <= max; passing MAX_CODE_POINT as min and 0 as max is invalid
        try {
            builder.withinRange(Character.MAX_CODE_POINT, 0);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
