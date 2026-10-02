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
public class DamerauLevenshteinDistance_ESTest_test07 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * Verifies that comparing a sequence of 582 null characters to an empty sequence
     * returns a distance of 582 (one deletion per character), which falls exactly
     * at the threshold boundary and is therefore not clamped to -1.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Threshold equals the length of the longer input, so the distance is allowed through
        int threshold = 582;
        DamerauLevenshteinDistance distanceCalculator = new DamerauLevenshteinDistance(threshold);

        // Left input: a CharBuffer filled with 582 null characters ('\0')
        CharBuffer fullBuffer = CharBuffer.allocate(582);

        // Right input: an empty CharSequence obtained by wrapping fullBuffer
        // with equal start and end positions, producing zero characters
        CharBuffer emptyBuffer = CharBuffer.wrap((CharSequence) fullBuffer, 582, 582);

        // Converting the 582-char sequence to empty costs exactly 582 deletions,
        // which equals the threshold, so the result is 582 (not -1)
        Integer distance = distanceCalculator.apply((CharSequence) fullBuffer, (CharSequence) emptyBuffer);
        assertEquals(582, (int) distance);
    }
}
