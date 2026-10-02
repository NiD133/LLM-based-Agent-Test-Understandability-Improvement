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
     * Verifies that matches() returns {0, 0, 0} when the two CharBuffer views of
     * the same backing array have no characters in common within the Jaro match window.
     *
     * Setup:
     *  - backing array: 12 null chars, then "X19l5(mgJ" written at position 0..8,
     *    leaving three trailing null chars at positions 9..11.
     *  - fullBuffer  : wraps the whole array (position=0, limit=12),
     *                  CharSequence content = "X19l5(mgJ\0\0\0" (12 chars)
     *  - trailingBuffer: the same CharBuffer after the put(), so position=9, limit=12,
     *                    CharSequence content = "\0\0\0" (3 chars)
     *
     * Match window analysis:
     *  - max length = 12 (fullBuffer), min length = 3 (trailingBuffer)
     *  - Jaro range = max(12/2 - 1, 0) = 5
     *  - Each of the 3 null chars in trailingBuffer looks for a match in fullBuffer
     *    within index window [mi-5, mi+5]. For mi in {0,1,2} the window only covers
     *    fullBuffer[0..7], which holds "X19l5(mg" — no null chars there.
     *  - Therefore: 0 matches, 0 half-transpositions, 0 common prefix.
     */
    @Test(timeout = 4000)
    public void test5() throws Throwable {
        // Build a 12-element backing array, initially all '\0'.
        char[] backingArray = new char[12];

        // Write "X19l5(mgJ" (9 chars) into the buffer; position advances to 9.
        // The last three slots (indices 9-11) remain '\0'.
        CharBuffer trailingBuffer = CharBuffer.wrap(backingArray);
        trailingBuffer.put("X19l5(mgJ");

        // Wrap the same array from the start; exposes all 12 chars as CharSequence.
        CharBuffer fullBuffer = CharBuffer.wrap(backingArray);

        // trailingBuffer now sits at position 9 → CharSequence is only "\0\0\0".
        // The null chars in trailingBuffer are too far from the null chars at the
        // end of fullBuffer to fall within the Jaro match window, so no matches occur.
        int[] result = JaroWinklerSimilarity.matches(fullBuffer, trailingBuffer);

        assertArrayEquals(new int[] { 0, 0, 0 }, result);
    }
}
