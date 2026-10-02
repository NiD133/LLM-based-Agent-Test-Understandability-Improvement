package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDistance_ESTest_test12 extends LevenshteinDistance_ESTest_scaffolding {

    /**
     * The Levenshtein distance between two empty sequences is 0, even when a
     * threshold is configured. Here the threshold is 0 and both inputs are
     * empty CharBuffers, so no edits are required.
     */
    @Test(timeout = 4000)
    public void distanceBetweenTwoEmptySequencesIsZero() throws Throwable {
        Integer threshold = Integer.valueOf(0);
        LevenshteinDistance distanceWithZeroThreshold = new LevenshteinDistance(threshold);

        CharBuffer emptySequence = CharBuffer.allocate(0);
        Integer distance = distanceWithZeroThreshold.apply((CharSequence) emptySequence, (CharSequence) emptySequence);

        assertEquals(0, (int) distance);
    }
}
