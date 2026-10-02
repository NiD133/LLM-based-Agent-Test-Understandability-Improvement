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
public class LongestCommonSubsequence_ESTest_test02 extends LongestCommonSubsequence_ESTest_scaffolding {

    /**
     * When both inputs are empty, the longest common subsequence is the empty string.
     */
    @Test(timeout = 4000)
    public void logestCommonSubsequenceOfTwoEmptyInputsReturnsEmptyString() throws Throwable {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        CharBuffer emptyLeft = CharBuffer.allocate(0);
        String emptyRight = "";

        CharSequence result = lcs.logestCommonSubsequence(emptyLeft, emptyRight);

        assertEquals("", result);
    }
}
