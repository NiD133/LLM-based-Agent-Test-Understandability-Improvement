package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JaroWinklerSimilarity_ESTest_test5 extends JaroWinklerSimilarity_ESTest_scaffolding {

    /**
     * Tests that matches() returns zero matches, zero half-transpositions, and zero prefix
     * when the two CharBuffer views share the same underlying array but have no characters
     * in common within the matching range.
     *
     * Setup:
     *   - A 12-element char array is allocated (all null chars '\0' initially).
     *   - "X19l5(mgJ" (9 chars) is written into charBufferWithAdvancedPosition, advancing its
     *     position to 9 so only the 3 trailing null chars ('\0', '\0', '\0') remain visible.
     *   - charBufferFromStart wraps the same array from position 0, exposing all 12 chars
     *     ("X19l5(mgJ\0\0\0").
     *
     * The 3 null chars visible in charBufferWithAdvancedPosition fall outside the match window
     * of the first 8 non-null chars of charBufferFromStart, so no matches are found.
     */
    @Test(timeout = 4000)
    public void test_matchesReturnsZeroWhenRemainingCharsAreOutsideMatchWindow() throws Throwable {
        char[] sharedCharArray = new char[12];

        // Wrap the array and write 9 characters, leaving the buffer positioned at index 9.
        // The 3 remaining chars (indices 9-11) in this view are still null ('\0').
        CharBuffer charBufferWithAdvancedPosition = CharBuffer.wrap(sharedCharArray);
        charBufferWithAdvancedPosition.put("X19l5(mgJ");

        // Wrap the same array from position 0, so all 12 chars are visible ("X19l5(mgJ\0\0\0").
        CharBuffer charBufferFromStart = CharBuffer.wrap(sharedCharArray);

        // The first argument (charBufferFromStart) has 12 chars; the second
        // (charBufferWithAdvancedPosition) has only 3 remaining null chars.
        // The null chars at the tail of the second buffer fall outside the Jaro match window
        // for the non-null chars at the head of the first buffer, yielding no matches.
        int[] matchResult = JaroWinklerSimilarity.matches(charBufferFromStart, charBufferWithAdvancedPosition);

        assertArrayEquals(new int[] { 0, 0, 0 }, matchResult);
    }
}
