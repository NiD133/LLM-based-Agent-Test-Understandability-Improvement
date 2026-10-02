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
public class LevenshteinDetailedDistance_ESTest_test16 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Tests Levenshtein distance between two CharBuffers built from char arrays
     * containing null characters and a single 'S' at different positions.
     *
     * left:  "S\0\0\0\0\0\0\0"  (length 8, 'S' at index 0)
     * right: "\0\0\0S\0\0\0"    (length 7, 'S' at index 3)
     *
     * Expected distance is 2: the 'S' shifts 3 positions right and the lengths differ by 1,
     * resulting in 2 edit operations.
     */
    @Test(timeout = 4000)
    public void test16() throws Throwable {
        // left input: 8-element array with 'S' at the start, rest are null chars
        char[] leftChars = new char[8];
        leftChars[0] = 'S';
        CharBuffer leftBuffer = CharBuffer.wrap(leftChars);

        // right input: 7-element array with 'S' in the middle at index 3, rest are null chars
        char[] rightChars = new char[7];
        rightChars[3] = 'S';
        CharBuffer rightBuffer = CharBuffer.wrap(rightChars);

        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance();
        LevenshteinResults results = distance.apply((CharSequence) leftBuffer, (CharSequence) rightBuffer);

        assertEquals(2, (int) results.getDistance());
    }
}
