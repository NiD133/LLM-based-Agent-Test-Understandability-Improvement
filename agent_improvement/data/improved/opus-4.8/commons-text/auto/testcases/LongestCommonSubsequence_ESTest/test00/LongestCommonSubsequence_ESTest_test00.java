package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LongestCommonSubsequence_ESTest_test00 extends LongestCommonSubsequence_ESTest_scaffolding {

    /**
     * The DP length array built by {@link LongestCommonSubsequence#longestCommonSubstringLengthArray}
     * has dimensions (left.length() + 1) x (right.length() + 1). This test verifies that the number
     * of rows equals the length of the {@code left} input plus one.
     */
    @Test(timeout = 4000)
    public void lengthArrayHasOneRowPerLeftCharacterPlusOne() throws Throwable {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        String input = "b?'NC=";

        int[][] lengthArray = lcs.longestCommonSubstringLengthArray(input, input);

        int expectedRowCount = input.length() + 1;
        assertEquals(expectedRowCount, lengthArray.length);
    }
}
