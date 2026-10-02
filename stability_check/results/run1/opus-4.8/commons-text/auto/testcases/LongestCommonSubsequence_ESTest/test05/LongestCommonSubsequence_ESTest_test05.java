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
public class LongestCommonSubsequence_ESTest_test05 extends LongestCommonSubsequence_ESTest_scaffolding {

    /**
     * When one input contains a single occurrence of a character that also appears
     * in the other input, the longest common subsequence has length 1.
     *
     * Here the left sequence is "\u0000r" (a null character followed by 'r') and the
     * right sequence is a freshly allocated CharBuffer whose backing array is filled
     * with the default '\u0000' (null) character. Only the leading null character is
     * shared between the two sequences, so the score is 1.
     */
    @Test(timeout = 4000)
    public void applyReturnsOneWhenSequencesShareSingleCharacter() throws Throwable {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();

        // A newly allocated CharBuffer is pre-filled with the null character '\u0000'.
        CharBuffer nullFilledBuffer = CharBuffer.allocate(3226);
        CharSequence left = "\u0000r";

        Integer score = lcs.apply(left, nullFilledBuffer);

        assertEquals(1, (int) score);
    }
}
