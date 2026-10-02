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
public class LevenshteinDistance_ESTest_test07 extends LevenshteinDistance_ESTest_scaffolding {

    /**
     * Comparing a string with itself yields a Levenshtein distance of zero,
     * even when a (here maximal) threshold is supplied.
     */
    @Test(timeout = 4000)
    public void identicalSequencesHaveZeroDistance() throws Throwable {
        Integer threshold = Integer.MAX_VALUE;
        LevenshteinDistance distanceWithThreshold = new LevenshteinDistance(threshold);

        String sequence = "3{sMd^PVa";
        Integer distance = distanceWithThreshold.apply((CharSequence) sequence, (CharSequence) sequence);

        assertEquals(0, (int) distance);
    }
}
