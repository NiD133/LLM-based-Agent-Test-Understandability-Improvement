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
public class LevenshteinDistance_ESTest_test01 extends LevenshteinDistance_ESTest_scaffolding {

    /**
     * The default instance uses the unlimited (no-threshold) algorithm.
     * The two strings differ only by a single extra character ('y') in the
     * left input, so the Levenshtein distance should be exactly one deletion.
     */
    @Test(timeout = 4000)
    public void apply_stringsDifferingByOneCharacter_returnsDistanceOne() throws Throwable {
        LevenshteinDistance distance = LevenshteinDistance.getDefaultInstance();

        CharSequence left = "|!(,ny:";
        CharSequence right = "|!(,n:";
        Integer editDistance = distance.apply(left, right);

        assertEquals(1, (int) editDistance);
    }
}
