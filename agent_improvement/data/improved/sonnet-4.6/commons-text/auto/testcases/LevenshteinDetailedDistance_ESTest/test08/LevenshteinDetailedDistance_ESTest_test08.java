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
public class LevenshteinDetailedDistance_ESTest_test08 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08_identicalCharBuffersShouldHaveZeroDistance() throws Throwable {
        // A CharBuffer wrapping two null characters, used as both left and right input
        char[] twoNullChars = new char[2];
        CharBuffer twoNullCharBuffer = CharBuffer.wrap(twoNullChars);

        // Use Integer.MAX_VALUE as threshold so the threshold-based code path is exercised
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(Integer.MAX_VALUE);

        // Comparing a sequence to itself must always yield distance 0
        LevenshteinResults result = distance.apply((CharSequence) twoNullCharBuffer, (CharSequence) twoNullCharBuffer);
        assertEquals(0, (int) result.getDistance());
    }
}
