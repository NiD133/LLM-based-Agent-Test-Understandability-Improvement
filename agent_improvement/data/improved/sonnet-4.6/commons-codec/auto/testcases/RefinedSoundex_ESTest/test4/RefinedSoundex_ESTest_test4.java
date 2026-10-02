package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RefinedSoundex_ESTest_test4 extends RefinedSoundex_ESTest_scaffolding {

    /**
     * A custom mapping of 6 null characters means every letter maps to code 0
     * (characters beyond index 5 also return 0 by bounds check). With all codes
     * equal to 0 the soundex of any word is just its first letter. Two identical
     * strings therefore share exactly that one leading character, so difference()
     * must return 1.
     */
    @Test(timeout = 4000)
    public void test4_differenceOfIdenticalStringsWithAllZeroMappingIsOne() throws Throwable {
        // All-zero mapping: every alphabet character maps to code '\0' (0),
        // so the encoded form of any word is only its first letter.
        char[] allZeroMapping = new char[6];
        RefinedSoundex refinedSoundex = new RefinedSoundex(allZeroMapping);

        String input = "org.apache.commons.codec.EncoderException";
        int similarityScore = refinedSoundex.difference(input, input);

        // Both strings encode identically; the single shared character yields a score of 1.
        assertEquals(1, similarityScore);
    }
}
