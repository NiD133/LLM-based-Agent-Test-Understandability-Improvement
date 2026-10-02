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
     * Two empty sequences have a Levenshtein distance of 0, even when a
     * threshold of 0 limits how large the computed distance may be.
     */
    @Test(timeout = 4000)
    public void distanceBetweenTwoEmptySequencesIsZero() throws Throwable {
        Integer threshold = Integer.valueOf(0);
        LevenshteinDistance distanceWithZeroThreshold = new LevenshteinDistance(threshold);

        CharSequence emptySequence = CharBuffer.allocate(0);
        Integer distance = distanceWithZeroThreshold.apply(emptySequence, emptySequence);

        assertEquals(0, (int) distance);
    }
}
