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
public class LevenshteinDetailedDistance_ESTest_test13 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * When the left input is empty and the right input has 1839 characters,
     * the edit distance (1839) exceeds the threshold (0), so the algorithm
     * returns -1 with all operation counts at zero.
     */
    @Test(timeout = 4000)
    public void test_emptyLeft_largeRight_exceedsZeroThreshold_returnsNegativeDistance() throws Throwable {
        Integer threshold = new Integer(0);
        LevenshteinDetailedDistance distanceWithZeroThreshold = new LevenshteinDetailedDistance(threshold);

        CharBuffer largeRightBuffer = CharBuffer.allocate(1839);
        CharBuffer emptyLeftBuffer = CharBuffer.allocate(0);

        LevenshteinResults result = distanceWithZeroThreshold.apply((CharSequence) emptyLeftBuffer, (CharSequence) largeRightBuffer);

        assertEquals((-1), (int) result.getDistance());
        assertEquals(0, (int) result.getSubstituteCount());
        assertEquals(0, (int) result.getInsertCount());
        assertEquals(0, (int) result.getDeleteCount());
    }
}
