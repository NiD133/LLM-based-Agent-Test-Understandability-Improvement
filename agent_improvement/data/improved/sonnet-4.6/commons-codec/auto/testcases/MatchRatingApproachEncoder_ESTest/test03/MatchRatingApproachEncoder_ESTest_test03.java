package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test03 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    // The encoder uppercases its input and preserves non-alphabetic characters.
    // "inS[" has a leading vowel (kept), consonants, and a bracket (not a vowel or double consonant).
    // After uppercasing and MRA processing the result is "INS[".
    @Test(timeout = 4000)
    public void test_encode_mixedCaseWithSpecialChar_returnsUppercasedResult() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        String encoded = encoder.encode("inS[");

        assertEquals("INS[", encoded);
    }
}
