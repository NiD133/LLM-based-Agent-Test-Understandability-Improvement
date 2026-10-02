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
public class LevenshteinDistance_ESTest_test03 extends LevenshteinDistance_ESTest_scaffolding {

    /**
     * The Levenshtein distance between two identical (here empty) sequences is
     * zero, because no character edits are needed to turn one into the other.
     */
    @Test(timeout = 4000)
    public void distanceBetweenTwoEmptyStringsIsZero() throws Throwable {
        LevenshteinDistance levenshteinDistance = LevenshteinDistance.getDefaultInstance();

        Integer distance = levenshteinDistance.apply((CharSequence) "", (CharSequence) "");

        assertEquals(0, (int) distance);
    }
}
