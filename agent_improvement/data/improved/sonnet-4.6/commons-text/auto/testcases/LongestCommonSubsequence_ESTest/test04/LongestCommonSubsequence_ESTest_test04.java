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
public class LongestCommonSubsequence_ESTest_test04 extends LongestCommonSubsequence_ESTest_scaffolding {

    /**
     * Verifies that calling the deprecated logestCommonSubsequence method with two null
     * arguments throws IllegalArgumentException, since null inputs are not permitted.
     */
    @Test(timeout = 4000)
    public void test04_logestCommonSubsequence_throwsIllegalArgumentException_whenBothInputsAreNull() throws Throwable {
        LongestCommonSubsequence lcs = LongestCommonSubsequence.INSTANCE;

        try {
            lcs.logestCommonSubsequence((CharSequence) null, (CharSequence) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.text.similarity.LongestCommonSubsequence", e);
        }
    }
}
