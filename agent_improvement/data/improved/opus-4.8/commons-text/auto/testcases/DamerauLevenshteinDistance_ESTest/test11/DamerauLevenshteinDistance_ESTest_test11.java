package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DamerauLevenshteinDistance_ESTest_test11 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * Two identical character sequences have a Damerau-Levenshtein distance of zero,
     * since no edits are needed to turn one into the other.
     */
    @Test(timeout = 4000)
    public void distanceBetweenIdenticalSequencesIsZero() throws Throwable {
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance();
        String text = "org.apache.commons.text.similarity.DamerauLevenshteinDistance";

        Integer result = distance.apply((CharSequence) text, (CharSequence) text);

        assertEquals(0, (int) result);
    }
}
