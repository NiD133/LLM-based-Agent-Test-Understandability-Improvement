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
public class LevenshteinDetailedDistance_ESTest_test11 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * When the threshold is 0, comparing a non-empty sequence (11 null chars) against an empty
     * sequence requires at least 11 deletions, which exceeds the threshold. The distance is
     * reported as -1, and all operation counts (insert, delete, substitute) remain 0.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        CharBuffer elevenNullCharsBuffer = CharBuffer.allocate(11);
        Integer threshold = new Integer(0);
        LevenshteinDetailedDistance distanceWithZeroThreshold = new LevenshteinDetailedDistance(threshold);
        CharBuffer emptyBuffer = CharBuffer.allocate(0);

        LevenshteinResults result = distanceWithZeroThreshold.apply((CharSequence) elevenNullCharsBuffer, (CharSequence) emptyBuffer);

        assertEquals(0, (int) result.getSubstituteCount());
        assertEquals((-1), (int) result.getDistance());
        assertEquals(0, (int) result.getDeleteCount());
        assertEquals(0, (int) result.getInsertCount());
    }
}
