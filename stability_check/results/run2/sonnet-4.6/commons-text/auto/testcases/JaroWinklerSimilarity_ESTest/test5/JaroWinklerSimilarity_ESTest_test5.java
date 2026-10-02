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
     * Verifies that matches() returns zero matches, zero half-transpositions, and zero prefix
     * when comparing two CharBuffers backed by the same array but at different positions.
     *
     * Setup:
     *   - A 12-element char array is initialised to null characters ('\0').
     *   - "X19l5(mgJ" (9 chars) is written into charBufferAtStart, advancing its position to 9.
     *   - charBufferAtArrayStart wraps the same array from position 0, so it sees the full 12
     *     chars: ['X','1','9','l','5','(','m','g','J','\0','\0','\0'].
     *   - charBufferAfterWrite still points at position 9, so it sees only the 3 trailing nulls:
     *     ['\0','\0','\0'].
     *
     * Because the short buffer ('\0','\0','\0') has no characters in common with the windowed
     * region of the longer buffer that the Jaro algorithm would consider, all three result
     * components (matches, half-transpositions, prefix) are expected to be 0.
     */
    @Test(timeout = 4000)
    public void test5() throws Throwable {
        // Create a 12-element backing array of null characters.
        char[] backingArray = new char[12];

        // charBufferAfterWrite: wraps the array and writes "X19l5(mgJ";
        // its position advances to 9, leaving 3 remaining null chars.
        CharBuffer charBufferAfterWrite = CharBuffer.wrap(backingArray);
        charBufferAfterWrite.put("X19l5(mgJ");

        // charBufferAtArrayStart: wraps the same backing array from index 0,
        // so it spans all 12 characters including the written content.
        CharBuffer charBufferAtArrayStart = CharBuffer.wrap(backingArray);

        // matches() compares charBufferAtArrayStart (12 chars: "X19l5(mgJ\0\0\0")
        // against charBufferAfterWrite (3 remaining chars: "\0\0\0").
        int[] matchesHalfTranspositionsPrefix =
                JaroWinklerSimilarity.matches(charBufferAtArrayStart, charBufferAfterWrite);

        // Expect: 0 matches, 0 half-transpositions, 0 common prefix.
        assertArrayEquals(new int[] {0, 0, 0}, matchesHalfTranspositionsPrefix);
    }
}
