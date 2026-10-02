package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test08 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Comparing a sequence with itself must yield a Levenshtein distance of 0,
     * even when the distance is computed with a threshold. Here the threshold is
     * Integer.MAX_VALUE (effectively unbounded) and the sequence is a two-character
     * CharBuffer, so no edits are needed to transform it into itself.
     */
    @Test(timeout = 4000)
    public void identicalSequencesHaveZeroDistance() throws Throwable {
        CharBuffer sequence = CharBuffer.wrap(new char[2]);
        LevenshteinDetailedDistance distanceWithMaxThreshold =
                new LevenshteinDetailedDistance(Integer.MAX_VALUE);

        LevenshteinResults results = distanceWithMaxThreshold.apply(sequence, sequence);

        assertEquals(0, (int) results.getDistance());
    }
}
