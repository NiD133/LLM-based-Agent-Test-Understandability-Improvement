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
public class JaroWinklerSimilarity_ESTest_test0 extends JaroWinklerSimilarity_ESTest_scaffolding {

    /**
     * Verifies that comparing a 2-character null-char sequence against a 6-character
     * null-char sequence yields the expected Jaro-Winkler similarity (~0.822).
     *
     * The shorter sequence ("\0\0") shares all its characters with the longer one
     * ("\0\0\0\0\0\0"), but the length difference reduces the score below 1.0.
     */
    @Test(timeout = 4000)
    public void testSimilarityBetweenShorterAndLongerNullCharSequences() throws Throwable {
        // "\0\0" — two null characters (capacity-2 allocated buffer, all zeroed)
        CharBuffer twoNullChars = CharBuffer.allocate(2);

        // "\0\0\0\0\0\0" — six null characters (backed by a zeroed char array)
        char[] sixNullChars = new char[6];
        CharBuffer sixNullCharBuffer = CharBuffer.wrap(sixNullChars);

        JaroWinklerSimilarity similarity = JaroWinklerSimilarity.INSTANCE;
        Double result = similarity.apply((CharSequence) twoNullChars, (CharSequence) sixNullCharBuffer);

        assertEquals(0.8222222222222222, result, 0.01);
    }
}
