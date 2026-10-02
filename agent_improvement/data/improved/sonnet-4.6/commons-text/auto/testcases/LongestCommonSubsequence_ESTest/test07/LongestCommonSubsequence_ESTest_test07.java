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
public class LongestCommonSubsequence_ESTest_test07 extends LongestCommonSubsequence_ESTest_scaffolding {

    // The LCS of two empty sequences has length 0.
    @Test(timeout = 4000)
    public void test07_applyReturnsZeroForTwoEmptyCharBuffers() throws Throwable {
        LongestCommonSubsequence lcs = LongestCommonSubsequence.INSTANCE;
        CharBuffer emptyBuffer = CharBuffer.wrap(new char[0]);
        Integer result = lcs.apply((CharSequence) emptyBuffer, (CharSequence) emptyBuffer);
        assertEquals(0, (int) result);
    }
}
