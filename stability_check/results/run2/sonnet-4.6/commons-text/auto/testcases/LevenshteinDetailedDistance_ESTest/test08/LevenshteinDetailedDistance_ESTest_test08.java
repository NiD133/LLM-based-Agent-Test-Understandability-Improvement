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

    /**
     * Verifies that applying the distance algorithm to a CharSequence compared with itself
     * yields a distance of zero, even when the threshold is set to Integer.MAX_VALUE.
     */
    @Test(timeout = 4000)
    public void testApply_sameCharBufferInput_returnsZeroDistance() throws Throwable {
        char[] twoNullChars = new char[2];
        CharBuffer charBuffer = CharBuffer.wrap(twoNullChars);
        Integer maxThreshold = Integer.MAX_VALUE;
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(maxThreshold);
        LevenshteinResults results = distance.apply((CharSequence) charBuffer, (CharSequence) charBuffer);
        assertEquals(0, (int) results.getDistance());
    }
}
