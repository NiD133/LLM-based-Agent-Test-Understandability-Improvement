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
public class LongestCommonSubsequence_ESTest_test00 extends LongestCommonSubsequence_ESTest_scaffolding {

    /**
     * Verifies that longestCommonSubstringLengthArray returns a DP table whose first dimension
     * equals (input length + 1). For two identical 6-character strings, the outer array must
     * have length 7 (indices 0..6 inclusive).
     */
    @Test(timeout = 4000)
    public void test00_dpTableRowCountEqualsInputLengthPlusOne() throws Throwable {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        String input = "b?'NC="; // length 6

        int[][] dpTable = lcs.longestCommonSubstringLengthArray(input, input);

        // The method allocates int[left.length()+1][right.length()+1], so the first dimension is 7
        assertEquals(7, dpTable.length);
    }
}
