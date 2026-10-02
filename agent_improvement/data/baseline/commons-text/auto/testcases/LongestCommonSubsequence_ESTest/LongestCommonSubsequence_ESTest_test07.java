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

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        LongestCommonSubsequence longestCommonSubsequence0 = LongestCommonSubsequence.INSTANCE;
        char[] charArray0 = new char[0];
        CharBuffer charBuffer0 = CharBuffer.wrap(charArray0);
        Integer integer0 = longestCommonSubsequence0.apply((CharSequence) charBuffer0, (CharSequence) charBuffer0);
        assertEquals(0, (int) integer0);
    }
}
