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
public class LevenshteinDetailedDistance_ESTest_test18 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Verifies that comparing two CharBuffer sequences of different lengths, where differences
     * are resolved purely by insertions and deletions (no substitutions), produces the expected
     * Levenshtein distance with a substitute count of zero.
     *
     * Source:  "\0\0\0S\0\0\0"  (length 8: 'S' at indices 0, 2, 5)
     * Target:  "\0\0\0S\0\0\0"  (length 7: 'S' at index 3)
     * Expected distance: 3 (all via inserts/deletes), substitutions: 0
     */
    @Test(timeout = 4000)
    public void test18() throws Throwable {
        // Build the target sequence: 7-char buffer with 'S' at position 3
        char[] targetChars = new char[7];
        targetChars[3] = 'S';
        CharBuffer target = CharBuffer.wrap(targetChars);

        // Build the source sequence: 8-char buffer with 'S' at positions 0, 2, and 5
        char[] sourceChars = new char[8];
        sourceChars[0] = 'S';
        sourceChars[2] = 'S';
        sourceChars[5] = 'S';
        CharBuffer source = CharBuffer.wrap(sourceChars);

        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance();
        LevenshteinResults results = distance.apply((CharSequence) source, (CharSequence) target);

        // All edits are inserts/deletes; no character substitutions occur
        assertEquals(3, (int) results.getDistance());
        assertEquals(0, (int) results.getSubstituteCount());
    }
}
