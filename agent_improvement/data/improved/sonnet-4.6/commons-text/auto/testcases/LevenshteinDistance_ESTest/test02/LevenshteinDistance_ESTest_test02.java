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
public class LevenshteinDistance_ESTest_test02 extends LevenshteinDistance_ESTest_scaffolding {

    /**
     * Verifies that the Levenshtein distance between a non-empty sequence and an empty sequence
     * equals the length of the non-empty sequence (every character must be deleted).
     *
     * The empty view is constructed by wrapping the source buffer with start == end == its capacity,
     * which produces a zero-length CharSequence.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // A 305-character buffer (filled with null chars '\0')
        CharBuffer sourceBuffer = CharBuffer.allocate(305);

        // An empty view of sourceBuffer: start and end both at position 305 → length 0
        CharBuffer emptyBuffer = CharBuffer.wrap((CharSequence) sourceBuffer, 305, 305);

        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        // Distance from a 305-char sequence to an empty sequence must be 305
        Integer distance = levenshteinDistance.apply((CharSequence) sourceBuffer, (CharSequence) emptyBuffer);
        assertEquals(305, (int) distance);
    }
}
