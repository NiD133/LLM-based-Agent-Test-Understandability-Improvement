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
public class DamerauLevenshteinDistance_ESTest_test11 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * Verifies that comparing two identical strings produces a distance of zero.
     * The Damerau-Levenshtein distance between a string and itself is always 0
     * because no insertions, deletions, substitutions, or transpositions are needed.
     */
    @Test(timeout = 4000)
    public void test11_identicalStringsHaveZeroDistance() throws Throwable {
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance();
        String fullyQualifiedClassName = "org.apache.commons.text.similarity.DamerauLevenshteinDistance";

        Integer result = distance.apply((CharSequence) fullyQualifiedClassName, (CharSequence) fullyQualifiedClassName);

        assertEquals("Distance between identical strings must be 0", 0, (int) result);
    }
}
