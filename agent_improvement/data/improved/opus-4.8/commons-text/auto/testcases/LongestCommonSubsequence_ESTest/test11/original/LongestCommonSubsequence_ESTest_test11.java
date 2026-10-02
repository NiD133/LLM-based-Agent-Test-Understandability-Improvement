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
public class LongestCommonSubsequence_ESTest_test11 extends LongestCommonSubsequence_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        LongestCommonSubsequence longestCommonSubsequence0 = new LongestCommonSubsequence();
        char[] charArray0 = new char[2];
        CharBuffer charBuffer0 = CharBuffer.wrap(charArray0);
        CharSequence charSequence0 = longestCommonSubsequence0.logestCommonSubsequence(charBuffer0, "\u0000r");
        assertEquals("\u0000", charSequence0);
    }
}
