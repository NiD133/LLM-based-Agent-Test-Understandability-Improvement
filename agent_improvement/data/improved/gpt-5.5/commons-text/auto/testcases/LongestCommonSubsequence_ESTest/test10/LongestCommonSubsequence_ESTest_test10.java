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
public class LongestCommonSubsequence_ESTest_test10 extends LongestCommonSubsequence_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        LongestCommonSubsequence lcs = LongestCommonSubsequence.INSTANCE;

        try {
            lcs.apply((CharSequence) null, (CharSequence) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            verifyException("org.apache.commons.text.similarity.LongestCommonSubsequence", expected);
        }
    }
}
