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
public class LevenshteinDistance_ESTest_test09 extends LevenshteinDistance_ESTest_scaffolding {

    /**
     * When the threshold is large enough to admit the true distance, apply()
     * returns that distance. Here a freshly allocated 41-character CharBuffer
     * (filled with NUL chars) is compared against a 33-character string whose
     * characters never include NUL. Every position differs, so the Levenshtein
     * distance equals the longer length (41 substitutions/insertions), which is
     * within the threshold of 41 and is therefore returned unchanged.
     */
    @Test(timeout = 4000)
    public void apply_withThresholdEqualToDistance_returnsDistance() throws Throwable {
        final int threshold = 41;
        LevenshteinDistance distance = new LevenshteinDistance(threshold);

        // A 41-char buffer of NUL characters versus a 33-char string of non-NUL chars.
        CharBuffer nulBuffer = CharBuffer.allocate(41);
        CharSequence comparisonText = "org.apache.commons.text.StrLookup";

        Integer result = distance.apply((CharSequence) nulBuffer, comparisonText);

        assertEquals(41, (int) result);
    }
}
