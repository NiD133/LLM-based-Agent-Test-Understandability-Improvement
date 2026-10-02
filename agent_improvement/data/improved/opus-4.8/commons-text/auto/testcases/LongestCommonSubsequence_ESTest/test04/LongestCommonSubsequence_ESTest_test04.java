package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LongestCommonSubsequence_ESTest_test04 extends LongestCommonSubsequence_ESTest_scaffolding {

    /**
     * Passing two null sequences to logestCommonSubsequence must be rejected with an
     * IllegalArgumentException ("Inputs must not be null"), since the algorithm
     * cannot operate on null inputs.
     */
    @Test(timeout = 4000)
    public void nullInputsThrowIllegalArgumentException() throws Throwable {
        LongestCommonSubsequence lcs = LongestCommonSubsequence.INSTANCE;

        try {
            lcs.logestCommonSubsequence((CharSequence) null, (CharSequence) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message thrown by the CUT: "Inputs must not be null"
            verifyException("org.apache.commons.text.similarity.LongestCommonSubsequence", e);
        }
    }
}
