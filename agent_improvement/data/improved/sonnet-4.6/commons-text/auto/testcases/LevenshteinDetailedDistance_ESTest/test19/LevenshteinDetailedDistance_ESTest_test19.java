package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test19 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Verifies that the Levenshtein distance between two CharBuffer sequences containing
     * null characters and 'f' is correctly computed when a threshold of 7 is used.
     *
     * left  = "\0\0\0f\0\0\0"  (length 7: 'f' at index 3)
     * right = "\0\0f\0\0\0ff"  (length 8: 'f' at indices 2, 6, 7)
     *
     * Expected distance: 3 (within the threshold of 7)
     */
    @Test(timeout = 4000)
    public void test19() throws Throwable {
        // left sequence: null chars with 'f' at position 3 (length 7)
        CharBuffer left  = CharBuffer.wrap("\0\0\0f\0\0\0");
        // right sequence: null chars with 'f' at positions 2, 6, 7 (length 8)
        CharBuffer right = CharBuffer.wrap("\0\0f\0\0\0ff");

        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(Integer.valueOf(7));

        LevenshteinResults results = distance.apply((CharSequence) left, (CharSequence) right);

        assertEquals(3, (int) results.getDistance());
    }
}
