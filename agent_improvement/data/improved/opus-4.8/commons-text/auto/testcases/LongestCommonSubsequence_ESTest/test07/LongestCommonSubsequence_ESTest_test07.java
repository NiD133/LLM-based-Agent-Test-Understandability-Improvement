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

    /**
     * Verifies that the longest common subsequence of two empty sequences is 0,
     * since there are no characters to share.
     */
    @Test(timeout = 4000)
    public void applyReturnsZeroForTwoEmptySequences() throws Throwable {
        LongestCommonSubsequence lcs = LongestCommonSubsequence.INSTANCE;
        CharBuffer emptySequence = CharBuffer.wrap(new char[0]);

        Integer lcsLength = lcs.apply((CharSequence) emptySequence, (CharSequence) emptySequence);

        assertEquals(0, (int) lcsLength);
    }
}
