package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test13 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * When the distance is computed with a threshold of 0, an empty "left"
     * sequence and a much longer "right" sequence cannot be matched within
     * that threshold. The algorithm therefore reports a distance of -1 and
     * leaves all individual edit-operation counts at 0.
     */
    @Test(timeout = 4000)
    public void applyWithZeroThresholdReturnsMinusOneWhenLengthsExceedThreshold() throws Throwable {
        Integer threshold = 0;
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(threshold);

        CharSequence emptyLeft = CharBuffer.allocate(0);
        CharSequence longRight = CharBuffer.allocate(1839);

        LevenshteinResults results = distance.apply(emptyLeft, longRight);

        assertEquals(-1, (int) results.getDistance());
        assertEquals(0, (int) results.getSubstituteCount());
        assertEquals(0, (int) results.getInsertCount());
        assertEquals(0, (int) results.getDeleteCount());
    }
}
