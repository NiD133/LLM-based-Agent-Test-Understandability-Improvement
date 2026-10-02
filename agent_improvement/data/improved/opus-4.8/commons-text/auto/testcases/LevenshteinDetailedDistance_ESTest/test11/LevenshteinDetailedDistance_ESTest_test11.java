package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test11 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * When a threshold of 0 is set, the distance between an 11-character
     * sequence and an empty sequence (which truly is 11) exceeds the
     * threshold. The algorithm therefore gives up and reports a distance of
     * -1 with all individual edit counts left at 0.
     */
    @Test(timeout = 4000)
    public void thresholdZeroExceededReturnsNegativeDistance() throws Throwable {
        // Threshold of 0: any non-zero distance is considered "too far".
        LevenshteinDetailedDistance distanceWithZeroThreshold =
                new LevenshteinDetailedDistance(Integer.valueOf(0));

        CharSequence elevenCharacters = CharBuffer.allocate(11);
        CharSequence emptySequence = CharBuffer.allocate(0);

        LevenshteinResults results =
                distanceWithZeroThreshold.apply(elevenCharacters, emptySequence);

        // Distance (11) exceeds the threshold, so -1 is reported.
        assertEquals(-1, (int) results.getDistance());
        // No individual edit operations are counted when the threshold is exceeded.
        assertEquals(0, (int) results.getInsertCount());
        assertEquals(0, (int) results.getDeleteCount());
        assertEquals(0, (int) results.getSubstituteCount());
    }
}
