package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test00 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Integer negativeThreshold = new Integer((-2571));
        LevenshteinDetailedDistance distance = null;

        try {
            distance = new LevenshteinDetailedDistance(negativeThreshold);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Threshold must not be negative
            //
            verifyException("org.apache.commons.text.similarity.LevenshteinDetailedDistance", e);
        }
    }
}
