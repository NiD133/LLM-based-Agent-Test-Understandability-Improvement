package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test03 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * Encoding "inS[" upper-cases it to "INS[". The leading vowel 'I' is kept,
     * there are no other vowels, double consonants, or trimmable punctuation to
     * remove, and the result is shorter than 7 characters, so the encoder returns
     * "INS[" unchanged.
     */
    @Test(timeout = 4000)
    public void encodeUpperCasesNameAndKeepsLeadingVowel() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        String encoded = encoder.encode("inS[");

        assertEquals("INS[", encoded);
    }
}
