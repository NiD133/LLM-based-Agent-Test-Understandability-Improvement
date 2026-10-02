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
public class LevenshteinDistance_ESTest_test11 extends LevenshteinDistance_ESTest_scaffolding {

    /**
     * With a threshold of 0, a non-empty string can never be transformed into an
     * empty one within budget: the edit distance equals the non-empty length (28),
     * which exceeds the threshold, so {@code apply} reports -1 ("over threshold").
     */
    @Test(timeout = 4000)
    public void thresholdZeroReturnsMinusOneWhenDistanceExceedsThreshold() throws Throwable {
        LevenshteinDistance distanceWithZeroThreshold = new LevenshteinDistance(Integer.valueOf(0));

        CharSequence nonEmptyText = "or.apace.commons.tex.StrLokp";
        CharSequence emptyText = CharBuffer.allocate(0);

        Integer result = distanceWithZeroThreshold.apply(nonEmptyText, emptyText);

        assertEquals(-1, (int) result);
    }
}
