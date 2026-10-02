package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LongestCommonSubsequence_ESTest_test10 extends LongestCommonSubsequence_ESTest_scaffolding {

    /**
     * Verifies that {@link LongestCommonSubsequence#apply(CharSequence, CharSequence)}
     * rejects null inputs by throwing an {@link IllegalArgumentException}.
     */
    @Test(timeout = 4000)
    public void applyWithBothInputsNullThrowsIllegalArgumentException() throws Throwable {
        LongestCommonSubsequence lcs = LongestCommonSubsequence.INSTANCE;

        try {
            lcs.apply((CharSequence) null, (CharSequence) null);
            fail("Expecting exception: IllegalArgumentException for null inputs");
        } catch (IllegalArgumentException e) {
            // The CUT rejects null inputs with the message "Inputs must not be null".
            verifyException("org.apache.commons.text.similarity.LongestCommonSubsequence", e);
        }
    }
}
